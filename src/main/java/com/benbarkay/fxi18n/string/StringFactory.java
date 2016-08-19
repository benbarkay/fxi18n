package com.benbarkay.fxi18n.string;

import com.benbarkay.fxi18n.StringFormatter;
import com.benbarkay.fxi18n.StringRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;

import java.lang.ref.WeakReference;

public class StringFactory {

    private final StringFormatter stringFormatter;

    public StringFactory(StringFormatter stringFormatter) {
        this.stringFormatter = stringFormatter;
    }

    public ObservableStringValue getString(
            String key,
            Object[] args,
            ObservableValue<StringRepository> observableRepository) {
        if (args == null) {
            args = new Object[0];
        }

        SimpleStringProperty stringProperty = new SimpleStringProperty();
        ObservableValue<Object[]> formatArguments = FormatArguments.forArguments(args);
        StringUpdater stringUpdater = new StringUpdater(
                key,
                formatArguments,
                observableRepository,
                new WeakReference<StringProperty>(stringProperty),
                stringFormatter
        );
        formatArguments.addListener(stringUpdater);
        observableRepository.addListener(stringUpdater);
        stringUpdater.update();
        return stringProperty;
    }
}
