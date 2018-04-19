package com.hgs.ruralhealth.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 9/30/2016.
 */
public class UserInputData implements Parcelable {

    public static final Creator<UserInputData> CREATOR = new Creator<UserInputData>() {
        @Override
        public UserInputData createFromParcel(Parcel in) {
            return new UserInputData(in);
        }

        @Override
        public UserInputData[] newArray(int size) {
            return new UserInputData[size];
        }
    };


    private String loginId;
    private String password;

    public UserInputData() {
    }

    protected UserInputData(Parcel in) {
        this();

        loginId = in.readString();
        password = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(loginId);
        dest.writeString(password);

    }

    @Override
    public String toString() {
        return "UserInputData{" +
                "loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
