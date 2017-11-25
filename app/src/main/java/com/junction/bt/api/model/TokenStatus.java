package com.junction.bt.api.model;

/**
 * Created by sibirsky on 25.11.17.
 */

public class TokenStatus implements ApiResponse {

    private Boolean isActive;

    public TokenStatus() { }

    public TokenStatus(boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
