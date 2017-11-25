package com.junction.bt.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.junction.bt.R;
import com.junction.bt.activity.adapter.ParcelsAdapter;
import com.junction.bt.api.ApiService;
import com.junction.bt.api.model.Parcel;
import com.junction.bt.context.UserContext;

import java.util.List;

public class ParcelsListActivity extends AppCompatActivity {

    private RecyclerView parcelsView;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcels_list);

        token = UserContext.getInstance().getToken();

        List<Parcel> parcels = ApiService.getInstance().getParcels(token);
        if (!parcels.isEmpty()) {
            parcelsView = (RecyclerView)findViewById(R.id.parcels);
            parcelsView.setLayoutManager(new LinearLayoutManager(this));
            parcelsView.setAdapter(new ParcelsAdapter(this, parcels));
        } else {
            Toast.makeText(getApplicationContext(), "No parcels yet!", Toast.LENGTH_LONG);
        }
    }
}
