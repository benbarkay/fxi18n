package com.benbarkay.fxi18n;

import com.benbarkay.fxi18n.util.Stub;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import org.junit.Before;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public class RepositoryUpdaterTest {
    private Property<StringRepository> repositoryProperty;

    @Before
    public void setUp() {
        repositoryProperty = new SimpleObjectProperty<>();
    }

    @Test
    public void itRemovesItselfWhenTargetPropertyIsGarbageCollected() {
        RepositoryUpdater updater = new RepositoryUpdater(
                null,
                new WeakReference<>(repositoryProperty),
                null
        );
        final AtomicReference<Object> removedListener = new AtomicReference<>();
        repositoryProperty = null;
        System.gc();
        // noinspection unchecked
        updater.changed(Stub.of(ObservableValue.class)
            .withOverride("removeListener", new Stub.MethodImplementation() {
                @Override
                public Object methodCalled(Object[] args) {
                    removedListener.set(args[0]);
                    return null;
                }
            }), null, null);
        assertEquals(updater, removedListener.get());
    }

    @Test
    public void itUpdatesRepositoryWhenLocaleChanges() {
        StringRepository expected = Stub.of(StringRepository.class)
                .thatReturns(null);
        RepositoryUpdater updater = new RepositoryUpdater(
                null,
                new WeakReference<>(repositoryProperty),
                Stub.of(StringRepositoryFactory.class).thatReturns(expected)
        );
        updater.changed(null, null, null);
        assertEquals(expected, repositoryProperty.getValue());
    }
}