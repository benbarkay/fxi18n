package com.benbarkay.fxi18n.string;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class FormatArgumentsTest {
    @Test
    public void itObtainsValueFromObservables() {
        assertArrayEquals(new Object[] { "test", 7357 }, FormatArguments.forArguments(new Object[] {
            new SimpleStringProperty("test"), new SimpleIntegerProperty(7357)
        }).getValue());
    }

    @Test
    public void itUsesValuesThatAreNotObservablePlainly() {
        assertArrayEquals(new Object[] { "test", 7357 }, FormatArguments.forArguments(new Object[] {
                "test", 7357
        }).getValue());
    }

    @Test
    public void itUpdatesValueWhenPropertyIsChanged() {
        Property<String> property = new SimpleStringProperty("foo");
        ObservableValue<Object[]> formatArguments = FormatArguments.forArguments(new Object[] {property});
        property.setValue("bar");
        assertArrayEquals(new Object[] {"bar"}, formatArguments.getValue());
    }

    @Test
    public void itFiresInvalidationEventWhenPropertyArgumentIsChanged() {
        final AtomicReference<Object[]> ref = new AtomicReference<>();
        Property<String> property = new SimpleStringProperty("foo");
        ObservableValue<Object[]> formatArguments = FormatArguments.forArguments(new Object[] {property});
        formatArguments.addListener(new ChangeListener<Object[]>() {
            @Override
            public void changed(ObservableValue<? extends Object[]> observable, Object[] oldValue, Object[] newValue) {
                ref.set(newValue);
            }
        });
        property.setValue("bar");
        assertArrayEquals(new Object[] {"bar"}, ref.get());
    }
}