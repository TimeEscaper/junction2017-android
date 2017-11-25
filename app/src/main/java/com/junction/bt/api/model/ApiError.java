package com.junction.bt.api.model;

/**
 * Created by sibirsky on 25.11.17.
 */

public class ApiError implements ApiResponse {

    private String error;

    public ApiError() { }

    public ApiError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
