package com.nibado.example.antlr;

import java.util.BitSet;
import java.util.Locale;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import com.nibado.example.antlr.SimpleParser.ExprContext;

public class ExpressionParser {
    public int parse(final String expression) {
        final ANTLRErrorListener listener = createErrorListener();
        final SimpleLexer lexer = new SimpleLexer(new ANTLRInputStream(expression));
        lexer.removeErrorListeners();
        lexer.addErrorListener(listener);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final SimpleParser parser = new SimpleParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(listener);

        final ExprContext context = parser.expr();

        return visit(context);
    }

    private ANTLRErrorListener createErrorListener() {
        return new ANTLRErrorListener() {
            public void syntaxError(final Recognizer<?, ?> arg0, final Object obj, final int line, final int position, final String message, final RecognitionException ex) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Exception parsing expression: '%s' on line %s, position %s", message, line, position));
            }

            public void reportContextSensitivity(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final int arg4, final ATNConfigSet arg5) {
            }

            public void reportAttemptingFullContext(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final BitSet arg4, final ATNConfigSet arg5) {
            }

            public void reportAmbiguity(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final boolean arg4, final BitSet arg5, final ATNConfigSet arg6) {
            }
        };
    }

    private int visit(final ExprContext context) {
        if (context.number() != null) {
            return Integer.parseInt(context.number().getText());
        }
        else if (context.BR_CLOSE() != null) {
            return visit(context.expr(0));
        }
        else if (context.TIMES() != null) {
            return visit(context.expr(0)) * visit(context.expr(1));
        }
        else if (context.DIV() != null) {
            return visit(context.expr(0)) / visit(context.expr(1));
        }
        else if (context.PLUS() != null) {
            return visit(context.expr(0)) + visit(context.expr(1));
        }
        else if (context.MINUS() != null) {
            return visit(context.expr(0)) - visit(context.expr(1));
        }
        else {
            return -1;
        }
    }
}
