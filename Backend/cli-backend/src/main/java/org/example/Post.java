package org.example;
import java.util.ArrayList;

public class Post implements Likeable{
    private static ArrayList<Post> posts = new ArrayList<Post>();
    static int postsCounter = 0;
    int commentsCounter;
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
        this.commentsCounter = 0;
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

    public void showFeed() {
        for  (Post iter : posts) {
            System.out.println(iter.title);
            System.out.println();
            System.out.println(iter.body.substring(0, 40).concat("..."));
            System.out.println();
            System.out.print("UP ".concat(String.valueOf(iter.voteCount)));
            System.out.println("DOWN");
            System.out.println("---");
        }
    }

    public void expandPost(int postID) {
        for (Post iter : posts) {
            System.out.println(iter.title);
            System.out.println();
            System.out.println(iter.body);
            System.out.println();
            System.out.print("UP ".concat(String.valueOf(iter.voteCount)));
            System.out.println("DOWN");
            System.out.println();
        }
    }


}

