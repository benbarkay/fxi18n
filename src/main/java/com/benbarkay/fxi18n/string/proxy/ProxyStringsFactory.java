package com.benbarkay.fxi18n.string.proxy;

import com.benbarkay.fxi18n.StringRepository;
import com.benbarkay.fxi18n.StringsFactory;
import com.benbarkay.fxi18n.string.StringFactory;
import javafx.beans.value.ObservableValue;

import java.lang.reflect.Proxy;

public class ProxyStringsFactory implements StringsFactory {

    private final StringFactory stringFactory;

    public ProxyStringsFactory(StringFactory stringFactory) {
        this.stringFactory = stringFactory;
    }

    @Override
    public <T> T getStrings(Class<T> cls, ObservableValue<StringRepository> observableStringRepository) {
        return cls.cast(Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[]{cls},
                new StringsInvocationHandler(
                        observableStringRepository,
                        stringFactory)));
    }
}
