package com.benbarkay.fxi18n.repositories.map;

import com.benbarkay.fxi18n.StringRepository;

import java.util.Map;

class MapStringRepository implements StringRepository {

    private final Map<String,String> map;

    MapStringRepository(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String getString(String key) {
        return map.get(key);
    }
}
