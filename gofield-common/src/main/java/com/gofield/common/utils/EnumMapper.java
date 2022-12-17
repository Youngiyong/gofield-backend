package com.gofield.common.utils;

import com.gofield.common.model.EnumCodeModel;
import com.gofield.common.model.EnumModel;
import com.gofield.common.model.EnumValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnumMapper {

    private final Map<String, List<EnumValue>> factory = new HashMap<>();

    private List<EnumValue> toEnumValues(Class<? extends EnumCodeModel> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(EnumValue::of)
                .collect(Collectors.toList());
    }

    public void put(String key, Class<? extends EnumCodeModel> e) {
        factory.put(key, toEnumValues(e));
    }

    private List<EnumValue> enumModelToEnumValues(Class<? extends EnumModel> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(EnumValue::of)
                .collect(Collectors.toList());
    }

    public void putEnumModel(String key, Class<? extends EnumModel> e) {
        factory.put(key, enumModelToEnumValues(e));
    }

    public Map<String, List<EnumValue>> getAll() {
        return factory;
    }

    public List<EnumValue> get(String key) {
        return factory.get(key);
    }


}