package com.junction.bt.api;

import com.junction.bt.api.model.Account;
import com.junction.bt.api.model.Checkpoint;
import com.junction.bt.api.model.Event;
import com.junction.bt.api.model.Parcel;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sibirsky on 25.11.17.
 */

public class ApiService {

    private static final String API_URL = "URL";

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final ApiService ourInstance = new ApiService();

    public static ApiService getInstance() {
        return ourInstance;
    }

    private ApiService() {
    }

    public Account authorize(String login, String password) {
        return null;
    }

    public List<Parcel> getParcels(Integer userId) {
        return null;
    }

    public List<Checkpoint> getCheckpoints(List<Integer> checkpointIds) {
        return null;
    }

    public List<Event> getUnreadEvents(Integer userId) {
        return null;
    }

    public void setParcelAlias(Integer userId, Integer parcelId, String alias) {
        return;
    }
}
