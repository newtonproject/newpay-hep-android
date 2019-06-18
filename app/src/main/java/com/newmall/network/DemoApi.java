package com.newmall.network;

import org.newtonproject.newpay.android.sdk.bean.NewAuthProof;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019-06-18--22:13
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public interface DemoApi {
    @POST("get/proof/")
    @FormUrlEncoded
    Observable<BaseResponse<NewAuthProof>> getAuthProof(@Field("newid") String newid);
}
