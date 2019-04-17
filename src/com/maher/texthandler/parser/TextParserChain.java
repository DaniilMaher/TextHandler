package com.maher.texthandler.parser;

import com.maher.texthandler.element.TextElement;
import com.maher.texthandler.element.TextElementType;

public abstract class TextParserChain {

    protected TextParserChain nextParser;

    public TextParserChain() {}

    public TextParserChain(TextParserChain nextParser) {

        this.nextParser = nextParser;
    }

    public abstract TextElement parse(String initialText);

    public abstract TextElement parse(String initialText, TextElementType type);

}