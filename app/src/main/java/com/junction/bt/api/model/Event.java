package com.junction.bt.api.model;

import java.time.LocalDateTime;

/**
 * Created by sibirsky on 25.11.17.
 */

public class Event {

    private LocalDateTime dateTime;
    private Integer checkpointId;
    private ParcelState state;
    private String description;

    public Event() {}

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(Integer checkpointId) {
        this.checkpointId = checkpointId;
    }

    public ParcelState getState() {
        return state;
    }

    public void setState(ParcelState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
