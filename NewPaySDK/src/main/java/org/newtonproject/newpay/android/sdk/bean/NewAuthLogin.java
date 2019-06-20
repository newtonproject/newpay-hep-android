package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

public class NewAuthLogin {
    @SerializedName("uuid")
    public String uuid;
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
