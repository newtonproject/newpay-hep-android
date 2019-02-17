package org.newtonproject.newpay.android.sdk.constant;

/**
 * @author weixuefeng@lubangame.com
 */
public class Constant {

    public interface MainNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.release.pay";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.release.authorize";
    }

    public interface TestNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.pay";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.authorize";
    }

    public interface BetaNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.beta.pay";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.beta.authorize";
    }

    public interface DevNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.dev.pay";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.dev.authorize";
    }

    public static final int PROTOCOL_VERSION = 1;
    public static final String EXTRA_BUNDLE_SOURCE = "BUNDLE_SOURCE";
    public static final String EXTRA_PROTOCOL_VERSION = "PROTOCOL_VERSION";

}
