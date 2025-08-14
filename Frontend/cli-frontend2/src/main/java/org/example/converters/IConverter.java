package org.example.converters;

import com.google.gson.JsonObject;

public interface IConverter<T> {
    T convertToObject(JsonObject json);

    String convertToJson(T object);
}
