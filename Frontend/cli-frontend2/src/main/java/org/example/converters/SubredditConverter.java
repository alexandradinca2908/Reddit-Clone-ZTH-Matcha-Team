package org.example.converters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.models.Subreddit;

public class SubredditConverter implements IConverter<Subreddit> {
    private static SubredditConverter instance;
    private Gson gson;

    private SubredditConverter(Gson gson) {
        this.gson = gson;
    }

    public static SubredditConverter getInstance(Gson gson) {
        if (instance == null) {
            instance = new SubredditConverter(gson);
        }
        return instance;
    }

    public Subreddit convertToObject(JsonObject object) {
        return gson.fromJson(object, Subreddit.class);
    }

    public String convertToJson(Subreddit object) {
        return gson.toJson(object);
    }
}
