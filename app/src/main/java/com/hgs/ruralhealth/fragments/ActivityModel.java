package com.hgs.ruralhealth.fragments;

import java.util.List;

/**
 * Created by pratikb on 21-03-2018.
 */

public class ActivityModel {
    String status,message;
    List<ActivityData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ActivityData> getData() {
        return data;
    }

    public void setData(List<ActivityData> data) {
        this.data = data;
    }
}
