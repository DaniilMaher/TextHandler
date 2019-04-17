package com.maher.texthandler.parser;

import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextLeaf;
import com.maher.texthandler.element.TextElementType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextToSentencesParser extends TextParserChain {

    private static final String SENTENCE_END_REGEX = "([.?!]|\\.{3})(\\s+|$)";
    private static final Pattern SENTENCE_END_PATTERN = Pattern.compile(SENTENCE_END_REGEX);

    public TextToSentencesParser(TextParserChain nextParser) {

        super(nextParser);
    }

    @Override
    public TextElement parse(String initialText) {
        return parse(initialText, TextElementType.PARAGRAPH);
    }

    @Override
    public TextElement parse(String paragraphText, TextElementType type) {

        TextComposite paragraph = new TextComposite(type);
        Matcher sentenceEndMatcher = SENTENCE_END_PATTERN.matcher(paragraphText);
        int currSentenceStart = 0;
        int currSentenceEnd;
        String currSentence;
        while (sentenceEndMatcher.find()) {
            currSentenceEnd = sentenceEndMatcher.end();
            currSentence = paragraphText.substring(currSentenceStart, currSentenceEnd).trim();
            if (nextParser != null) {
                paragraph.addElement(nextParser.parse(currSentence, TextElementType.SENTENCE));
            } else {
                paragraph.addElement(new TextLeaf(TextElementType.SENTENCE, currSentence));
            }
            currSentenceStart = currSentenceEnd;
        }
        return paragraph;
    }
}
