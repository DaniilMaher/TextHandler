package com.maher.texthandler.element;

public abstract class AbstractTextElement implements TextElement {

    private TextElementType type;

    public AbstractTextElement(TextElementType type) {

        this.type = type;
    }

    @Override
    public TextElementType getType() {

        return type;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof AbstractTextElement)) {
            return false;
        }
        AbstractTextElement that = (AbstractTextElement) o;
        return (type == that.type);
    }

    @Override
    public int hashCode() {

        return type.hashCode();
    }

    @Override
    public String toString() {

        return "AbstractTextElement{"
                + "type=" + type
                + '}';
    }
}
