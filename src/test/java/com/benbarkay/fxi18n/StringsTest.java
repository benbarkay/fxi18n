package com.benbarkay.fxi18n;

import com.benbarkay.fxi18n.formatters.DefaultStringFormatter;
import com.benbarkay.fxi18n.repositories.map.MapRepositoryFactory;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableStringValue;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Locale;

import static org.junit.Assert.*;

public class StringsTest {

    interface TestStrings {
        ObservableStringValue test();
        ObservableStringValue formattedTest(String text, int number);
    }

    private Property<Locale> localeProperty;
    private MapRepositoryFactory repositoryFactory;
    private Strings strings;

    @Before
    public void setUp() {
        localeProperty = new SimpleObjectProperty<>(Locale.getDefault());
        repositoryFactory = new MapRepositoryFactory();
        strings = Strings.builder()
                .locale(localeProperty)
                .formatter(new DefaultStringFormatter())
                .repositoryFactory(repositoryFactory)
                .build();
    }

    @Test
    public void itTranslatesText() {
        repositoryFactory.putStrings(TestStrings.class, Locale.getDefault(), Collections.singletonMap("test", "proef"));
        TestStrings testStrings = strings.get(TestStrings.class);
        assertEquals("proef", testStrings.test().getValue());
    }

    @Test
    public void itUpdatesTranslationWhenLocaleIsChanged() {
        repositoryFactory.putStrings(TestStrings.class, Locale.getDefault(), Collections.singletonMap("test", "proef"));
        repositoryFactory.putStrings(TestStrings.class, Locale.FRENCH, Collections.singletonMap("test", "tester"));
        TestStrings testStrings = strings.get(TestStrings.class);
        ObservableStringValue testString = testStrings.test();
        localeProperty.setValue(Locale.FRENCH);
        assertEquals("tester", testString.getValue());
    }

    @Test
    public void itFormatsStringsWithArguments() {
        repositoryFactory.putStrings(TestStrings.class, Locale.getDefault(),
                Collections.singletonMap("formattedTest", "%s %d"));
        TestStrings testStrings = strings.get(TestStrings.class);
        assertEquals("test 123", testStrings.formattedTest("test", 123).getValue());
    }

    @Test
    public void itFormatsStringsWithArgumentsWhenChangingLocale() {
        repositoryFactory.putStrings(TestStrings.class, Locale.getDefault(),
                Collections.singletonMap("formattedTest", "%s %d"));
        repositoryFactory.putStrings(TestStrings.class, Locale.GERMAN,
                Collections.singletonMap("formattedTest", "%2$d %1$s"));
        TestStrings testStrings = strings.get(TestStrings.class);
        ObservableStringValue formattedTest = testStrings.formattedTest("test", 123);
        strings.setLocale(Locale.GERMAN);
        assertEquals("123 test", formattedTest.getValue());
    }
}