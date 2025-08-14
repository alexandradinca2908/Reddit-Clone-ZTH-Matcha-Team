package org.example.apiclients;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface IApiClient {

    JsonArray handleGet();

    JsonObject handlePost(String json);

    JsonObject handlePut(String json);

    void handleDelete(String UID);

}
