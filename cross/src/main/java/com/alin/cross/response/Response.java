package com.alin.cross.response;

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
public class Response implements Parcelable{
    private String mResult;

    public Response(String result) {
        mResult = result;
    }

    protected Response(Parcel in) {
        mResult = in.readString();
    }

    public String getResult() {
        return mResult == null ? "" : mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mResult);
    }
}
