package com.junction.bt.activity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            List<Integer> checkpointIds = new ArrayList<>();
            for (Event event : parcel.getHistory()) {
                checkpointIds.add(event.getCheckpointId());
            }
            ApiService.getInstance().getCheckpoints(subscribeId, checkpointIds,
                    UserContext.getInstance().getToken());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        ApiService.getInstance().unsubscribeTemp(subscribeId);
        savedInstanceState.putInt(SUBSCRIBED_ID, subscribeId);
    }

    @Override
    protected void onDestroy() {
        ApiService.getInstance().unsubscribe(subscribeId);
        super.onDestroy();
    }

    @Override
    public void onSuccess(ApiService.Method method, ApiResponse response) {
        ResponseList<Checkpoint> checkpoints = (ResponseList<Checkpoint>)response;
        Map<Integer, Checkpoint> checkpointMap = new HashMap<>();
        for (Checkpoint checkpoint : checkpoints.getItems()) {
            checkpointMap.put(checkpoint.getCheckpointId(), checkpoint);
        }

        idLabel.setText("ID: " + parcel.getParcelId().toString());
        aliasLabel.setText("Alias: " + parcel.getAlias());
        originalWeightLabel.setText("Original weight: " + parcel.getWeight().toString());
        receiverLabel.setText("Receiver: " + parcel.getReceiver().getFirstName() +
            parcel.getReceiver().getSecondName());

        for (Event event : parcel.getHistory()) {
            TextView textView = new TextView(getApplicationContext());
            StringBuilder builder = new StringBuilder();
            builder.append(event.getDateTime().toString() + '\n')
                    .append(checkpointMap.get(event.getCheckpointId()).getAddress() + '\n')
                    .append(event.getDescription() + '\n')
                    .append(event.getWeight().toString() + " g\n\n");
            textView.setText(builder.toString());
            addContentView(textView, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT));
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

    private void render() {

    }
}
