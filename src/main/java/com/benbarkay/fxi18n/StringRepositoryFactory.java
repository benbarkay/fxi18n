package com.benbarkay.fxi18n;

import java.util.Locale;

public interface StringRepositoryFactory {
    StringRepository getRepository(Class cls, Locale locale);
}
