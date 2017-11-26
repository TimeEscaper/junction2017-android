package com.junction.bt.cache;

import com.junction.bt.activity.MainActivity;
import com.junction.bt.api.model.Account;
import com.junction.bt.api.model.Checkpoint;
import com.junction.bt.api.model.Parcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sibirsky on 25.11.17.
 */

public class CacheManager {

    private static final CacheManager ourInstance = new CacheManager();

    private Map<Integer, Parcel> cachedParcels = new HashMap<>();
    private Map<Integer, Checkpoint> cachedCheckpoints = new HashMap<>();

    public static CacheManager getInstance() {
        return ourInstance;
    }

    private CacheManager() {
    }

    public void cacheAccount(Account account) {

    }

    public void putParcels(List<Parcel> parcels) {
        for (Parcel parcel : parcels) {
            cachedParcels.put(parcel.getId(), parcel);
        }
    }

    public Parcel getCachedParcel(Integer parcelId) {
        return cachedParcels.get(parcelId);
    }

    public void putCheckpoints(List<Checkpoint> checkpoints) {
        for (Checkpoint checkpoint : checkpoints) {
            cachedCheckpoints.put(checkpoint.getId(), checkpoint);
        }
    }

    public Checkpoint getCachedCheckpoint(Integer checkpointId) {
        return cachedCheckpoints.get(checkpointId);
    }
}
