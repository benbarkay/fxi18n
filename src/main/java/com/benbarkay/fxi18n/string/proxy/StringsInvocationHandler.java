package com.benbarkay.fxi18n.string.proxy;

import com.benbarkay.fxi18n.StringRepository;
import com.benbarkay.fxi18n.string.StringFactory;
import javafx.beans.value.ObservableValue;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class StringsInvocationHandler implements InvocationHandler {

    private final ObservableValue<StringRepository> observableStringRepository;
    private final StringFactory stringFactory;

    StringsInvocationHandler(
            ObservableValue<StringRepository> observableStringRepository,
            StringFactory stringFactory) {
        this.observableStringRepository = observableStringRepository;
        this.stringFactory = stringFactory;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return stringFactory.getString(method.getName(), args, observableStringRepository);
    }
}
