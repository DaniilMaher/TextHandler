package com.maher.texthandler.parser;

import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElementType;
import com.maher.texthandler.element.TextLeaf;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.exception.TextHandlerException;
import com.maher.texthandler.expression.ExpressionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexemeParser extends TextParserChain {

    private static final Logger logger = LogManager.getLogger();
    private static final String CHARACTER_DELIMITER = "";
    private static final String EXPRESSION_REGEX = "[\\d~<>&|^()\\-+]*\\d[\\d~<>&|^()\\-+]*";
    private static final String WORD_REGEX = "[A-Za-z]+([-_'`][A-Za-z]+)*";
    private static final String WORD_OR_EXPRESSION_REGEX = "(" + WORD_REGEX + ")|(" + EXPRESSION_REGEX + ")";
    private static final Pattern WORD_OR_EXPRESSION_PATTERN = Pattern.compile(WORD_OR_EXPRESSION_REGEX);

    @Override
    public TextElement parse(String initialText) {
        return parse(initialText, TextElementType.LEXEME);
    }

    @Override
    public TextElement parse(String lexemeText, TextElementType type) {

        TextComposite lexeme = new TextComposite(type);
        lexemeText = lexemeText.trim();
        String[] characters = lexemeText.split(CHARACTER_DELIMITER);
        int prevElementEnd = 0;
        Matcher wordOrExpressionMatcher = WORD_OR_EXPRESSION_PATTERN.matcher(lexemeText);
        while (wordOrExpressionMatcher.find()) {
            addCharacters(characters, lexeme, prevElementEnd, wordOrExpressionMatcher.start());
            String element = wordOrExpressionMatcher.group();
            if (element.matches(WORD_REGEX)) {
                lexeme.addElement(new TextLeaf(TextElementType.WORD, element));
            } else {
                ExpressionHandler expressionHandler = new ExpressionHandler(element);
                try {
                    expressionHandler.parse();
                    int expressionValue = expressionHandler.calculate();
                    lexeme.addElement(new TextLeaf(TextElementType.EXPRESSION, String.valueOf(expressionValue)));
                } catch (TextHandlerException e) {
                    logger.error("Incorrect expression", e);
                    lexeme.addElement(new TextLeaf(TextElementType.EXPRESSION, element));
                }
            }
            prevElementEnd = wordOrExpressionMatcher.end();
        }
        addCharacters(characters, lexeme, prevElementEnd, lexemeText.length());
        return lexeme;
    }

    private void addCharacters(String[] characters, TextComposite lexeme, int start, int end) {
        for (int i = start; i < end; i++) {
            lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, characters[i]));
        }
    }
}
