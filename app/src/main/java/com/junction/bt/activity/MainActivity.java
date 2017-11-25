package com.junction.bt.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.junction.bt.R;
import com.junction.bt.api.ApiCallback;
import com.junction.bt.api.ApiService;
import com.junction.bt.api.model.Account;
import com.junction.bt.api.model.ApiError;
import com.junction.bt.api.model.ApiResponse;
import com.junction.bt.api.model.TokenStatus;
import com.junction.bt.cache.CacheManager;
import com.junction.bt.context.UserContext;
import com.junction.bt.util.JsonUtil;

public class MainActivity extends AppCompatActivity implements ApiCallback {

    public static final String SUBSCRIBED_ID = "SUBSCRIBED_ID";

    private Integer subscribeId = null;

    private Account account = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            Account account = getAccountFromCache();
            if (account == null) {
                openLogin();
            } else {
                this.account = account;
                subscribeId = ApiService.getInstance().subscribe(this);
                String token = account.getToken();
                ApiService.getInstance().checkToken(subscribeId, token);
            }
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

    private void openLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void openParcels() { startActivity(new Intent(MainActivity.this, ParcelsListActivity.class)); }

    private Account getAccountFromCache() {
        SharedPreferences sharedPreferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String accountJson = sharedPreferences.getString("ACCOUNT", "");
        if ("".equals(accountJson)) {
            return null;
        }
        return JsonUtil.fromJson(accountJson, Account.class);
    }

    @Override
    public void onSuccess(ApiService.Method method, ApiResponse response) {
        TokenStatus tokenStatus = (TokenStatus)response;
        if (tokenStatus.getActive()) {
            UserContext.getInstance().setAccount(account);
            openParcels();
        } else {
            openLogin();
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