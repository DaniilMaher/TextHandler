package com.maher.texthandler.action;

import com.maher.texthandler.comparator.TextElementByCharOccurrenceComparator;
import com.maher.texthandler.comparator.TextElementByLengthComparator;
import com.maher.texthandler.comparator.TextElementBySubElementsCountComparator;
import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextElementType;
import com.maher.texthandler.parser.*;
import com.maher.texthandler.reader.FileTextReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TextElementActionTest {

    private TextElement text;

    @BeforeClass
    public void prepareText() {
        FileTextReader reader = new FileTextReader("data/text.txt");
        String initialText = reader.read();
        TextParserChain lexemeParser = new LexemeParser();
        TextParserChain sentenceParser = new TextToLexemesParser(lexemeParser);
        TextParserChain paragraphParser = new TextToSentencesParser(sentenceParser);
        TextParserChain textParser = new TextToParagraphsParser(paragraphParser);
        text = textParser.parse(initialText, TextElementType.TEXT);
    }

    @Test
    public void textRestoreTest() {
        TextElementAction action = new TextElementAction();
        String restoredText = action.restoreText(text);
        System.out.println(restoredText);
    }

    @Test
    public void paragraphsBySentencesCountSortTest(){
        TextElementAction action = new TextElementAction();
        TextElementBySubElementsCountComparator comparator = new TextElementBySubElementsCountComparator(TextElementType.SENTENCE);
        List<TextElement> sortedParagraphs = action.sortedSubElements((TextComposite)text, TextElementType.PARAGRAPH, comparator);
        StringBuilder result = new StringBuilder();
        for (TextElement paragraph : sortedParagraphs) {
            result.append(action.restoreText(paragraph));
            result.append("\n");
        }
        System.out.println(result);
    }

    @Test
    public void wordsByLengthInEachSentenceSortTest() {
        TextElementAction action = new TextElementAction();
        List<TextElement> sentences = action.getSubElementsByType((TextComposite) text, TextElementType.SENTENCE);
        TextElementByLengthComparator comparator = new TextElementByLengthComparator();
        List<List<TextElement>> sentencesWithSortedWords = new ArrayList<>();
        for (TextElement sentence : sentences) {
            sentencesWithSortedWords.add(action.sortedSubElements((TextComposite)sentence, TextElementType.WORD, comparator));
        }
        StringBuilder result = new StringBuilder();
        for (List<TextElement> sentence : sentencesWithSortedWords) {
            for (TextElement word : sentence) {
                result.append(action.restoreText(word));
                result.append(" ");
            }
            result.append("\n");
        }
        System.out.println(result);
    }

    @Test
    public void lexemesByCharacterOccurrenceOrAlphabetSortTest() {
        TextToLexemesParser parser = new TextToLexemesParser(null);
        String initialSentence = "It is a (7^5|1&2<<(2|5>>2&71))|1200 established fact that a reader will be of a page when looking at its layout.";
        TextElement sentence = parser.parse(initialSentence, TextElementType.SENTENCE);
        TextElementAction action = new TextElementAction();
        TextElementByCharOccurrenceComparator comparator = new TextElementByCharOccurrenceComparator('e');
        List<TextElement> lexemes = action.sortedSubElements((TextComposite)sentence, TextElementType.LEXEME, comparator);
        StringBuilder result = new StringBuilder();
        for (TextElement lexeme : lexemes) {
            result.append(action.restoreText(lexeme));
            result.append(" ");
        }
        System.out.println(result);
    }
}