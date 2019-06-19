package com.newmall.network;

import org.newtonproject.newpay.android.sdk.bean.NewAuthLogin;
import org.newtonproject.newpay.android.sdk.bean.NewAuthPay;
import org.newtonproject.newpay.android.sdk.bean.NewAuthProof;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019-06-18--22:13
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public interface DemoApi {
    @POST("get/client/proof/")
    @FormUrlEncoded
    Observable<BaseResponse<NewAuthProof>> getAuthProof(@Field("newid") String newid);

    @GET("get/client/login/")
    Observable<BaseResponse<NewAuthLogin>> getAuthLogin();

    @POST("get/client/pay/")
    @FormUrlEncoded
    Observable<BaseResponse<NewAuthPay>> getAuthPay(@Field("newid") String newid);
}
