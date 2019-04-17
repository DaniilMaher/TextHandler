package com.maher.texthandler.parser;

import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextLeaf;
import com.maher.texthandler.element.TextElementType;

public class TextToParagraphsParser extends TextParserChain {

    private static final String PARAGRAPH_DELIMITER = "\\s*\n\\s*(\t|( ){4})\\s*";

    public TextToParagraphsParser(TextParserChain nextParser) {
        
        super(nextParser);
    }

    @Override
    public TextElement parse(String initialText) {
        return parse(initialText, TextElementType.TEXT);
    }

    @Override
    public TextElement parse(String initialText, TextElementType type) {

        initialText = initialText.trim();
        String[] paragraphs = initialText.split(PARAGRAPH_DELIMITER);
        TextComposite text = new TextComposite(type);
        if (nextParser != null) {
            for (String paragraph : paragraphs) {
                text.addElement(nextParser.parse(paragraph.trim(), TextElementType.PARAGRAPH));
            }
        } else {
            for (String paragraph : paragraphs) {
                text.addElement(new TextLeaf(TextElementType.PARAGRAPH, paragraph.trim()));
            }
        }
        return text;
    }
}
