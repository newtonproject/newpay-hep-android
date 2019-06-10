package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019-06-04--17:34
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public class HepProfile {
    @SerializedName("uuid")
    public String uuid;
    @SerializedName("signature")
    public String signature;
    @SerializedName("profile")
    public Profile profile;
    @SerializedName("signType")
    public String signType; // prime256v1   NIST P-256

    public String getProfileString() {
        if(profile == null) throw new IllegalStateException("profile can not be null");
        return new Gson().toJson(profile);
    }

    @Override
    public String toString() {
        return "HepProfile{" +
                "signature='" + signature + '\'' +
                ", profile=" + profile +
                ", signType='" + signType + '\'' +
                '}';
    }

    class Profile {
        @SerializedName("name")
        public String name;
        @SerializedName("country_code")
        public String countryCode;
        @SerializedName("cellphone")
        public String cellphone;
        @SerializedName("address")
        public String address;
        @SerializedName("newid")
        public String newid;
        @SerializedName("avatar")
        public String avatarPath;
        @SerializedName("invite_code")
        public String inviteCode;
    }

    public ProfileInfo getProfileInfo() {
        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.name = profile.name;
        profileInfo.countryCode = profile.countryCode;
        profileInfo.cellphone = profile.cellphone;
        profileInfo.address = profile.address;
        profileInfo.newid = profile.newid;
        profileInfo.avatarPath = profile.avatarPath;
        profileInfo.inviteCode = profile.inviteCode;
        return profileInfo;
    }

    public String getSignature() {
        return signature;
    }

    public String getsignType() {
        return signType;
    }
}
