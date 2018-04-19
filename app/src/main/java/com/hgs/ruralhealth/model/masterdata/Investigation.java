package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 21-04-2017.
 */
public class Investigation implements Parcelable{
    int status;
    String message;
    List<InvestigationData>data;

    public static final Creator<Investigation> CREATOR = new Creator<Investigation>() {
        @Override
        public Investigation createFromParcel(Parcel in) {
            return new Investigation(in);
        }

        @Override
        public Investigation[] newArray(int size) {
            return new Investigation[size];
        }
    };

    protected Investigation(Parcel in) {
        status = in.readInt();
        message = in.readString();
        data = in.createTypedArrayList(InvestigationData.CREATOR);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(message);
        dest.writeTypedList(data);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<InvestigationData> getData() {
        return data;
    }

    public void setData(List<InvestigationData> data) {
        this.data = data;
    }
}
