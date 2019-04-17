package com.maher.texthandler.parser;

import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextElementType;
import com.maher.texthandler.element.TextLeaf;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TextToLexemesParserTest {

    @Test
    public void testParse() {
        String initialText = "   \n\tIt is a \n\t(7^5|1&2<<(2|5>>2&71))|1200 established fact.\n  ";
        TextToLexemesParser parser = new TextToLexemesParser(null);
        TextElement actual = parser.parse(initialText, TextElementType.SENTENCE);
        TextElement expected = new TextComposite(TextElementType.SENTENCE);
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.LEXEME, "It"));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.LEXEME, "is"));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.LEXEME, "a"));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.LEXEME, "(7^5|1&2<<(2|5>>2&71))|1200"));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.LEXEME, "established"));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.LEXEME, "fact."));
        Assert.assertEquals(actual, expected);

    }
}