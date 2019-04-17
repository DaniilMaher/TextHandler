package com.maher.texthandler.parser;

import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextLeaf;
import com.maher.texthandler.element.TextElementType;

public class TextToLexemesParser extends TextParserChain {

    private static final String LEXEME_DELIMITER = "\\s+";

    public TextToLexemesParser(TextParserChain nextParser) {

        super(nextParser);
    }

    @Override
    public TextElement parse(String initialText) {

        return parse(initialText, TextElementType.SENTENCE);
    }

    @Override
    public TextElement parse(String initialText, TextElementType type) {

        initialText = initialText.trim();
        TextComposite sentence = new TextComposite(type);
        String[] lexemes = initialText.split(LEXEME_DELIMITER);
        if (nextParser != null) {
            for (String lexeme : lexemes) {
                sentence.addElement(nextParser.parse(lexeme, TextElementType.LEXEME));
            }
        } else {
            for (String lexeme : lexemes) {
                sentence.addElement(new TextLeaf(TextElementType.LEXEME, lexeme));
            }
        }
        return sentence;
    }
}
