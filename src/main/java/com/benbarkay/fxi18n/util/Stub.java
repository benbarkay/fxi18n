package com.benbarkay.fxi18n.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicReference;

// This class is just used in testing.
public class Stub<T> {

    public interface MethodImplementation {
        Object methodCalled(Object args[]);
    }

    public static <T> Stub<T> of(Class<T> cls) {
        return new Stub<>(cls);
    }

    public Stub(Class<T> cls) {
        this.cls = cls;
    }

    private final Class<T> cls;

    public T thatReturns(Object returnValue) {
        return thatReturns(new AtomicReference<>(returnValue));
    }

    public T thatReturns(final AtomicReference ref) {
        return makeStub(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return ref.get();
            }
        });
    }

    public T withOverride(final String methodName, final MethodImplementation listener) {
        return makeStub(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals(methodName)) {
                    return listener.methodCalled(args);
                }
                return null;
            }
        });
    }

    private T makeStub(final InvocationHandler invocationHandler) {
        return cls.cast(
                Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("equals".equals(method.getName())) {
                            return System.identityHashCode(proxy) == System.identityHashCode(args[0]);
                        } else if ("hashCode".equals(method.getName())) {
                            return System.identityHashCode(proxy);
                        } else if ("toString".equals(method.getName())) {
                            return "Proxy/" + System.identityHashCode(proxy);
                        }
                        return invocationHandler.invoke(proxy, method, args);
                    }
                }));
    }
}
