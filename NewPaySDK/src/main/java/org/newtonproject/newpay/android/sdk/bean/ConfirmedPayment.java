package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019-06-20--23:29
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public class ConfirmedPayment {
    @SerializedName("signature")
    public String signature;
    @SerializedName("sign_type")
    public String signType;
    @SerializedName("txid")
    public String txid;
    @SerializedName("ts")
    public String timestamp;
    @SerializedName("nonce")
    public String nonce;
    @SerializedName("order_number")
    public String orderNumber;
    @SerializedName("dapp_id")
    public String dappId;
    @SerializedName("uuid")
    public String uuid;

    @Override
    public String toString() {
        return "ConfirmedPayment{" +
                "signature='" + signature + '\'' +
                ", signType='" + signType + '\'' +
                ", txid='" + txid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", dappId='" + dappId + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
