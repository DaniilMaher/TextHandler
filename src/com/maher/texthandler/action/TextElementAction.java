package com.maher.texthandler.action;

import com.maher.texthandler.element.TextComposite;
import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextElementType;
import com.maher.texthandler.element.TextLeaf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TextElementAction {

    private static final String BLANK_LINE = "";
    private static final String SPACE = " ";
    private static final String TABULATION = "\t";
    private static final String LINE_FEED = "\n";

    public List<TextElement> sortedSubElements(TextComposite sourceTextElement,
                                               TextElementType subElementType,
                                               Comparator<TextElement> textElementComparator) {

        List<TextElement> result = getSubElementsByType(sourceTextElement, subElementType);
        result.sort(textElementComparator);
        return result;
    }

    public List<TextElement> getSubElementsByType(TextComposite initialTextElement,
                                                  TextElementType requiredSubElementType) {

        List<TextElement> result = new ArrayList<>();
        int subElementsCount = initialTextElement.subElementsCount();
        for (int i = 0; i < subElementsCount; i++) {
            TextElement subElement = initialTextElement.getSubElement(i);
            if (subElement.getType() == requiredSubElementType) {
                result.add(subElement);
            } else {
                if (subElement instanceof TextComposite) {
                    TextComposite compositeSubElement = (TextComposite) subElement;
                    result.addAll(getSubElementsByType(compositeSubElement, requiredSubElementType));
                }
            }
        }
        return result;
    }

    public String restoreText(TextElement text) {

        if (text instanceof TextLeaf) {
            return ((TextLeaf) text).getValue();
        } else {
            StringBuilder result = new StringBuilder();
            TextComposite textElement = (TextComposite) text;
            int subElementsCount = textElement.subElementsCount();
            for (int i = 0; i < subElementsCount; i++) {
                TextElement subElement = textElement.getSubElement(i);
                result.append(getPrefix(subElement));
                result.append(restoreText(subElement).trim());
                result.append(getPostfix(subElement));
            }
            return result.toString();
        }
    }

    private String getPrefix(TextElement element) {

        return ((element.getType() == TextElementType.PARAGRAPH) ? TABULATION : BLANK_LINE);
    }

    private String getPostfix(TextElement element) {

        TextElementType type = element.getType();
        return ((type == TextElementType.PARAGRAPH)
                ? LINE_FEED
                : ((type == TextElementType.SENTENCE || type == TextElementType.LEXEME) ? SPACE
                                                                                        : BLANK_LINE));
    }
}
