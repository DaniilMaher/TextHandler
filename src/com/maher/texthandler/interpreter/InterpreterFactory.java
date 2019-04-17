package com.maher.texthandler.interpreter;

import com.maher.texthandler.exception.TextHandlerException;
import com.maher.texthandler.operation.BitwiseOperation;

public class InterpreterFactory {

    public static ExpressionInterpreter createOperationInterpreter(BitwiseOperation operation) throws TextHandlerException {

        switch (operation) {
            case NOT: return (c -> c.pushValue(~c.popValue()));
            case OR: return (c -> c.pushValue(c.popValue() | c.popValue()));
            case AND: return (c -> c.pushValue(c.popValue() & c.popValue()));
            case XOR: return (c -> c.pushValue(c.popValue() ^ c.popValue()));
            case LEFT_SHIFT: return (c -> {
                int shiftPositionsCount = c.popValue();
                c.pushValue(c.popValue() << shiftPositionsCount);
            });
            case RIGHT_SHIFT: return (c -> {
                int shiftPositionsCount = c.popValue();
                c.pushValue(c.popValue() >> shiftPositionsCount);
            });
            default: throw new TextHandlerException("Unsupported operation, can't create interpreter for operation "
                    + operation);
        }
    }

    public static ExpressionInterpreter createNumberInterpreter(int number) {

        return (c -> c.pushValue(number));
    }
}
