package com.maher.texthandler.parser;

import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextElementType;
import com.maher.texthandler.element.TextLeaf;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TextToSentencesParserTest {

    @Test
    public void testParse() {
        String initialText = "\tSentence (simple). And one more!\nAnd with 5.269 number... Enough?     \t\n\r\b\n\t\rOne more...";
        TextToSentencesParser parser = new TextToSentencesParser(null);
        TextElement actual = parser.parse(initialText);
        TextElement expected = new TextComposite(TextElementType.PARAGRAPH);
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.SENTENCE, "Sentence (simple)."));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.SENTENCE, "And one more!"));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.SENTENCE, "And with 5.269 number..."));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.SENTENCE, "Enough?"));
        ((TextComposite) expected).addElement(new TextLeaf(TextElementType.SENTENCE, "One more..."));
        Assert.assertEquals(actual, expected);
    }
}