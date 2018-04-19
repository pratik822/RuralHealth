package com.hgs.ruralhealth.model.masterdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rameshg on 9/18/2016.
 */
public class ProductsOutputData implements Parcelable {
    private Integer productId;
    private String productCode;
    private String productName;
    private String productDescription;
    private String unit;
    private String qty;

    public ProductsOutputData() {

    }
    public static final Creator<ProductsOutputData> CREATOR = new Creator<ProductsOutputData>() {
        @Override
        public ProductsOutputData createFromParcel(Parcel in) {
            return new ProductsOutputData(in);
        }

        @Override
        public ProductsOutputData[] newArray(int size) {
            return new ProductsOutputData[size];
        }
    };

    protected ProductsOutputData(Parcel in) {
        productCode = in.readString();
        productName = in.readString();
        productDescription = in.readString();
        unit = in.readString();
        qty = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productCode);
        dest.writeString(productName);
        dest.writeString(productDescription);
        dest.writeString(unit);
        dest.writeString(qty);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "ProductsOutputData{" +
                "productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", unit='" + unit + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }

    //genrate getter setter
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}








