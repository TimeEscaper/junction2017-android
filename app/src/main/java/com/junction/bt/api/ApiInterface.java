package com.junction.bt.api;

import com.junction.bt.api.model.Account;
import com.junction.bt.api.model.Checkpoint;
import com.junction.bt.api.model.Event;
import com.junction.bt.api.model.Parcel;
import com.junction.bt.api.model.ResponseList;
import com.junction.bt.api.model.TokenStatus;
import com.junction.bt.api.model.request.AuthRequest;
import com.junction.bt.api.model.request.ParcelAliasRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sibirsky on 25.11.17.
 */

public interface ApiInterface {

    @Headers({"Accept: application/json"})
    @POST("/simple/login")
    Call<Account> auth(@Body AuthRequest request);

    @GET("/simple/checktoken")
    Call<TokenStatus> checkToken(@Query("token") String token);

    @POST("/simple/account")
    Call<ResponseList<Parcel>> getParcels(@Query("token") String token,
                                          @Query("type") String type);

    @POST("/simple/account")
    Call<ResponseList<Checkpoint>> getCheckpoints(@Query("token") String token,
                                                  @Query("type") String type,
                                                  @Body Integer[] ids);

    @GET("/simple/events/unread")
    Call<ResponseList<Event>> getUnreadEvents(@Query("token") String token);

    @POST("/simple/parcel/alias")
    Call<Parcel> setParcelAlias(@Body ParcelAliasRequest request);
}
