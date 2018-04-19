package com.hgs.ruralhealth.model.MSW_Distribution;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pratikb on 03-11-2016.
 */
public class Items implements Parcelable{
    private String createdDate,mSWName,itemName,qty,Unit;

  public Items(){}

    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };

    protected Items(Parcel in) {
        createdDate = in.readString();
        mSWName = in.readString();
        itemName = in.readString();
        qty = in.readString();
        Unit = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdDate);
        dest.writeString(mSWName);
        dest.writeString(itemName);
        dest.writeString(qty);
        dest.writeString(Unit);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getmSWName() {
        return mSWName;
    }

    public void setmSWName(String mSWName) {
        this.mSWName = mSWName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}
