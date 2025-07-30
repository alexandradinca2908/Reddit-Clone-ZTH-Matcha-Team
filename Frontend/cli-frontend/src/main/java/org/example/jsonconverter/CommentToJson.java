package org.example.jsonconverter;

import com.google.gson.Gson;
import org.example.dto.CommentDTO;
import org.example.models.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CommentToJson {
    private static CommentToJson instance;

    public static CommentToJson getInstance() {
        if(instance == null) {
            instance = new CommentToJson();
        }
        return instance;
    }

//    public static CommentDTO mapCommentToDTO(Comment comment) {
//        LocalDateTime now = LocalDateTime.now();
//        CommentDTO commentDTO;
//        if(comment.getReplyList().isEmpty()) {
//            commentDTO = new CommentDTO(
//                    comment.getCommentUID(),
//                    comment.getParentPost().getPostUID(),
//                    comment.getParentPost().getPostUID(),
//                    comment.getCommentText(),
//                    comment.getParentUser().getUsername(),
//                    0,//TODO cu baiatul de la front
//                    0, //TODO cu baiatul de la front
//                    comment.getVotes(),
//                    "?", //TODO
//                    now.toString(),
//                    now.toString(),
//                    new ArrayList<CommentDTO>()
//
//            );
//        } else {
//            ArrayList<CommentDTO> replyDTO = new ArrayList<>();
//            for(Comment reply : comment.getReplyList()) {
//                replyDTO.add(mapCommentToDTO(reply));
//            }
//            commentDTO = new CommentDTO(
//                    comment.getCommentUID(),
//                    comment.getParentPost().getPostUID(),
//                    comment.getParentPost().getPostUID(),
//                    comment.getCommentText(),
//                    comment.getParentUser().getUsername(),
//                    0,//TODO cu baiatul de la front
//                    0, //TODO cu baiatul de la front
//                    comment.getVotes(),
//                    "?", //TODO
//                    now.toString(),
//                    now.toString(),
//                    replyDTO
//            );
//        }
//
//        return commentDTO;
//    }

//    public String convert(Comment comment) {
//        CommentDTO commentDTO = CommentToJson.mapCommentToDTO(comment);
//        Gson gson = new Gson();
//        return gson.toJson(commentDTO);
//    }
}
