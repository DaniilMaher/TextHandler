package com.maher.texthandler.expression;

import com.maher.texthandler.exception.TextHandlerException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ExpressionHandlerTest {

    @DataProvider(name = "bitwiseExpressionDataProvider")
    private Object[][] bitwiseExpressionDataProvider() {
        return new Object[][] {
                {"4&(1^5|6&47)", 4&(1^5|6&47)},
                {"13<<2", 13<<2},
                {"3>>5", 3>>5},
                {"5|(1&2&(3|(4&(1^5|6&47)|3)|2)|1)", 5|(1&2&(3|(4&(1^5|6&47)|3)|2)|1)},
                {"~6&9|(3&4)", ~6&9|(3&4)},
                {"(~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78", (~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78},
                {"(7^5|1&2<<(2|5>>2&71))|1200", (7^5|1&2<<(2|5>>2&71))|1200}
        };
    }

    @Test(dataProvider = "bitwiseExpressionDataProvider")
    public void testParse(String expression, int expectedResult) throws TextHandlerException {
        ExpressionHandler handler = new ExpressionHandler(expression);
        handler.parse();
        int actual = handler.calculate();
        assertEquals(expectedResult, actual);
    }
}