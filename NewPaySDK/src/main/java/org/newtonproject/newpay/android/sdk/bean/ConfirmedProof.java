package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019-06-20--23:29
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public class ConfirmedProof {
    @SerializedName("signature")
    public String signature;
    @SerializedName("sign_type")
    public String signType;
    @SerializedName("proof_hash")
    public String proofHash;
    @SerializedName("ts")
    public String ts;
    @SerializedName("dapp_id")
    public String dappId;
    @SerializedName("nonce")
    public String nonce;
    @SerializedName("uuid")
    public String uuid;

    @Override
    public String toString() {
        return "ConfirmedProof{" +
                "signature='" + signature + '\'' +
                ", signType='" + signType + '\'' +
                ", proofHash='" + proofHash + '\'' +
                ", ts='" + ts + '\'' +
                ", dappId='" + dappId + '\'' +
                ", nonce='" + nonce + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
