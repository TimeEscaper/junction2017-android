package com.junction.bt.api.model;

import java.time.LocalDateTime;

/**
 * Created by sibirsky on 25.11.17.
 */

public class Event implements ApiResponse {

    private LocalDateTime dateTime;
    private Integer checkpointId;
    private Integer parcelId;
    private ParcelState state;
    private Integer weight;
    private String description;

    public Event() {}

    public LocalDateTime getDateTime() { return dateTime; }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(Integer checkpointId) {
        this.checkpointId = checkpointId;
    }

    public Integer getParcelId() { return parcelId;}

    public void setParcelId(Integer parcelId) { this.parcelId = parcelId; }

    public ParcelState getState() {
        return state;
    }

    public void setState(ParcelState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public Integer getWeight() { return weight; }

    public void setWeight(Integer weight) { this.weight = weight; }

    public void setDescription(String description) {
        this.description = description;
    }
}
