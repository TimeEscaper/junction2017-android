package com.junction.bt.api;

import com.junction.bt.api.model.Account;
import com.junction.bt.api.model.Checkpoint;
import com.junction.bt.api.model.Event;
import com.junction.bt.api.model.Parcel;
import com.junction.bt.api.model.request.AuthRequest;
import com.junction.bt.api.model.request.ParcelAliasRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sibirsky on 25.11.17.
 */

public interface ApiInterface {

    @POST("/api/auth")
    Call<Account> auth(@Body AuthRequest request);

    @GET("/api/auth/check")
    Call<Boolean> checkToken(@Query("token") String token);

    @GET("/api/parcels")
    Call<List<Parcel>> getParcels(@Query("token") String token);

    @GET("/api/checkpoints")
    Call<List<Checkpoint>> getCheckpoints(@Query("ids") Integer[] ids,
                                          @Query("token") String token);

    @GET("/api/events/unread")
    Call<List<Event>> getUnreadEvents(@Query("token") String token);

    @POST("/api/parcel/alias")
    Call<Parcel> setParcelAlias(@Body ParcelAliasRequest request);
}
