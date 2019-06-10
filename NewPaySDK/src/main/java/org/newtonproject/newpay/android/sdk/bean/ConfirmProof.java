package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019-06-10--17:29
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public class ConfirmProof {
    @SerializedName("signature")
    public String signature;
    @SerializedName("sign_type")
    public String signType;
    @SerializedName("proof_hash")
    public String proofHash;
    @SerializedName("ts")
    public String ts;

    @Override
    public String toString() {
        return "ConfirmProofResponse{" +
                "signature='" + signature + '\'' +
                ", signType='" + signType + '\'' +
                ", proofHash='" + proofHash + '\'' +
                ", ts='" + ts + '\'' +
                '}';
    }
}
