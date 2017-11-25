package com.junction.bt.api;

import com.junction.bt.api.model.ApiResponse;

/**
 * Created by sibirsky on 25.11.17.
 */

public class ApiCacheItem {

    private ApiResponse entity;
    private ApiService.Method method;

    public ApiCacheItem(ApiResponse entity, ApiService.Method method) {
        this.entity = entity;
        this.method = method;
    }

    public ApiResponse getEntity() {
        return entity;
    }

    public void setEntity(ApiResponse entity) {
        this.entity = entity;
    }

    public ApiService.Method getMethod() {
        return method;
    }

    public void setMethod(ApiService.Method method) {
        this.method = method;
    }
}
