package com.benbarkay.fxi18n.string;

import com.benbarkay.fxi18n.StringRepository;
import com.benbarkay.fxi18n.util.Stub;
import com.benbarkay.fxi18n.formatters.DefaultStringFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import org.junit.Before;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public class StringUpdaterTest {

    private StringUpdater updater;
    private StringProperty property;

    @Before
    public void setUp() {
        property = new SimpleStringProperty();
        updater = new StringUpdater(
                new WeakReference<>(property),
                "test",
                new Object[] { "five", 5 },
                new DefaultStringFormatter());
    }

    @Test
    public void itCallsRemoveListenerWhenPropertyIsGarbageCollected() {
        final AtomicReference<Object> removedListener = new AtomicReference<>();
        property = null;
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
    public void itUpdatesStringWhenRepositoryChanges() {
        updater.changed(null, null, Stub.of(StringRepository.class)
                .thatReturns("%s = %d"));
        assertEquals("five = 5", property.get());
    }



}