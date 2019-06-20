package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

public class NewAuthPay {
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
    @SerializedName("description")
    public String description;
    @SerializedName("price_currency")
    public String priceCurrency; // CNY, USD
    @SerializedName("total_price")
    public String totalPrice;
    @SerializedName("order_number")
    public String orderNumber;
    @SerializedName("seller")
    public String seller;
    @SerializedName("customer")
    public String customer;
    @SerializedName("broker")
    public String broker;

    @SerializedName("sign_type")
    public String signType;
    @SerializedName("signature")
    public String signature;
}
