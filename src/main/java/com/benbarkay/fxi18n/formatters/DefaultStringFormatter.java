package com.benbarkay.fxi18n.formatters;

import com.benbarkay.fxi18n.StringFormatter;

/**
 * Formats strings using the JRL's {@link java.util.Formatter}.
 */
public class DefaultStringFormatter implements StringFormatter {
    @Override
    public String format(String format, Object... args) {
        return String.format(format, args);
    }
}
