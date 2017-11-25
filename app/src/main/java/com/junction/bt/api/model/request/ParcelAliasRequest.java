package com.junction.bt.api.model.request;

/**
 * Created by sibirsky on 25.11.17.
 */

public class ParcelAliasRequest {

    private String token;
    private Integer parcelId;
    private String alias;

    public ParcelAliasRequest() { }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getParcelId() {
        return parcelId;
    }

    public void setParcelId(Integer parcelId) {
        this.parcelId = parcelId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
