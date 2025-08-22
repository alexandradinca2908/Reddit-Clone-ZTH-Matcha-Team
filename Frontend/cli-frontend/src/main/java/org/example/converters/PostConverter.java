package org.example.converters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.models.Post;

public class PostConverter implements IConverter<Post> {
    private static PostConverter instance;
    private Gson gson;

    private PostConverter(Gson gson) {
        this.gson = gson;
    }

    public static PostConverter getInstance(Gson gson) {
        if (instance == null) {
            instance = new PostConverter(gson);
        }
        return instance;
    }

    public Post convertToObject(JsonObject json) {
        return gson.fromJson(json, Post.class);
    }

    public String convertToJson(Post post) {
        return gson.toJson(post);
    }
}
