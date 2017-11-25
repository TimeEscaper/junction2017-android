package com.junction.bt.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.junction.bt.R;
import com.junction.bt.activity.adapter.ParcelsAdapter;
import com.junction.bt.api.ApiCallback;
import com.junction.bt.api.ApiService;
import com.junction.bt.api.model.ApiError;
import com.junction.bt.api.model.ApiResponse;
import com.junction.bt.api.model.Parcel;
import com.junction.bt.api.model.ResponseList;
import com.junction.bt.cache.CacheManager;
import com.junction.bt.context.UserContext;

import java.util.List;

public class ParcelsListActivity extends AppCompatActivity implements ApiCallback {

    public static final String SUBSCRIBED_ID = "SUBSCRIBED_ID";

    private RecyclerView parcelsView;

    private Integer subscribeId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcels_list);

        parcelsView = (RecyclerView)findViewById(R.id.parcels);

        if (savedInstanceState != null) {
            subscribeId = savedInstanceState.getInt(SUBSCRIBED_ID);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (subscribeId != null) {
            boolean isCached = ApiService.getInstance().checkCache(this, subscribeId);
            if (!isCached) {
                boolean isPending = ApiService.getInstance().checkPending(this, subscribeId);
                if (!isPending) {
                    subscribeId = ApiService.getInstance().subscribe(this);
                }
            }
        } else {
            subscribeId = ApiService.getInstance().subscribe(this);
            ApiService.getInstance().getParcels(subscribeId, UserContext.getInstance().getToken());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (subscribeId != null) {
            ApiService.getInstance().unsubscribeTemp(subscribeId);
            savedInstanceState.putInt(SUBSCRIBED_ID, subscribeId);
        }
    }

    @Override
    protected void onDestroy() {
        if (subscribeId != null) {
            ApiService.getInstance().unsubscribe(subscribeId);
        }
        super.onDestroy();
    }

    @Override
    public void onSuccess(ApiService.Method method, ApiResponse response) {
        ResponseList<Parcel> parcelsList = (ResponseList<Parcel>)response;
        CacheManager.getInstance().putParcels(parcelsList.getItems());
        if (!parcelsList.getItems().isEmpty()) {
            parcelsView.setLayoutManager(new LinearLayoutManager(this));
            parcelsView.setAdapter(new ParcelsAdapter(this, parcelsList.getItems()));
        }
    }

    @Override
    public void onError(ApiService.Method method, ApiError response) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Error while checking token!" + response.getError());
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogBuilder.create().show();
    }
}
