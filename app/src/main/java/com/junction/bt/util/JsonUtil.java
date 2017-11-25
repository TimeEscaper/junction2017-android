package com.junction.bt.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by sibirsky on 25.11.17.
 */

public final class JsonUtil {

    private static final Gson GSON = new GsonBuilder().create();

    private JsonUtil() { }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }
}
