package com.benbarkay.fxi18n.string;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

class FormatArguments extends SimpleObjectProperty<Object[]> {

    static ObservableValue<Object[]> forArguments(Object[] arguments) {
        FormatArguments formatArguments = new FormatArguments(arguments);
        for (Object argument : arguments) {
            if (argument instanceof ObservableValue) {
                ((ObservableValue)argument).addListener(formatArguments.argumentInvalidated);
            }
        }
        formatArguments.update();
        return formatArguments;
    }

    private final Object[] args;

    private final InvalidationListener argumentInvalidated = new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            update();
        }
    };

    private FormatArguments(Object[] args) {
        this.args = args;
    }

    private void update() {
        Object[] concreteArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ObservableValue) {
                concreteArgs[i] = ((ObservableValue)args[i]).getValue();
            } else {
                concreteArgs[i] = args[i];
            }
        }
        setValue(concreteArgs);
    }
}
