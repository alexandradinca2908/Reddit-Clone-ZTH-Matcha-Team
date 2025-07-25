package org.matcha.springbackend.response;

public class SubredditResponse extends DataResponse<SubredditResponse> {
    private int total;

    public SubredditResponse(boolean success, SubredditResponse data, int total) {
        super(success, data);
        this.total = total;
    }
}
