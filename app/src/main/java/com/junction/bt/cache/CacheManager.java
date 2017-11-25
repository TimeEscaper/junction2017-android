package com.junction.bt.cache;

import com.junction.bt.activity.MainActivity;
import com.junction.bt.api.model.Account;

/**
 * Created by sibirsky on 25.11.17.
 */

public class CacheManager {
    private static final CacheManager ourInstance = new CacheManager();

    public static CacheManager getInstance() {
        return ourInstance;
    }

    private CacheManager() {
    }

    public void cacheAccount(Account account) {

    }

    public Account getCachedAccount() {
        return null;
    }

    public void clearCachedAccount() {
        return;
    }
}
