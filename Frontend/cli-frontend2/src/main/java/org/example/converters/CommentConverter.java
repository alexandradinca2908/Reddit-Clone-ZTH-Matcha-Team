package org.example.converters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.models.Comment;

public class CommentConverter implements IConverter<Comment> {
    private static CommentConverter instance;
    private Gson gson;

    private CommentConverter(Gson gson) {
        this.gson = gson;
    }

    public static CommentConverter getInstance(Gson gson) {
        if (instance == null) {
            instance = new CommentConverter(gson);
        }
        return instance;
    }

    public Comment convertToObject(JsonObject json) {
        return gson.fromJson(json, Comment.class);
    }

    public String convertToJson(Comment object) {
        return gson.toJson(object);
    }
}
