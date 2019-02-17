package org.newtonproject.newpay.android.sdk.bean;

import com.google.gson.annotations.SerializedName;

public class Order{
    @SerializedName("order_number")
    public String orderNumber;
    @SerializedName("price")
    public float price;
    @SerializedName("currency")
    public String currency;
    @SerializedName("seller_newid")
    public String sellerNewid;
    @SerializedName("buyer_newid")
    public String buyerNewid;
}
