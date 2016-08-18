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
        SimpleStringProperty stringProperty = new SimpleStringProperty();
        StringUpdater updater = new StringUpdater(
                new WeakReference<StringProperty>(stringProperty),
                key,
                args,
                stringFormatter
        );
        observableRepository.addListener(updater);
        updater.changed(null, null, observableRepository.getValue());
        return stringProperty;
    }
}
