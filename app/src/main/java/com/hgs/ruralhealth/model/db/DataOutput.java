package com.hgs.ruralhealth.model.db;


import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by rameshg on 10/19/2016.
 */
public class DataOutput implements Parcelable {

    public static final Creator<DataOutput> CREATOR = new Creator<DataOutput>() {
        @Override
        public DataOutput createFromParcel(Parcel in) {
            return new DataOutput(in);
        }

        @Override
        public DataOutput[] newArray(int size) {
            return new DataOutput[size];
        }
    };

    private long totalReceived;
    private long totalAdded;

    public DataOutput() {
    }

    protected DataOutput(Parcel in) {
        this();
        totalReceived = in.readLong();
        totalAdded = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(totalReceived);
        dest.writeLong(totalAdded);
    }

    @Override
    public String toString() {
        return "DataOutput{" +
                "totalReceived='" + totalReceived + '\'' +
                ", totalAdded='" + totalAdded + '\'' +
                '}';
    }

    ;

    @Override
    public int describeContents() {
        return 0;
    }

    //Setter and Getter


    public long getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(long totalReceived) {
        this.totalReceived = totalReceived;
    }

    public long getTotalAdded() {
        return totalAdded;
    }

    public void setTotalAdded(long totalAdded) {
        this.totalAdded = totalAdded;
    }
}
