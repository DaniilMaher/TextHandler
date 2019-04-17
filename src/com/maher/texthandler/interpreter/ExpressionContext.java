package com.maher.texthandler.interpreter;

import java.util.ArrayDeque;

public class ExpressionContext {

    private ArrayDeque<Integer> values = new ArrayDeque<>();

    public Integer popValue() {

        return values.pop();
    }

    public void pushValue(Integer value) {

        values.push(value);
    }
}
