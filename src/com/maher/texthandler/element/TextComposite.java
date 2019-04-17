package com.maher.texthandler.element;

import java.util.ArrayList;
import java.util.List;

public class TextComposite extends AbstractTextElement {

    private List<TextElement> subElements = new ArrayList<>();

    public TextComposite(TextElementType type) {

        super(type);
    }

    public void addElement(TextElement element) {

        subElements.add(element);
    }

    public TextElement getSubElement(int number) {

        return ((number < 0 || number >= subElements.size()) ? null
               : subElements.get(number));
    }

    public int subElementsCount() {

        return subElements.size();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof TextComposite)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TextComposite that = (TextComposite) o;
        return subElements.equals(that.subElements);
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + subElements.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "TextComposite{"
                + "subElements=" + subElements
                + "} " + super.toString();
    }
}
