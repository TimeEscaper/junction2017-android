package com.junction.bt.api.model;

/**
 * Created by sibirsky on 25.11.17.
 */

public class Checkpoint {

    private Integer checkpointId;
    private String name;
    private String address;

    public Integer getCheckpointId() { return checkpointId; }

    public void setCheckpointId(Integer checkpointId) {
        this.checkpointId = checkpointId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
