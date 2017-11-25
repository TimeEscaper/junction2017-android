package com.junction.bt.api;

import com.junction.bt.api.model.Account;
import com.junction.bt.api.model.ApiError;
import com.junction.bt.api.model.ApiResponse;
import com.junction.bt.api.model.Checkpoint;
import com.junction.bt.api.model.Event;
import com.junction.bt.api.model.Parcel;
import com.junction.bt.api.model.ResponseList;
import com.junction.bt.api.model.TokenStatus;
import com.junction.bt.api.model.request.AuthRequest;
import com.junction.bt.exception.AuthException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sibirsky on 25.11.17.
 */

public class ApiService {

    private static final String API_URL = "http://40.69.84.9:8080/simple/";

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private static final ApiService ourInstance = new ApiService();

    private ApiInterface api;

    private Map<Integer, ApiCacheItem> apiCache = new HashMap<>();
    private Map<Integer, ApiCallback> apiCallbacks = new ConcurrentHashMap<>();

    public static ApiService getInstance() {
        return ourInstance;
    }

    private ApiService() {
        api = RETROFIT.create(ApiInterface.class);
    }

    public boolean checkCache(ApiCallback apiCallback, Integer subscribeId) {
        ApiCacheItem cachedResponse = apiCache.get(subscribeId);
        if (cachedResponse == null) {
            return false;
        }
        apiCache.remove(subscribeId);
        if (cachedResponse.getEntity() instanceof ApiError) {
            apiCallback.onError(cachedResponse.getMethod(), (ApiError)cachedResponse.getEntity());
        } else {
            apiCallback.onSuccess(cachedResponse.getMethod(), cachedResponse.getEntity());
        }
        apiCallbacks.remove(subscribeId);
        return true;
    }

    public boolean checkPending(ApiCallback apiCallback, Integer subscribedId) {
        if (apiCallbacks.containsKey(subscribedId)) {
            apiCallbacks.put(subscribedId, apiCallback);
            return true;
        }
        return false;
    }

    public Integer subscribe(ApiCallback apiCallback) {
        Integer subscribedId = ID_GENERATOR.getAndIncrement();
        apiCallbacks.put(subscribedId, apiCallback);
        return subscribedId;
    }

    public void unsubscribeTemp(Integer subscribedId) {
        apiCallbacks.put(subscribedId, null);
    }

    public void unsubscribe(Integer subscribedId) {
        apiCallbacks.remove(subscribedId);
        apiCache.remove(subscribedId);
    }

    public void authorize(final Integer subscribedId, String login, String password) {
        Call<Account> call = api.auth(new AuthRequest(login, password));
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                ApiResponse apiResponse;
                boolean isError;
                if (response.isSuccessful()) {
                    apiResponse = response.body();
                    isError = false;
                } else {
                    apiResponse = new ApiError();
                    isError = true;
;               }
                ApiCallback apiCallback = apiCallbacks.get(subscribedId);
                if (apiCallback != null) {
                    if (!isError) {
                        apiCallback.onSuccess(Method.AUTHORIZE, apiResponse);
                    } else {
                        apiCallback.onError(Method.AUTHORIZE, (ApiError)apiResponse);
                    }
                } else {
                    if (apiCallbacks.containsKey(subscribedId)) {
                        apiCache.put(subscribedId, new ApiCacheItem(apiResponse, Method.AUTHORIZE));
                    }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                ApiError apiError = new ApiError(t.getMessage());
                ApiCallback apiCallback = apiCallbacks.get(subscribedId);
                if (apiCallback != null) {
                    apiCallback.onError(Method.AUTHORIZE, apiError);
                } else {
                    if (apiCallbacks.containsKey(subscribedId)) {
                        apiCache.put(subscribedId, new ApiCacheItem(apiError, Method.AUTHORIZE));
                    }
                }
            }
        });
    }

    public void checkToken(final Integer subscribedId, String token) {
        Call<TokenStatus> call = api.checkToken(token);
        call.enqueue(new Callback<TokenStatus>() {
            @Override
            public void onResponse(Call<TokenStatus> call, Response<TokenStatus> response) {
                ApiResponse apiResponse;
                boolean isError;
                if (response.isSuccessful()) {
                    apiResponse = response.body();
                    isError = false;
                } else {
                    apiResponse = new ApiError();
                    isError = true;
                }
                ApiCallback apiCallback = apiCallbacks.get(subscribedId);
                if (apiCallback != null) {
                    if (!isError) {
                        apiCallback.onSuccess(Method.CHECK_TOKEN, apiResponse);
                    } else {
                        apiCallback.onError(Method.CHECK_TOKEN, (ApiError)apiResponse);
                    }
                } else {
                    if (apiCallbacks.containsKey(subscribedId)) {
                        apiCache.put(subscribedId, new ApiCacheItem(apiResponse, Method.AUTHORIZE));
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenStatus> call, Throwable t) {
                ApiError apiError = new ApiError(t.getMessage());
                ApiCallback apiCallback = apiCallbacks.get(subscribedId);
                if (apiCallback != null) {
                    apiCallback.onError(Method.CHECK_TOKEN, apiError);
                } else {
                    if (apiCallbacks.containsKey(subscribedId)) {
                        apiCache.put(subscribedId, new ApiCacheItem(apiError, Method.CHECK_TOKEN));
                    }
                }
            }
        });
    }

    public void getParcels(final Integer subscribedId, String token) {
        Call<ResponseList<Parcel>> call = api.getParcels(token);
        call.enqueue(new Callback<ResponseList<Parcel>>() {
            @Override
            public void onResponse(Call<ResponseList<Parcel>> call, Response<ResponseList<Parcel>> response) {
                ApiResponse apiResponse;
                boolean isError;
                if (response.isSuccessful()) {
                    apiResponse = response.body();
                    isError = false;
                } else {
                    apiResponse = new ApiError();
                    isError = true;
                    ;               }
                ApiCallback apiCallback = apiCallbacks.get(subscribedId);
                if (apiCallback != null) {
                    if (!isError) {
                        apiCallback.onSuccess(Method.GET_PARCELS, apiResponse);
                    } else {
                        apiCallback.onError(Method.GET_PARCELS, (ApiError)apiResponse);
                    }
                } else {
                    if (apiCallbacks.containsKey(subscribedId)) {
                        apiCache.put(subscribedId, new ApiCacheItem(apiResponse, Method.GET_PARCELS));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseList<Parcel>> call, Throwable t) {
                ApiError apiError = new ApiError(t.getMessage());
                ApiCallback apiCallback = apiCallbacks.get(subscribedId);
                if (apiCallback != null) {
                    apiCallback.onError(Method.GET_PARCELS, apiError);
                } else {
                    if (apiCallbacks.containsKey(subscribedId)) {
                        apiCache.put(subscribedId, new ApiCacheItem(apiError, Method.GET_PARCELS));
                    }
                }
            }
        });
    }

    public void getCheckpoints(final Integer subscribedId, List<Integer> checkpointIds, String token) {
        return;
    }

    public List<Event> getUnreadEvents(String token) {
        return null;
    }

    public void setParcelAlias(String token, Integer parcelId, String alias) {
        return;
    }

    public enum Method {
        AUTHORIZE,
        CHECK_TOKEN,
        GET_PARCELS,
        GET_CHECKPOINTS,
        GET_UNREAD_EVENTS,
        SET_PARCEL_ALIAS
    }
}
