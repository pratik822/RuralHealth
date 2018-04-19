package com.hgs.ruralhealth.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 9/29/2016.
 */
public class UserOutputData  implements Parcelable {

    public static final Creator<UserOutputData> CREATOR = new Creator<UserOutputData>() {
        @Override
        public UserOutputData createFromParcel(Parcel in) {
            return new UserOutputData(in);
        }

        @Override
        public UserOutputData[] newArray(int size) {
            return new UserOutputData[size];
        }
    };


    private int status;
    private String message;
    private UserData data;

    public UserOutputData() {
        data = new UserData();
    }

    protected UserOutputData(Parcel in) {
        this();
        status = in.readInt();
        message = in.readString();
        data = in.readParcelable(UserData.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(message);
        dest.writeParcelable(data, flags);
    }

    @Override
    public String toString() {
        return "UserOutputData{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
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

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
