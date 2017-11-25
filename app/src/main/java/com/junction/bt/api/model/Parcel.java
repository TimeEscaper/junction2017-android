package com.junction.bt.api.model;

import java.util.List;

/**
 * Created by sibirsky on 25.11.17.
 */

public class Parcel {

    private Integer parcelId;
    private User sender;
    private User receiver;
    private String destinationAddress;
    private String backAddress;
    private Checkpoint from;
    private Checkpoint to;
    private Event[] history;
    private String alias;

    public Parcel() {}

    public Integer getParcelId() { return parcelId; }

    public void setParcelId(Integer id) {
        this.parcelId = parcelId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() { return receiver; }

    public void setReceiver(User receiver) { this.receiver = receiver; }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getBackAddress() {
        return backAddress;
    }

    public void setBackAddress(String backAddress) {
        this.backAddress = backAddress;
    }

    public Checkpoint getFrom() {
        return from;
    }

    public void setFrom(Checkpoint from) {
        this.from = from;
    }

    public Checkpoint getTo() {
        return to;
    }

    public void setTo(Checkpoint to) {
        this.to = to;
    }

    public Event[] getHistory() {
        return history;
    }

    public void setHistory(Event[] history) {
        this.history = history;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
