package com.benbarkay.fxi18n.repositories.map;

import com.benbarkay.fxi18n.StringRepository;
import com.benbarkay.fxi18n.StringRepositoryFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MapRepositoryFactory implements StringRepositoryFactory {

    private static class Key {
        private final Class cls;
        private final Locale locale;

        private Key(Class cls, Locale locale) {
            this.cls = cls;
            this.locale = locale;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;

            Key key = (Key) o;

            if (cls != null ? !cls.equals(key.cls) : key.cls != null) return false;
            return locale != null ? locale.equals(key.locale) : key.locale == null;

        }

        @Override
        public int hashCode() {
            int result = cls != null ? cls.hashCode() : 0;
            result = 31 * result + (locale != null ? locale.hashCode() : 0);
            return result;
        }
    }

    private final Map<Key,Map<String,String>> maps;

    public MapRepositoryFactory() {
        this.maps = new HashMap<>();
    }

    @Override
    public StringRepository getRepository(Class cls, Locale locale) {
        Map<String,String> strings = maps.get(new Key(cls, locale));
        if (strings == null) {
            strings = Collections.emptyMap();
        }
        return new MapStringRepository(strings);
    }

    public void putStrings(Class cls, Locale locale, Map<String,String> map) {
        maps.put(new Key(cls, locale), map);
    }
}
