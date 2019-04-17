package com.maher.texthandler.comparator;

import com.maher.texthandler.action.TextElementAction;
import com.maher.texthandler.element.TextElement;

import java.util.Comparator;

public class TextElementByCharOccurrenceComparator implements Comparator<TextElement> {

    private final char SEARCH_CHAR;

    public TextElementByCharOccurrenceComparator(char searchChar) {

        this.SEARCH_CHAR = searchChar;
    }


    @Override
    public int compare(TextElement o1, TextElement o2) {

        TextElementAction action = new TextElementAction();
        String firstElementText = action.restoreText(o1);
        String secondElementText = action.restoreText(o2);
        long firstStringCharCount = firstElementText.chars().filter(ch -> ch == SEARCH_CHAR).count();
        long secondStringCharCount = secondElementText.chars().filter(ch -> ch == SEARCH_CHAR).count();
        return ((firstStringCharCount != secondStringCharCount)
                ? (int) (firstStringCharCount - secondStringCharCount)
                : firstElementText.compareToIgnoreCase(secondElementText));
    }
}
