package com.hgs.ruralhealth.model.MSW_Activity;


import android.os.Parcel;
import android.os.Parcelable;

import com.hgs.ruralhealth.model.register.RegistrationData;

/**
 * Created by pratikb on 20-10-2016.
 */
public class MswOuputResponse implements Parcelable{
    private String status,message;
    public MswData data;

    public MswOuputResponse(){

    }
    public static final Creator<MswOuputResponse> CREATOR = new Creator<MswOuputResponse>() {
        @Override
        public MswOuputResponse createFromParcel(Parcel in) {
            return new MswOuputResponse(in);
        }

        @Override
        public MswOuputResponse[] newArray(int size) {
            return new MswOuputResponse[size];
        }
    };

    protected MswOuputResponse(Parcel in) {
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

    public MswData getData() {
        return data;
    }

    public void setData(MswData data) {
        this.data = data;
    }
}
