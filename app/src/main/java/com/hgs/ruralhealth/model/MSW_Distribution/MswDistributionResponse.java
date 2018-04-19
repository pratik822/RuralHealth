package com.hgs.ruralhealth.model.MSW_Distribution;


import android.os.Parcel;
import android.os.Parcelable;

import com.hgs.ruralhealth.model.MSW_Activity.MswAcitivyInputData;

import java.util.List;

/**
 * Created by pratikb on 20-10-2016.
 */
public class MswDistributionResponse implements Parcelable {
    private String status, message, syncFromId, syncToId;
    public List<Msw_Distribution_InputData> data;

    public MswDistributionResponse(){

    }

    public static final Creator<MswDistributionResponse> CREATOR = new Creator<MswDistributionResponse>() {
        @Override
        public MswDistributionResponse createFromParcel(Parcel in) {
            return new MswDistributionResponse(in);
        }

        @Override
        public MswDistributionResponse[] newArray(int size) {
            return new MswDistributionResponse[size];
        }
    };

    protected MswDistributionResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        syncFromId = in.readString();
        syncToId = in.readString();
        data = in.createTypedArrayList(Msw_Distribution_InputData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeString(syncFromId);
        dest.writeString(syncToId);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }


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

    public String getSyncFromId() {
        return syncFromId;
    }

    public void setSyncFromId(String syncFromId) {
        this.syncFromId = syncFromId;
    }

    public List<Msw_Distribution_InputData> getData() {
        return data;
    }

    public void setData(List<Msw_Distribution_InputData> data) {
        this.data = data;
    }

    public String getSyncToId() {
        return syncToId;
    }

    public void setSyncToId(String syncToId) {
        this.syncToId = syncToId;
    }
}
