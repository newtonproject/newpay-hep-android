package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

public class ProfileInfo {
    @SerializedName("newid")
    public String newid;
    @SerializedName("country_code")
    public String countryCode;
    @SerializedName("cellphone")
    public String cellphone;
    @SerializedName("invite_code")
    public String inviteCode;
    @SerializedName("avatar")
    public String avatarPath;
    @SerializedName("name")
    public String name;
    @SerializedName("access_key")
    public String access_key;
    @SerializedName("wallet_address")
    public String address;


    @Override
    public String toString() {
        return "ProfileInfo{" +
                "newid='" + newid + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", name='" + name + '\'' +
                ", access_key='" + access_key + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
