package com.junction.bt.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.junction.bt.R;
import com.junction.bt.api.ApiService;
import com.junction.bt.api.model.Account;
import com.junction.bt.context.UserContext;
import com.junction.bt.exception.AuthException;

public class LoginActivity extends AppCompatActivity {

    private EditText loginInput;
    private EditText passwordInput;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    private void btnSignupPress() {
        String login = loginInput.getText().toString();
        String password = passwordInput.getText().toString();

        try {
            Account account = ApiService.getInstance().authorize(login, password);
            UserContext.getInstance().setAccount(account);
        } catch (AuthException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
        }
    }
}
