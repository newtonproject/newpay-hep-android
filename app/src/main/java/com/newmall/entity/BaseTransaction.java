package com.newmall.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author weixuefeng@lubangame.com
 * @version $
 * @time: 2020-03-10--16:40
 * @description
 * @copyright (c) 2020 Newton Foundation. All rights reserved.
 */
public class BaseTransaction {
    @SerializedName("amount")
    public String amount;
    @SerializedName("from")
    public String from;
    @SerializedName("to")
    public String to;
    @SerializedName("transaction_count")
    public String transactionCount;
    @SerializedName("gas_price")
    public String gasPrice;
    @SerializedName("gas_limit")
    public String gasLimit;
    @SerializedName("data")
    public String data;

    public BaseTransaction(String amount, String from, String to, String transactionCount, String gasPrice, String gasLimit, String data) {
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.transactionCount = transactionCount;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseTransaction{" +
                "amount='" + amount + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", transactionCount='" + transactionCount + '\'' +
                ", gasPrice='" + gasPrice + '\'' +
                ", gasLimit='" + gasLimit + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
