package org.example.models;

public class Image {
    private String imageURL;
    private int imageID;
    private static int imageCounter = 0;

    public Image(String imageURL) {
        this.imageURL = imageURL;
        imageID = imageCounter++;
    }

    public int getImageID() {
        return imageID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

}
