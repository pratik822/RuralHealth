package com.hgs.ruralhealth.model.MSW_Distribution;


import android.os.Parcel;
import android.os.Parcelable;

import com.hgs.ruralhealth.model.MSW_Activity.MswData;

/**
 * Created by pratikb on 20-10-2016.
 */
public class MswDistributionOuputResponse implements Parcelable{
    private String status,message;
    public MswDistributionData data;

    public MswDistributionOuputResponse(){

    }
    public static final Creator<MswDistributionOuputResponse> CREATOR = new Creator<MswDistributionOuputResponse>() {
        @Override
        public MswDistributionOuputResponse createFromParcel(Parcel in) {
            return new MswDistributionOuputResponse(in);
        }

        @Override
        public MswDistributionOuputResponse[] newArray(int size) {
            return new MswDistributionOuputResponse[size];
        }
    };

    protected MswDistributionOuputResponse(Parcel in) {
        status = in.readString();
        message = in.readString();
        data = in.readParcelable(MswData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeParcelable(data, flags);
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

    public MswDistributionData getData() {
        return data;
    }

    public void setData(MswDistributionData data) {
        this.data = data;
    }
}
