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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NewPaySDK {

    private static Application mApplication;
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

    private static final String SYMBOL = "SYMBOL";
    private static final String ADDRESS = "ADDRESS";
    private static final String AMOUNT = "AMOUNT";
    private static final String SOURCE = "REQUEST_PAY_SOURCE";


    private static String SHARE_URL = RELEASE_SHARE_URL;
    private static String authorize_pay = Constant.MainNet.authorize_pay;
    private static String authorize_login_place = Constant.MainNet.authorize_login_place;

    private NewPaySDK() {}


    /**
     * @param context context
     * @param appid app id which registered in newton api.
     */
    public static void init(Application context, String appid) {
        mApplication = context;
        appId = appid;
        gson = new Gson();
    }

    /**
     * @param context context
     * @param appid app id which registered in newton api.
     * @param environment eg: Environment.MAINNET, Environment.TESTNET ...
     */
    public static void init(Application context, String appid, Environment environment) {
        mApplication = context;
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

    public static void requestProfile(Activity activity, SigMessage sigMessage) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_login_place));
        intent.putExtra(ACTION, Action.REQUEST_PROFILE);
        intent.putExtra(APPID, appId);
        intent.putExtra(SIGNATURE, gson.toJson(sigMessage));
        checkAndStartActivity(activity, intent, REQUEST_CODE_NEWPAY);
    }

    public static void pay(Activity activity, String address, BigInteger account){
        String unitStr = "NEW";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_pay));
        intent.putExtra(SYMBOL, unitStr);
        intent.putExtra(ADDRESS, address);
        intent.putExtra(AMOUNT, account.toString(10));
        intent.putExtra(SOURCE, activity.getPackageName());
        checkAndStartActivity(activity, intent, REQUEST_CODE_NEWPAY_PAY);
    }

    public static void placeOrder(Activity activity, ArrayList<Order> orders, SigMessage sigMessage) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_login_place));
        intent.putExtra(ACTION, Action.PUSH_ORDER);
        intent.putExtra(APPID, appId);
        intent.putExtra(SIGNATURE, gson.toJson(sigMessage));
        intent.putExtra(CONTENT, gson.toJson(orders));
        checkAndStartActivity(activity, intent, REQUEST_CODE_PUSH_ORDER);

    }

    public static void requestProfile(Fragment activity, SigMessage sigMessage) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_login_place));
        intent.putExtra(ACTION, Action.REQUEST_PROFILE);
        intent.putExtra(APPID, appId);
        intent.putExtra(SIGNATURE, gson.toJson(sigMessage));
        checkAndStartActivity(activity, intent, REQUEST_CODE_NEWPAY);
    }

    public static void pay(Fragment activity, String address, BigInteger account){
        String unitStr = "NEW";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_pay));
        intent.putExtra(SYMBOL, unitStr);
        intent.putExtra(ADDRESS, address);
        intent.putExtra(AMOUNT, account.toString(10));
        intent.putExtra(SOURCE, activity.getContext().getPackageName());
        checkAndStartActivity(activity, intent, REQUEST_CODE_NEWPAY_PAY);
    }

    public static void placeOrder(Fragment activity, ArrayList<Order> orders, SigMessage sigMessage) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_login_place));
        intent.putExtra(ACTION, Action.PUSH_ORDER);
        intent.putExtra(APPID, appId);
        intent.putExtra(SIGNATURE, gson.toJson(sigMessage));
        intent.putExtra(CONTENT, gson.toJson(orders));
        checkAndStartActivity(activity, intent, REQUEST_CODE_PUSH_ORDER);
    }

    private static void checkAndStartActivity(Activity activity, Intent intent, int requestCode) {
        boolean isIntentSafe = checkNewPay(intent);
        if (isIntentSafe) {
            activity.startActivityForResult(intent, requestCode);
        } else{
            startDownloadUrl(activity);
        }
    }

    private static void checkAndStartActivity(Fragment activity, Intent intent, int requestCode) {
        boolean isIntentSafe = checkNewPay(intent);
        if (isIntentSafe) {
            activity.startActivityForResult(intent, requestCode);
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
}
