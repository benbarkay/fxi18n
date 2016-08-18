package com.benbarkay.fxi18n;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

import java.lang.ref.WeakReference;
import java.util.Locale;

public class Strings {

    public static Strings createDefault() {
        return builder().build();
    }

    public static StringsBuilder builder() {
        return new StringsBuilder();
    }

    private final Property<Locale> localeProperty;
    private final StringRepositoryFactory repositoryLoader;
    private final StringsFactory stringsFactory;

    Strings(
            Property<Locale> localeProperty,
            StringRepositoryFactory repositoryLoader,
            StringsFactory stringsFactory) {
        this.localeProperty = localeProperty;
        this.repositoryLoader = repositoryLoader;
        this.stringsFactory = stringsFactory;
    }

    public <T> T get(Class<T> cls) {
        Property<StringRepository> observableStringRepository = new SimpleObjectProperty<>();
        RepositoryUpdater updater = new RepositoryUpdater(
                cls,
                new WeakReference<>(observableStringRepository),
                repositoryLoader
        );
        localeProperty.addListener(updater);
        updater.changed(null, null, localeProperty.getValue());
        return stringsFactory.getStrings(cls, observableStringRepository);
    }

    public void setLocale(Locale locale) {
        localeProperty.setValue(locale);
    }
}
