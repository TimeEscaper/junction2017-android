package com.junction.bt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.junction.bt.R;
import com.junction.bt.api.ApiCallback;
import com.junction.bt.api.ApiService;
import com.junction.bt.api.model.Account;
import com.junction.bt.api.model.ApiError;
import com.junction.bt.api.model.ApiResponse;
import com.junction.bt.context.UserContext;
import com.junction.bt.exception.AuthException;

public class LoginActivity extends AppCompatActivity implements ApiCallback {

    public static final String SUBSCRIBED_ID = "SUBSCRIBED_ID";

    private Integer subscribeId = null;

    private EditText loginInput;
    private EditText passwordInput;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState != null) {
            subscribeId = savedInstanceState.getInt(SUBSCRIBED_ID);
        }

        loginInput = (EditText)findViewById(R.id.login_input);
        passwordInput = (EditText)findViewById(R.id.password_input);
        btnSignup = (Button)findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignupPress();
            }
        });
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

    private void btnSignupPress() {
        String login = loginInput.getText().toString();
        String password = passwordInput.getText().toString();

        ApiService.getInstance().authorize(subscribeId, login, password);
    }

    private void openParcels() {
        startActivity(new Intent(LoginActivity.this, ParcelsListActivity.class));
    }

    @Override
    public void onSuccess(ApiService.Method method, ApiResponse response) {
        openParcels();
    }

    @Override
    public void onError(ApiService.Method method, ApiError response) {
        Toast.makeText(getApplicationContext(), response.getError(), Toast.LENGTH_LONG);
    }
}
