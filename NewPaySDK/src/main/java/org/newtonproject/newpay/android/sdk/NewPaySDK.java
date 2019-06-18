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
import org.newtonproject.newpay.android.sdk.bean.NewAuthLogin;
import org.newtonproject.newpay.android.sdk.bean.NewAuthPay;
import org.newtonproject.newpay.android.sdk.bean.NewAuthProof;
import org.newtonproject.newpay.android.sdk.constant.Constant;
import org.newtonproject.newpay.android.sdk.constant.Environment;

import java.math.BigInteger;
import java.util.List;

public class NewPaySDK {

    private static Application mApplication;
    private static Gson gson;

    public static final int REQUEST_CODE_NEWPAY = 3001;
    public static final int REQUEST_CODE_NEWPAY_PAY = 3002;
    public static final int REQUEST_CODE_PUSH_ORDER = 3003;


    private static final String ACTION = "ACTION";
    private static final String APPID = "APPID";
    private static final String CONTENT = "CONTENT";

    private static String SHARE_URL = Constant.MainNet.share_url;
    private static String authorize_pay = Constant.MainNet.authorize_pay;
    private static String authorize_login_place = Constant.MainNet.authorize_login_place;

    private NewPaySDK() {}


    /**
     * @param context context
     */
    public static void init(Application context) {
        mApplication = context;
        gson = new Gson();
    }

    /**
     * @param context context
     * @param environment eg: Environment.MAINNET, Environment.TESTNET ...
     */
    public static void init(Application context, Environment environment) {
        mApplication = context;
        gson = new Gson();
        switch (environment) {
            case DEVNET:
                authorize_pay = Constant.DevNet.authorize_pay;
                authorize_login_place = Constant.DevNet.authorize_login_place;
                SHARE_URL = Constant.DevNet.share_url;
                break;
            case BETANET:
                authorize_pay = Constant.BetaNet.authorize_pay;
                authorize_login_place = Constant.BetaNet.authorize_login_place;
                SHARE_URL = Constant.BetaNet.share_url;
                break;
            case TESTNET:
                authorize_pay = Constant.TestNet.authorize_pay;
                authorize_login_place = Constant.TestNet.authorize_login_place;
                SHARE_URL = Constant.TestNet.share_url;
                break;
            case MAINNET:
                authorize_pay = Constant.MainNet.authorize_pay;
                authorize_login_place = Constant.MainNet.authorize_login_place;
                SHARE_URL = Constant.MainNet.share_url;
                break;
        }
    }

    public static void requestProfile(Activity activity, NewAuthLogin params) {
        Intent intent = getRequestProfileIntent(params, activity);
        checkAndStartActivity(activity, intent, REQUEST_CODE_NEWPAY);
    }

    public static void requestProfile(Fragment fragment, NewAuthLogin params) {
        Intent intent = getRequestProfileIntent(params, fragment.getContext());
        checkAndStartActivity(fragment, intent, REQUEST_CODE_NEWPAY);
    }

    private static Intent getRequestProfileIntent(NewAuthLogin params, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_login_place));
        intent.putExtra(ACTION, Action.REQUEST_PROFILE);
        intent.putExtra(APPID, params.dappId);
        intent.putExtra(CONTENT, gson.toJson(params));
        intent.putExtra(Constant.EXTRA_BUNDLE_SOURCE, context.getPackageName());
        return intent;
    }

    public static void pay(Activity activity, NewAuthPay params){
        Intent intent = getRequestPayIntent(params, activity);
        checkAndStartActivity(activity, intent, REQUEST_CODE_NEWPAY_PAY);
    }

    public static void pay(Fragment fragment, NewAuthPay params){
        Intent intent = getRequestPayIntent(params, fragment.getContext());
        checkAndStartActivity(fragment, intent, REQUEST_CODE_NEWPAY_PAY);
    }

    private static Intent getRequestPayIntent(NewAuthPay params, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_pay));
        intent.putExtra(ACTION, Action.REQUEST_PAY);
        intent.putExtra(APPID, params.dappId);
        intent.putExtra(CONTENT, gson.toJson(params));
        intent.putExtra(Constant.EXTRA_BUNDLE_SOURCE, context.getPackageName());
        return intent;
    }

    public static void placeOrder(Activity activity,  NewAuthProof params) {
        Intent intent = getRequestProofIntent(params, activity);
        checkAndStartActivity(activity, intent, REQUEST_CODE_PUSH_ORDER);
    }

    public static void placeOrder(Fragment fragment,  NewAuthProof params) {
        Intent intent = getRequestProofIntent(params, fragment.getContext());
        checkAndStartActivity(fragment, intent, REQUEST_CODE_PUSH_ORDER);
    }

    private static Intent getRequestProofIntent(NewAuthProof params, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorize_login_place));
        intent.putExtra(ACTION, Action.PUSH_ORDER);
        intent.putExtra(APPID, params.dappId);
        intent.putExtra(CONTENT, gson.toJson(params));
        intent.putExtra(Constant.EXTRA_BUNDLE_SOURCE, context.getPackageName());
        return intent;
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
