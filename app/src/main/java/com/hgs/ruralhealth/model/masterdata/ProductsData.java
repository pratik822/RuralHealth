package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by pratikb on 30-09-2016.
 */
public class ProductsData implements Parcelable {
    private String status;
    private String message;
    List<ProductsOutputData> data;

    public static final Creator<ProductsData> CREATOR = new Creator<ProductsData>() {
        @Override
        public ProductsData createFromParcel(Parcel in) {
            return new ProductsData(in);
        }

        @Override
        public ProductsData[] newArray(int size) {
            return new ProductsData[size];
        }
    };

    protected ProductsData(Parcel in) {
        status = in.readString();
        message = in.readString();
        data = in.createTypedArrayList(ProductsOutputData.CREATOR);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //genrate getter setter

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

    public List<ProductsOutputData> getData() {
        return data;
    }

    public void setData(List<ProductsOutputData> data) {
        this.data = data;
    }
}
