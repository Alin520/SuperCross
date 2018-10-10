package com.alin.cross.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/8 16:30
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class Request implements Parcelable{

    private String mRequestData;
    private int mRequestType;

    public Request(String requestData, int requestType) {
        mRequestData = requestData;
        mRequestType = requestType;
    }

    protected Request(Parcel in) {
        mRequestData = in.readString();
        mRequestType = in.readInt();
    }

    public String getRequestData() {
        return mRequestData == null ? "" : mRequestData;
    }

    public void setRequestData(String requestData) {
        mRequestData = requestData;
    }

    public int getRequestType() {
        return mRequestType;
    }

    public void setRequestType(int requestType) {
        mRequestType = requestType;
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRequestData);
        dest.writeInt(mRequestType);
    }
}
