package com.benbarkay.fxi18n;

import javafx.beans.value.ObservableValue;

public interface StringsFactory {
    <T> T getStrings(Class<T> cls, ObservableValue<StringRepository> observableStringRepository);
}
