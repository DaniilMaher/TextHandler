package com.maher.texthandler.expression;

import com.maher.texthandler.interpreter.ExpressionContext;
import com.maher.texthandler.exception.TextHandlerException;
import com.maher.texthandler.interpreter.*;
import com.maher.texthandler.operation.BitwiseOperation;
import com.maher.texthandler.operation.constant.OperationMarkConstant;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionHandler {

    private static final String NUMBER_REGEX = "[+\\-]?\\d+";
    private static final String EXPRESSION_ELEMENT_REGEX = "([+\\-]?\\d+)|[<>]{2}|[~|^&()]";
    private static final Pattern EXPRESSION_ELEMENT_PATTERN = Pattern.compile(EXPRESSION_ELEMENT_REGEX);

    private String initialExpression;
    private List<ExpressionInterpreter> expressionList = new ArrayList<>();
    private ArrayDeque<BitwiseOperation> stack = new ArrayDeque<>();

    public ExpressionHandler(String expression) {

        this.initialExpression = expression;
    }

    public void parse() throws TextHandlerException {

        Matcher expressionElementMatcher = EXPRESSION_ELEMENT_PATTERN.matcher(initialExpression);
        int prevElementEnd = 0;
        while (expressionElementMatcher.find()) {
            if (prevElementEnd == expressionElementMatcher.start()) {
                String element = expressionElementMatcher.group();
                if (element.matches(NUMBER_REGEX)) {
                    expressionList.add(InterpreterFactory.createNumberInterpreter(Integer.parseInt(element)));
                } else {
                    processOperation(element);
                }
                prevElementEnd = expressionElementMatcher.end();
            } else {
                throw new TextHandlerException("Can't parse initialExpression " + initialExpression
                        + ", illegal character on position number " + prevElementEnd);
            }
        }
        while (!stack.isEmpty()) {
            BitwiseOperation prevOperation = stack.pop();
            expressionList.add(InterpreterFactory.createOperationInterpreter(prevOperation));
        }
    }

    private void processOperation(String operationMark) throws TextHandlerException {

        if (operationMark.equals(OperationMarkConstant.CLOSE_PARENTHESES)) {
            while (!stack.isEmpty() && stack.getFirst() != BitwiseOperation.PARENTHESES) {
                BitwiseOperation prevOperation = stack.pop();
                expressionList.add(InterpreterFactory.createOperationInterpreter(prevOperation));
            }
            if (!stack.isEmpty()) {
                stack.pop();
            } else {
                throw new TextHandlerException("Incorrect initialExpression, parentheses mismatch");
            }
        } else {
            BitwiseOperation operation = BitwiseOperation.getOperationByMark(operationMark);
            if (operation == BitwiseOperation.PARENTHESES) {
                stack.push(operation);
            } else {
                while (!stack.isEmpty() && operation.getPriority() < stack.getFirst().getPriority()) {
                    BitwiseOperation prevOperation = stack.pop();
                    expressionList.add(InterpreterFactory.createOperationInterpreter(prevOperation));
                }
                stack.push(operation);
            }
        }
    }


    public int calculate() throws TextHandlerException {

        if (expressionList.isEmpty()) {
            throw new TextHandlerException("Expression should be parsed before calculation");
        }
        ExpressionContext context = new ExpressionContext();
        try {
            for (ExpressionInterpreter expression : expressionList) {
                expression.accept(context);
            }
            return context.popValue();
        } catch (NoSuchElementException e) {
            throw new TextHandlerException("Expression " + initialExpression + " is incorrect, can't be calculated");
        }

    }
}
