package com.junction.bt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.junction.bt.R;
import com.junction.bt.api.ApiService;
import com.junction.bt.api.model.Account;
import com.junction.bt.cache.CacheManager;
import com.junction.bt.context.UserContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openLogin();

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
}