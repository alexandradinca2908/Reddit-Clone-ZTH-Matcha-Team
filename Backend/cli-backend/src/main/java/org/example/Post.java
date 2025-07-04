package org.example;
import java.util.ArrayList;

public class Post implements Likeable{
    private static ArrayList<Post> posts = new ArrayList<Post>();
    static int postsCounter = 0;
    int postID;
    int ownershipID;
    String title;
    String body;
    int voteCount;

    @Override
    public void upvote() {
        voteCount++;
    }
    @Override
    public void downvote() {
        voteCount--;
    }
    @Override
    public int getVotes() {
        return voteCount;
    }

    public Post(String title, String body, int ownershipID) {
        this.title = title;
        this.body = body;
        this.voteCount = 0;
        this.ownershipID = ownershipID;
        postID = postsCounter++;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void deletePost(int postID) {
        for (Post iter : posts) {
            if (iter.postID == postID) {
                posts.remove(iter);
            }
        }
    }
}

