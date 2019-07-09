package org.newtonproject.newpay.android.sdk.constant;

/**
 * @author weixuefeng@lubangame.com
 */
public class Constant {

    public interface MainNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.release.authorize_v2";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.release.authorize_v2";
        String share_url = "https://www.newtonproject.org/download/newpay/mainnet/";
    }

    public interface TestNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.authorize_v2";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.authorize_v2";
        String share_url = "https://developer.newtonproject.org/download/newpay/testnet/";
    }

    public interface BetaNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.beta.authorize_v2";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.beta.authorize_v2";
        String share_url = "https://developer.newtonproject.org/download/newpay/betanet/";
    }

    public interface DevNet {
        String authorize_pay = "newpay://org.newtonproject.newpay.android.dev.authorize_v2";
        String authorize_login_place = "newpay://org.newtonproject.newpay.android.dev.authorize_v2";
        String share_url = "https://developer.newtonproject.org/download/newpay/devnet/";
    }

    public static final String EXTRA_BUNDLE_SOURCE = "BUNDLE_SOURCE";
}
