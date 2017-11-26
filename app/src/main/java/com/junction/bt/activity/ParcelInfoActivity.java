package com.junction.bt.activity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.junction.bt.R;
import com.junction.bt.api.ApiCallback;
import com.junction.bt.api.ApiService;
import com.junction.bt.api.model.ApiError;
import com.junction.bt.api.model.ApiResponse;
import com.junction.bt.api.model.Checkpoint;
import com.junction.bt.api.model.Event;
import com.junction.bt.api.model.Parcel;
import com.junction.bt.api.model.ResponseList;
import com.junction.bt.cache.CacheManager;
import com.junction.bt.context.UserContext;
import com.junction.bt.util.JsonUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParcelInfoActivity extends AppCompatActivity implements ApiCallback {

    public static final String SUBSCRIBED_ID = "SUBSCRIBED_ID";
    public static final String PARCEL_TAG = "PARCEL";

    private TextView idLabel;
    private TextView aliasLabel;
    private TextView originalWeightLabel;
    private TextView receiverLabel;

    private Parcel parcel;
    private Map<Integer, Checkpoint> checkpoints;
    private Integer subscribeId = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_info);

        idLabel = (TextView)findViewById(R.id.parcel_id);
        aliasLabel = (TextView)findViewById(R.id.parcel_alias);
        originalWeightLabel = (TextView)findViewById(R.id.parcel_original_weight);
        receiverLabel = (TextView)findViewById(R.id.parcel_receiver);

        Integer parcelId = getIntent().getIntExtra(PARCEL_TAG, 0);
        parcel = CacheManager.getInstance().getCachedParcel(parcelId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        subscribeId = ApiService.getInstance().subscribe(this);
        List<Integer> ids = new ArrayList<>();
        for (Event event : parcel.getHistory()) {
            ids.add(event.getCheckpointId());
        }
        ApiService.getInstance().getCheckpoints(subscribeId, ids, UserContext.getInstance().getToken());
    }

    @Override
    protected void onStop() {
        ApiService.getInstance().unsubscribe(subscribeId);
        super.onStop();
    }

    @Override
    public void onSuccess(ApiService.Method method, ApiResponse response) {
        ResponseList<Checkpoint> checkpoints = (ResponseList<Checkpoint>)response;
        Map<Integer, Checkpoint> checkpointMap = new HashMap<>();
        for (Checkpoint checkpoint : checkpoints.getItems()) {
            checkpointMap.put(checkpoint.getId(), checkpoint);
        }

        idLabel.setText("ID: " + parcel.getId().toString());
        aliasLabel.setText("Alias: " + parcel.getAlias());
        if (parcel.getHistory() != null && !parcel.getHistory().isEmpty()) {
            originalWeightLabel.setText("Original weight: " +
                    parcel.getHistory().get(0).getWeight().toString() + " g");
        }
        receiverLabel.setText("Receiver: " + parcel.getReceiver().getFirstName() +
            parcel.getReceiver().getSecondName());

        for (Event event : parcel.getHistory()) {
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.info_layout);
            TextView textView = new TextView(getApplicationContext());
            StringBuilder builder = new StringBuilder();
            builder.append(event.getDateTime().toString() + '\n')
                    .append(checkpointMap.get(event.getCheckpointId()).getAddress() + '\n')
                    .append(event.getDescription() + '\n')
                    .append(event.getWeight().toString() + " g\n\n");
            //textView.setTextColor(255);
            textView.setText(builder.toString());
            textView.setTextColor(Color.BLACK);
            //addContentView(textView, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT));
            linearLayout.addView(textView);
        }
    }

    @Override
    public void onError(ApiService.Method method, ApiError response) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Error while getting parcel list! " + response.getError());
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogBuilder.create().show();
    }

    private void render() {

    }
}
