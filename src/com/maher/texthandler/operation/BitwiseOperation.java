package com.maher.texthandler.operation;

import com.maher.texthandler.exception.TextHandlerException;

import static com.maher.texthandler.operation.constant.OperationMarkConstant.*;

public enum BitwiseOperation {

    PARENTHESES (OPEN_PARENTHESES, 1),
    OR (PIPE, 2),
    XOR(CARET, 3),
    AND (AMPERSAND, 4),
    LEFT_SHIFT(DOUBLE_OPEN_BRACKET, 5),
    RIGHT_SHIFT (DOUBLE_CLOSE_BRACKET, 5),
    NOT (TILDE, 6);

    private String mark;
    private int priority;

    BitwiseOperation(String mark, int priority) {

        this.mark = mark;
        this.priority = priority;
    }

    public String getMark() {

        return mark;
    }

    public int getPriority() {
        return priority;
    }

    public static BitwiseOperation getOperationByMark(String mark) throws TextHandlerException {
        switch (mark) {
            case OPEN_PARENTHESES:
            case CLOSE_PARENTHESES: return PARENTHESES;
            case PIPE: return OR;
            case CARET: return XOR;
            case AMPERSAND: return AND;
            case DOUBLE_OPEN_BRACKET: return LEFT_SHIFT;
            case DOUBLE_CLOSE_BRACKET: return RIGHT_SHIFT;
            case TILDE: return NOT;
            default: throw new TextHandlerException("Illegal argument. There is no operation, associated with mark " + mark);
        }
    }


}
