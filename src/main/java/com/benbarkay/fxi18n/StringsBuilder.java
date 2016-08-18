package com.benbarkay.fxi18n;

import com.benbarkay.fxi18n.formatters.DefaultStringFormatter;
import com.benbarkay.fxi18n.repositories.resourcebundle.ResourceBundleRepositoryFactory;
import com.benbarkay.fxi18n.string.StringFactory;
import com.benbarkay.fxi18n.string.proxy.ProxyStringsFactory;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Locale;

public class StringsBuilder {

    private Property<Locale> localeProperty;
    private StringRepositoryFactory repositoryFactory;
    private StringFormatter formatter;

    StringsBuilder() {
        localeProperty = new SimpleObjectProperty<Locale>();
        repositoryFactory = new ResourceBundleRepositoryFactory();
        formatter = new DefaultStringFormatter();
    }

    public StringsBuilder locale(Locale locale) {
        localeProperty.setValue(locale);
        return this;
    }

    public StringsBuilder locale(Property<Locale> localeProperty) {
        this.localeProperty = localeProperty;
        return this;
    }

    public StringsBuilder repositoryFactory(StringRepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
        return this;
    }

    public StringsBuilder formatter(StringFormatter formatter) {
        this.formatter = formatter;
        return this;
    }

    public Strings build() {
        return new Strings(
                localeProperty,
                repositoryFactory,
                new ProxyStringsFactory(new StringFactory(formatter))
        );
    }
}
