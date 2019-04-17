package com.maher.texthandler.parser;

import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextElementType;
import com.maher.texthandler.element.TextLeaf;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TextToParagraphsParserTest {

    @Test
    public void testParse() {

        String initialText = "    \n\tJust one paragraph.\n    And another one.\n\t\n\tAnd more.\n\r\tAnd one more \t           ";
        TextToParagraphsParser parser = new TextToParagraphsParser(null);
        TextElement actual = parser.parse(initialText);
        TextElement expected = new TextComposite(TextElementType.TEXT);
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.PARAGRAPH, "Just one paragraph."));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.PARAGRAPH, "And another one."));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.PARAGRAPH, "And more."));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.PARAGRAPH, "And one more"));
        Assert.assertEquals(actual, expected);

    }
}