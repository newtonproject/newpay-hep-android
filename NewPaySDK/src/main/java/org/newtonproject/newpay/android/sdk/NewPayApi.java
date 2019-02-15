package org.newtonproject.newpay.android.sdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;

import org.newtonproject.newpay.android.sdk.bean.Action;
import org.newtonproject.newpay.android.sdk.bean.Order;
import org.newtonproject.newpay.android.sdk.bean.SigMessage;
import org.newtonproject.newpay.android.sdk.constant.Constant;
import org.newtonproject.newpay.android.sdk.constant.Environment;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewPayApi {

    private static Application mApplication;
    private static String privateKey;
    private static String appId;
    private static Gson gson;
    public static final int REQUEST_CODE_NEWPAY = 3001;
    public static final int REQUEST_CODE_NEWPAY_PAY = 3002;
    public static final int REQUEST_CODE_PUSH_ORDER = 3003;

    private static final String TESTNET_SHARE_URL = "https://developer.newtonproject.org/testnet/newpay/download";
    private static final String RELEASE_SHARE_URL = "https://developer.newtonproject.org/release/newpay/download";

    private static final String ACTION = "ACTION";
    private static final String APPID = "APPID";
    private static final String CONTENT = "CONTENT";
    private static final String SIGNATURE = "SIGNATURE";

    private static String SHARE_URL = RELEASE_SHARE_URL;
    private static String authorize_pay = Constant.MainNet.authorize_pay;
    private static String authorize_login_place = Constant.MainNet.authorize_login_place;

    private NewPayApi() {}


    /**
     * @param context context
     * @param appKey appkey for third application
     * @param appid app id which registered in newton api.
     */
    public static void init(Application context, String appKey, String appid) {
        mApplication = context;
        privateKey = appKey;
        appId = appid;
        gson = new Gson();
    }

    /**
     * @param context context
     * @param appKey appkey for third application
     * @param appid app id which registered in newton api.
     * @param environment eg: Environment.MAINNET, Environment.TESTNET ...
     */
    public static void init(Application context, String appKey, String appid, Environment environment) {
        mApplication = context;
        privateKey = appKey;
        appId = appid;
        gson = new Gson();
        switch (environment) {
            case DEVNET:
                authorize_pay = Constant.DevNet.authorize_pay;
                authorize_login_place = Constant.DevNet.authorize_login_place;
                break;
            case BETANET:
                authorize_pay = Constant.BetaNet.authorize_pay;
                authorize_login_place = Constant.BetaNet.authorize_login_place;
                break;
            case TESTNET:
                authorize_pay = Constant.TestNet.authorize_pay;
                authorize_login_place = Constant.TestNet.authorize_login_place;
                break;
            case MAINNET:
                authorize_pay = Constant.MainNet.authorize_pay;
                authorize_login_place = Constant.MainNet.authorize_login_place;
                break;
        }
    }

    public static void requestProfileFromNewPay(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_login_place));
        intent.putExtra(ACTION, Action.REQUEST_PROFILE);
        intent.putExtra(APPID, appId);
        intent.putExtra(SIGNATURE, gson.toJson(getSigMessage(privateKey)));
        boolean isIntentSafe = checkNewPay(intent);
        // Start an activity if it's safe
        if (isIntentSafe) {
            activity.startActivityForResult(intent, REQUEST_CODE_NEWPAY);
        } else{
            startDownloadUrl(activity);
            //Toast.makeText(activity, R.string.no_newpay_application, Toast.LENGTH_SHORT).show();
        }
    }

    public static void requestPayForThirdApp(Activity activity, String address, BigInteger account){
        String unitStr = "NEW";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_pay));
        intent.putExtra("SYMBOL", unitStr);
        intent.putExtra("ADDRESS", address);
        intent.putExtra("AMOUNT", account.toString(10));
        intent.putExtra("REQUEST_PAY_SOURCE", activity.getPackageName());
        boolean isIntentSafe = checkNewPay(intent);
        // Start an activity if it's safe
        if (isIntentSafe) {
            activity.startActivityForResult(intent, REQUEST_CODE_NEWPAY_PAY);
        } else{
            startDownloadUrl(activity);
        }
    }

    public static void requestPushOrder(Activity activity, ArrayList<Order> orders) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_login_place));
        intent.putExtra(ACTION, Action.PUSH_ORDER);
        intent.putExtra(APPID, appId);
        intent.putExtra(SIGNATURE, gson.toJson(getSigMessage(privateKey)));
        intent.putExtra(CONTENT, gson.toJson(orders));
        boolean isIntentSafe = checkNewPay(intent);
        if (isIntentSafe) {
            activity.startActivityForResult(intent, REQUEST_CODE_PUSH_ORDER);
        } else{
            startDownloadUrl(activity);
        }
    }

    public static void requestProfileFromNewPay(Fragment activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_login_place));
        intent.putExtra(ACTION, Action.REQUEST_PROFILE);
        intent.putExtra(APPID, appId);
        intent.putExtra(SIGNATURE, gson.toJson(getSigMessage(privateKey)));
        boolean isIntentSafe = checkNewPay(intent);
        // Start an activity if it's safe
        if (isIntentSafe) {
            activity.startActivityForResult(intent, REQUEST_CODE_NEWPAY);
        } else{
            startDownloadUrl(activity.getContext());
            //Toast.makeText(activity, R.string.no_newpay_application, Toast.LENGTH_SHORT).show();
        }
    }

    public static void requestPayForThirdApp(Fragment activity, String address, BigInteger account){
        String unitStr = "NEW";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_pay));
        intent.putExtra("SYMBOL", unitStr);
        intent.putExtra("ADDRESS", address);
        intent.putExtra("AMOUNT", account.toString(10));
        intent.putExtra("REQUEST_PAY_SOURCE", activity.getContext().getPackageName());
        boolean isIntentSafe = checkNewPay(intent);
        // Start an activity if it's safe
        if (isIntentSafe) {
            activity.startActivityForResult(intent, REQUEST_CODE_NEWPAY_PAY);
        } else{
            startDownloadUrl(activity.getContext());
        }
    }

    public static void requestPushOrder(Fragment activity, ArrayList<Order> orders) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_login_place));
        intent.putExtra(ACTION, Action.PUSH_ORDER);
        intent.putExtra(APPID, appId);
        intent.putExtra(SIGNATURE, gson.toJson(getSigMessage(privateKey)));
        intent.putExtra(CONTENT, gson.toJson(orders));
        boolean isIntentSafe = checkNewPay(intent);
        if (isIntentSafe) {
            activity.startActivityForResult(intent, REQUEST_CODE_PUSH_ORDER);
        } else{
            startDownloadUrl(activity.getContext());
        }
    }

    private static void startDownloadUrl(final Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setMessage(R.string.no_newpay_application)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.download_newpay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse(SHARE_URL);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();

    }

    private static boolean checkNewPay(Intent intent) {
        PackageManager packageManager = mApplication.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }

    private static String getMessage() {
        return System.currentTimeMillis() + new Random().nextInt(1000000) + "";
    }

    private static SigMessage getSigMessage(String privateKey) {
        String message = getMessage();
        Sign.SignatureData sig = Sign.signMessage(message.getBytes(), ECKeyPair.create(Numeric.toBigInt(privateKey)));
        return new SigMessage(Numeric.toHexString(sig.getR()), Numeric.toHexString(sig.getS()), message);
    }
}
