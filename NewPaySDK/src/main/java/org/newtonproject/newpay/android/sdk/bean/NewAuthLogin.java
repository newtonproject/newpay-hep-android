package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019-06-10--11:22
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public class NewAuthLogin {
    @SerializedName("dapp_id")
    public String dappId;
    @SerializedName("protocol")
    public String protocol;
    @SerializedName("version")
    public String version;
    @SerializedName("ts")
    public String ts;
    @SerializedName("nonce")
    public String nonce;

    @SerializedName("action")
    public String action;
    @SerializedName("scope")
    public String scope;
    @SerializedName("memo")
    public String memo;

    @SerializedName("sign_type")
    public String signType;
    @SerializedName("signature")
    public String signature;
}
