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

    private static final String API_URL = "http://10.100.17.77:8080";

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private static final ApiService ourInstance = new ApiService();

    private ApiInterface api;

    private Map<Integer, ApiCallback> apiCallbacks = new ConcurrentHashMap<>();

    public static ApiService getInstance() {
        return ourInstance;
    }

    private ApiService() {
        api = RETROFIT.create(ApiInterface.class);
    }

    public Integer subscribe(ApiCallback apiCallback) {
        Integer subscribedId = ID_GENERATOR.getAndIncrement();
        apiCallbacks.put(subscribedId, apiCallback);
        return subscribedId;
    }


    public void unsubscribe(Integer subscribedId) {
        apiCallbacks.remove(subscribedId);
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
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                ApiError apiError = new ApiError(t.getMessage());
                ApiCallback apiCallback = apiCallbacks.get(subscribedId);
                if (apiCallback != null) {
                    apiCallback.onError(Method.AUTHORIZE, apiError);
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
                }
            }

            @Override
            public void onFailure(Call<TokenStatus> call, Throwable t) {
                ApiError apiError = new ApiError(t.getMessage());
                ApiCallback apiCallback = apiCallbacks.get(subscribedId);
                if (apiCallback != null) {
                    apiCallback.onError(Method.CHECK_TOKEN, apiError);
                }
            }
        });
    }

    public void getParcels(final Integer subscribedId, String token) {
        Call<ResponseList<Parcel>> call = api.getParcels(token, "parcels");
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
                }
            }

            @Override
            public void onFailure(Call<ResponseList<Parcel>> call, Throwable t) {
                ApiError apiError = new ApiError(t.getMessage());
                ApiCallback apiCallback = apiCallbacks.get(subscribedId);
                if (apiCallback != null) {
                    apiCallback.onError(Method.GET_PARCELS, apiError);
                }
            }
        });
    }

    public void getCheckpoints(final Integer subscribedId, List<Integer> checkpointIds, String token) {
        Call<ResponseList<Checkpoint>> call = api.getCheckpoints(token, "checkpoints",
                checkpointIds.toArray(new Integer[checkpointIds.size()]));
        call.enqueue(new Callback<ResponseList<Checkpoint>>() {
            @Override
            public void onResponse(Call<ResponseList<Checkpoint>> call, Response<ResponseList<Checkpoint>> response) {
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
                        apiCallback.onSuccess(Method.GET_PARCELS, apiResponse);
                    } else {
                        apiCallback.onError(Method.GET_PARCELS, (ApiError)apiResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseList<Checkpoint>> call, Throwable t) {
                ApiError apiError = new ApiError(t.getMessage());
                ApiCallback apiCallback = apiCallbacks.get(subscribedId);
                if (apiCallback != null) {
                    apiCallback.onError(Method.GET_CHECKPOINTS, apiError);
                }
            }
        });
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
