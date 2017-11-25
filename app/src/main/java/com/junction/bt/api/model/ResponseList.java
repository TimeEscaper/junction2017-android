package com.junction.bt.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sibirsky on 25.11.17.
 */

public class ResponseList<T> implements ApiResponse {

    List<T> items = new ArrayList<>();

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
