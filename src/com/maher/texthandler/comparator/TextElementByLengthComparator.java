package com.maher.texthandler.comparator;

import com.maher.texthandler.action.TextElementAction;
import com.maher.texthandler.element.TextElement;

import java.util.Comparator;

public class TextElementByLengthComparator implements Comparator<TextElement> {

    @Override
    public int compare(TextElement o1, TextElement o2) {
        TextElementAction action = new TextElementAction();
        return (action.restoreText(o1).length() - action.restoreText(o2).length());
    }
}
