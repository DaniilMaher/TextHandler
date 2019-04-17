package com.maher.texthandler.element;

public class TextLeaf extends AbstractTextElement {

    private String value;

    public TextLeaf(TextElementType type, String value) {

        super(type);
        this.value = value;
    }

    public String getValue() {

        return value;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof TextLeaf)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TextLeaf textLeaf = (TextLeaf) o;
        return value.equals(textLeaf.value);
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "TextLeaf{"
                + "value='" + value + '\''
                + "} " + super.toString();
    }
}
