package org.matcha.springbackend.response;

public class DataResponse<T> {
    private boolean success;
    private T data;

    public DataResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }
}
