package com.benbarkay.fxi18n.string;

import com.benbarkay.fxi18n.StringFormatter;
import com.benbarkay.fxi18n.StringRepository;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.lang.ref.WeakReference;

class StringUpdater implements ChangeListener<StringRepository> {

    private final WeakReference<StringProperty> weakStringProperty;
    private final String key;
    private final Object[] arguments;
    private final StringFormatter formatter;

    StringUpdater(
            WeakReference<StringProperty> weakStringProperty,
            String key,
            Object[] arguments,
            StringFormatter formatter) {
        this.weakStringProperty = weakStringProperty;
        this.key = key;
        this.arguments = arguments;
        this.formatter = formatter;
    }

    @Override
    public void changed(
            ObservableValue<? extends StringRepository> observable,
            StringRepository oldRepository,
            StringRepository repository) {
        StringProperty string = weakStringProperty.get();
        if (string == null) {
            observable.removeListener(this);
        } else {
            string.setValue(formatter.format(repository.getString(key), arguments));
        }
    }
}
