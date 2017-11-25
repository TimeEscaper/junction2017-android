package com.junction.bt.api;

import com.junction.bt.api.model.ApiError;
import com.junction.bt.api.model.ApiResponse;

import retrofit2.Response;

/**
 * Created by sibirsky on 25.11.17.
 */

public interface ApiCallback {

    public void onSuccess(ApiService.Method method, ApiResponse response);

    public void onError(ApiService.Method method, ApiError response);
}
