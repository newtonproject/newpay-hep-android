package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@lubangame.com
 * @version $
 */
public class NewSignMessage {
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
    @SerializedName("sign_type")
    public String signType;
    @SerializedName("signature")
    public String signature;


    @SerializedName("message")
    public String message;

    @Override
    public String toString() {
        return "NewSignMessage{" +
                "uuid='" + uuid + '\'' +
                ", dappId='" + dappId + '\'' +
                ", protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                ", ts='" + ts + '\'' +
                ", nonce='" + nonce + '\'' +
                ", signType='" + signType + '\'' +
                ", signature='" + signature + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
