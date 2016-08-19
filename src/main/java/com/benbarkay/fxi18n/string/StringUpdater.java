package com.benbarkay.fxi18n.string;

import com.benbarkay.fxi18n.StringFormatter;
import com.benbarkay.fxi18n.StringRepository;
import com.benbarkay.fxi18n.util.Stub;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.lang.ref.WeakReference;

class StringUpdater implements InvalidationListener {

    private static final Observable STUB_OBSERVER = new Stub<>(Observable.class).thatReturns(null);

    private final String key;
    private final ObservableValue<Object[]> arguments;
    private final ObservableValue<StringRepository> observableRepository;
    private final WeakReference<StringProperty> stringPropertyRef;
    private final StringFormatter formatter;

    StringUpdater(
            String key,
            ObservableValue<Object[]> arguments,
            ObservableValue<StringRepository> observableRepository,
            WeakReference<StringProperty> stringPropertyRef,
            StringFormatter formatter) {
        this.key = key;
        this.arguments = arguments;
        this.observableRepository = observableRepository;
        this.stringPropertyRef = stringPropertyRef;
        this.formatter = formatter;
    }

    @Override
    public void invalidated(Observable observable) {
        StringProperty property = stringPropertyRef.get();
        if (property != null) {
            property.setValue(formatter.format(
                    observableRepository.getValue().getString(key),
                    arguments.getValue()));
        } else {
            observable.removeListener(this);
        }
    }

    public void update() {
        invalidated(STUB_OBSERVER);
    }
}
