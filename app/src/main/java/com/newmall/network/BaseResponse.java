package com.newmall.network;

import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019-06-18--22:14
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public class BaseResponse<T> {
    @SerializedName("error_code")
    public int errorCode;
    @SerializedName("error_message")
    public String errorMessage;
    @SerializedName("result")
    public T result;

    @Override
    public String toString() {

        return "BaseResponse{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", result=" + result +
                '}';
    }
}
