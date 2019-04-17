package com.maher.texthandler.comparator;

import com.maher.texthandler.action.TextElementAction;
import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextElementType;

import java.util.Comparator;

public class TextElementBySubElementsCountComparator implements Comparator<TextElement> {

    private final TextElementType REQUIRED_SUB_ELEMENT_TYPE;

    public TextElementBySubElementsCountComparator(TextElementType requiredSubElementType) {

        this.REQUIRED_SUB_ELEMENT_TYPE = requiredSubElementType;
    }

    @Override
    public int compare(TextElement o1, TextElement o2) {

        TextElementAction action = new TextElementAction();
        int firstSubElementsCount = ((o1 instanceof TextComposite) ?
                action.getSubElementsByType((TextComposite) o1, REQUIRED_SUB_ELEMENT_TYPE).size() : 0);
        int secondSubElementsCount = ((o2 instanceof TextComposite) ?
                action.getSubElementsByType((TextComposite) o2, REQUIRED_SUB_ELEMENT_TYPE).size() : 0);
        return firstSubElementsCount - secondSubElementsCount;
    }
}
