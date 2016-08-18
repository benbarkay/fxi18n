package com.benbarkay.fxi18n;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.lang.ref.WeakReference;
import java.util.Locale;

class RepositoryUpdater implements ChangeListener<Locale> {
    private final Class cls;
    private final WeakReference<Property<StringRepository>> weakRepositoryProperty;
    private final StringRepositoryFactory repositoryFactory;

    RepositoryUpdater(
            Class cls,
            WeakReference<Property<StringRepository>> weakRepositoryProperty,
            StringRepositoryFactory repositoryFactory) {
        this.cls = cls;
        this.weakRepositoryProperty = weakRepositoryProperty;
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public void changed(
            ObservableValue<? extends Locale> observable,
            Locale oldLocale,
            Locale locale) {
        Property<StringRepository> repositoryProperty = weakRepositoryProperty.get();
        if (repositoryProperty == null) {
            observable.removeListener(this);
        } else {
            repositoryProperty.setValue(repositoryFactory.getRepository(cls, locale));
        }
    }
}
