package com.latoken.api.client.v2;

import java.util.Collection;
import java.util.Map;

final class Validation {
    static <T> T checkNotNull(T obj) {
        if (obj == null) throw new NullPointerException();
        return obj;
    }

    static boolean notNullOrEmpty(String key) {
        return key != null && !key.isEmpty();
    }

    static <T extends Collection<?>> T checkNotEmpty(T collection) {
        if (isNullOrEmpty(collection)) {
            throw new IllegalStateException("collection is empty");
        }
        return collection;
    }

    static <T extends Map<?, ?>> T checkNotEmpty(T map) {
        if (isNullOrEmpty(map)) {
            throw new IllegalStateException("map is empty");
        }
        return map;
    }

    static <T extends Collection> boolean isNullOrEmpty(T collection) {
        return collection == null || collection.isEmpty();
    }

    static <T extends Map<?, ?>> boolean isNullOrEmpty(T map) {
        return map == null || map.isEmpty();
    }
}
