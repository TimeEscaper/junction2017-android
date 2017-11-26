package com.junction.bt.api.model;

/**
 * Created by sibirsky on 25.11.17.
 */

public class Checkpoint implements ApiResponse {

    private Integer id;
    private String name;
    private String address;

    public Integer getId() { return id; }

    public void setId(Integer checkpointId) {
        this.id = id;
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
