package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@lubangame.com
 * @version $
 * @time: 2020-03-10--16:39
 * @description
 * @copyright (c) 2020 Newton Foundation. All rights reserved.
 */
public class NewSignTransaction {
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


    @SerializedName("action")
    public String action;
    @SerializedName("amount")
    public String amount;
    @SerializedName("from")
    public String from;
    @SerializedName("to")
    public String to;
    @SerializedName("gas_limit")
    public String gasLimit;
    @SerializedName("gas_price")
    public String gasPrice;
    @SerializedName("transaction_count")
    public String transactionCount;
    @SerializedName("data")
    public String data;

    @Override
    public String toString() {
        return "NewSignTransaction{" +
                "uuid='" + uuid + '\'' +
                ", dappId='" + dappId + '\'' +
                ", protocol='" + protocol + '\'' +
                ", version='" + version + '\'' +
                ", ts='" + ts + '\'' +
                ", nonce='" + nonce + '\'' +
                ", signType='" + signType + '\'' +
                ", signature='" + signature + '\'' +
                ", action='" + action + '\'' +
                ", amount='" + amount + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", gasLimit='" + gasLimit + '\'' +
                ", gasPrice='" + gasPrice + '\'' +
                ", transactionCount='" + transactionCount + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
