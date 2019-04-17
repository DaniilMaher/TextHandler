package com.maher.texthandler.parser;

import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextElementType;
import com.maher.texthandler.element.TextLeaf;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LexemeParserTest {

    @DataProvider(name = "parseTestDataProvider")
    public Object[][] parseTestDataProvider() {
        Object[][] data = new Object[3][2];
        TextComposite lexeme;

        data[0][0] = "ab(~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78,.";
        lexeme = new TextComposite(TextElementType.LEXEME);
        lexeme.addElement(new TextLeaf(TextElementType.WORD, "ab"));
        lexeme.addElement(new TextLeaf(TextElementType.EXPRESSION, String.valueOf((~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78)));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, ","));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "."));
        data[0][1] = lexeme;

        data[1][0] = "(more-or-less).";
        lexeme = new TextComposite(TextElementType.LEXEME);
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "("));
        lexeme.addElement(new TextLeaf(TextElementType.WORD, "more-or-less"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, ")"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "."));
        data[1][1] = lexeme;

        data[2][0] = " #%@_#-('$^&|?~!   ";
        lexeme = new TextComposite(TextElementType.LEXEME);
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "#"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "%"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "@"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "_"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "#"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "-"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "("));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "'"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "$"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "^"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "&"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "|"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "?"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "~"));
        lexeme.addElement(new TextLeaf(TextElementType.CHARACTER, "!"));
        data[2][1] = lexeme;
        return data;
    }

    @Test(dataProvider = "parseTestDataProvider")
    public void parseTest(String lexemeText, TextElement expected) {
        TextParserChain parser = new LexemeParser();
        TextElement actual = parser.parse(lexemeText);
        Assert.assertEquals(actual, expected);
    }
}