package com.benbarkay.fxi18n.repositories.resourcebundle;

import com.benbarkay.fxi18n.StringRepository;
import com.benbarkay.fxi18n.StringRepositoryFactory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Constructs {@link StringRepository} instances that are backed by a
 * {@link ResourceBundle}. This factory locates the resource bundles
 * by using the specified class' canonical name, the specified {@code locale},
 * and its class loader.
 */
public class ResourceBundleRepositoryFactory implements StringRepositoryFactory {
    @Override
    public StringRepository getRepository(Class cls, Locale locale) {
        ResourceBundle resourceBundle;
        if (locale == null) {
            resourceBundle = ResourceBundle.getBundle(cls.getCanonicalName());
        } else {
            resourceBundle = ResourceBundle.getBundle(cls.getCanonicalName(), locale);
        }
        return new ResourceBundleStringRepository(resourceBundle);
    }
}
