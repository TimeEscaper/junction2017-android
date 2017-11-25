package com.junction.bt.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.junction.bt.R;
import com.junction.bt.api.ApiService;
import com.junction.bt.api.model.Account;
import com.junction.bt.cache.CacheManager;
import com.junction.bt.context.UserContext;
import com.junction.bt.util.JsonUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Account account = getAccountFromCache();
        if (account == null) {
            openLogin();
        } else {
            String token = account.getToken();
        }

        /*Account account = CacheManager.getInstance().getCachedAccount();
        if (account == null) {
            openLogin();
        }
        else {
            String token = account.getToken();
            boolean isValid = ApiService.getInstance().checkToken(token);
            if (!isValid) {
                openLogin();
            } else {
                UserContext.getInstance().setAccount(account);
            }
        }*/
    }

    private void openLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private Account getAccountFromCache() {
        SharedPreferences sharedPreferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String accountJson = sharedPreferences.getString("ACCOUNT", "");
        if ("".equals(accountJson)) {
            return null;
        }
        return JsonUtil.fromJson(accountJson, Account.class);
    }
}