package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

public class HepProfile {
    @SerializedName("signature")
    public String signature;
    @SerializedName("sign_type")
    public String sign_type ; // prime256v1   NIST P-256
    @SerializedName("uuid")
    public String uuid;
    @SerializedName("name")
    public String name;
    @SerializedName("country_code")
    public String countryCode;
    @SerializedName("cellphone")
    public String cellphone;
    @SerializedName("avatar")
    public String avatarPath;
    @SerializedName("address")
    public String address;
    @SerializedName("newid")
    public String newid;
    @SerializedName("invite_code")
    public String inviteCode;
}
