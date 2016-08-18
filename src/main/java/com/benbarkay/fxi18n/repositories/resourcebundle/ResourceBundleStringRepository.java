package com.benbarkay.fxi18n.repositories.resourcebundle;

import com.benbarkay.fxi18n.StringRepository;

import java.util.ResourceBundle;

class ResourceBundleStringRepository implements StringRepository{

    private final ResourceBundle resourceBundle;

    ResourceBundleStringRepository(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}
