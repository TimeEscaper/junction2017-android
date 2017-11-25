package com.junction.bt.api;

import com.junction.bt.api.model.Account;
import com.junction.bt.api.model.Checkpoint;
import com.junction.bt.api.model.Event;
import com.junction.bt.api.model.Parcel;
import com.junction.bt.exception.AuthException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sibirsky on 25.11.17.
 */

public class ApiService {

    private static final String API_URL = "http://api.my.com/";

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

    public Account authorize(String login, String password) throws AuthException {
        Account account = new Account();
        account.setToken("mockToken");
        return account;
    }

    public boolean checkToken(String token) {
        return false;
    }

    public List<Parcel> getParcels(String token) {
        List<Parcel> parcels = new ArrayList<>();
        Parcel parcel1 = new Parcel();
        parcel1.setParcelId(1);
        parcel1.setAlias("Parcel 1");
        Parcel parcel2 = new Parcel();
        parcel2.setParcelId(2);
        parcel2.setAlias("Parcel 2");
        parcels.add(parcel1);
        parcels.add(parcel2);

        return parcels;
    }

    public List<Checkpoint> getCheckpoints(List<Integer> checkpointIds) {
        return null;
    }

    public List<Event> getUnreadEvents(String token) {
        return null;
    }

    public void setParcelAlias(String token, Integer parcelId, String alias) {
        return;
    }
}
