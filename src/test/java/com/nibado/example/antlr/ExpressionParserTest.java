package com.nibado.example.antlr;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExpressionParserTest {
    private ExpressionParser _parser;
    @Rule
    public ExpectedException _expected = ExpectedException.none();

    @Before
    public void setup() {
        _parser = new ExpressionParser();
    }

    @Test
    public void testNumber() {
        assertThat(_parser.parse("42"), equalTo(42));
    }

    @Test
    public void testNumberBrackets() {
        assertThat(_parser.parse("(42)"), equalTo(42));
    }

    @Test
    public void testTimes() {
        assertThat(_parser.parse("21 * 2"), equalTo(42));
    }

    @Test
    public void testDiv() {
        assertThat(_parser.parse("84 / 2"), equalTo(42));
    }

    @Test
    public void testPlus() {
        assertThat(_parser.parse("40 + 2"), equalTo(42));
    }

    @Test
    public void testMinus() {
        assertThat(_parser.parse("53 - 11"), equalTo(42));
    }

    @Test
    public void testComplex() {
        assertThat(_parser.parse("(21 * (8 / 2 - (1 + 1)))"), equalTo(42));
    }

    @Test
    public void testInvalid() {
        _expected.expect(IllegalArgumentException.class);
        _expected.expectMessage(containsString("token recognition error at: '#'"));

        assertThat(_parser.parse("(21 # 2)"), equalTo(42));
    }

}
