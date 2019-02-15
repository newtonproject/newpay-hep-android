package org.newtonproject.newpay.android.sdk.bean;

public class SigMessage {

    public String signS;
    public String signR;
    public String message;

    public SigMessage(String signR, String signS, String message) {
        this.signR = signR;
        this.signS = signS;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("{" +
                "signS: %s " +
                "signR: %s " +
                "message: %s " +
                "}", signS, signR, message);
    }
}
