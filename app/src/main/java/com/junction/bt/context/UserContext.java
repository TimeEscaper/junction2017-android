package com.junction.bt.context;

import com.junction.bt.api.model.Account;

/**
 * Created by sibirsky on 25.11.17.
 */

public class UserContext {

    private static final UserContext ourInstance = new UserContext();

    private Account account;

    public static UserContext getInstance() {
        return ourInstance;
    }

    private UserContext() {
    }

    public Account getAccount() { return account; }

    public void setAccount(Account account) { this.account = account; }

    public String getToken() { return account.getToken(); }
}
