package com.newmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newmall.network.BaseResponse;
import com.newmall.network.HttpService;
import com.squareup.picasso.Picasso;

import org.newtonproject.newpay.android.sdk.NewPaySDK;
import org.newtonproject.newpay.android.sdk.bean.HepProfile;
import org.newtonproject.newpay.android.sdk.bean.NewAuthLogin;
import org.newtonproject.newpay.android.sdk.bean.NewAuthPay;
import org.newtonproject.newpay.android.sdk.bean.NewAuthProof;
import org.newtonproject.newpay.android.sdk.constant.Environment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout profileLinearLayout;
    private TextView nameTextView;
    private TextView cellphoneTextView;
    private TextView newidTextView;
    private ImageView imageView;
    private String TAG = "Activity";

    private static final String ERROR_CODE = "ERROR_CODE";
    private static final String ERROR_MESSAGE = "ERROR_MESSAGE";


    TextView dev;
    TextView beta;
    TextView testnet;
    TextView mainnet;
    TextView evn;

    //Profile key
    private static final String SIGNED_PROFILE = "SIGNED_PROFILE";
    private static final String SIGNED_PROOF = "SIGNED_PROOF";
    private static final String dappId = "5b796b9b48f74f28b96bcd3ea42f9aaf";
    private HepProfile profileInfo;

    private static final int REQUEST_CODE_NEWPAY = 1000;
    Gson gson = new Gson();
    private Button request20Bt;
    private Button single;
    private Button multiple;
    private MainActivity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        NewPaySDK.init(getApplication());
        context = this;
    }

    private void initView() {
        profileLinearLayout = findViewById(R.id.profileLayout);
        nameTextView = findViewById(R.id.nameTextView);
        cellphoneTextView = findViewById(R.id.cellphoneTextView);
        newidTextView = findViewById(R.id.newidTextView);
        imageView = findViewById(R.id.avatarImageView);
        request20Bt = findViewById(R.id.request20Bt);
        dev = findViewById(R.id.dev);
        beta = findViewById(R.id.beta);
        testnet = findViewById(R.id.testnet);
        mainnet = findViewById(R.id.mainnet);
        evn = findViewById(R.id.env);
        profileLinearLayout.setOnClickListener(this);
        request20Bt.setOnClickListener(this);
        dev.setOnClickListener(this);
        beta.setOnClickListener(this);
        mainnet.setOnClickListener(this);
        testnet.setOnClickListener(this);
        single = findViewById(R.id.pushSingle);
        multiple = findViewById(R.id.pushMultiple);
        single.setOnClickListener(this);
        multiple.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profileLayout:
                HttpService
                        .getInstance()
                        .getNewAuthLogin()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<BaseResponse<NewAuthLogin>>() {
                                    @Override
                                    public void accept(BaseResponse<NewAuthLogin> response) throws Exception {
                                        if(response.errorCode == 1) {
                                            NewPaySDK.requestProfile(context, response.result);
                                        }
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                break;
            case R.id.request20Bt:
                if(profileInfo == null || TextUtils.isEmpty(profileInfo.newid)) {
                    Toast.makeText(this, "Please get profile first", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpService
                        .getInstance()
                        .getNewAuthPay(profileInfo.newid)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<BaseResponse<NewAuthPay>>() {
                                    @Override
                                    public void accept(BaseResponse<NewAuthPay> response) throws Exception {
                                        if(response.errorCode == 1) {
                                            NewPaySDK.pay(context, response.result);
                                        }
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                break;
            case R.id.pushMultiple:
                //pushSingle();
                break;
            case R.id.pushSingle:
                pushSingle();
                break;
            case R.id.dev:
                evn.setText("Dev");
                NewPaySDK.init(getApplication(), Environment.DEVNET);
                break;
            case R.id.beta:
                evn.setText("Beta");
                NewPaySDK.init(getApplication(), Environment.BETANET);

                break;
            case R.id.testnet:
                evn.setText("testnet");
                NewPaySDK.init(getApplication(), Environment.TESTNET);

                break;
            case R.id.mainnet:
                evn.setText("main");
                NewPaySDK.init(getApplication(), Environment.MAINNET);
                break;
        }
    }

    private void pushSingle() {
        if(profileInfo == null || TextUtils.isEmpty(profileInfo.newid)) {
            Toast.makeText(context, "Please get profile newid first", Toast.LENGTH_SHORT).show();
            return;
        }
        Disposable subscribe = HttpService.getInstance().getNewAuthProof(profileInfo.newid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<NewAuthProof>>() {
                               @Override
                               public void accept(BaseResponse<NewAuthProof> newAuthProofBaseResponse) throws Exception {
                                    if(newAuthProofBaseResponse.errorCode == 1) {
                                        NewAuthProof proof = newAuthProofBaseResponse.result;
                                        NewPaySDK.placeOrder(context , proof);
                                    } else {
                                        Toast.makeText(context, newAuthProofBaseResponse.errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null) return;

        if(resultCode == RESULT_OK) {
            int errorCode = data.getIntExtra(ERROR_CODE, 0);
            String errorMessage = data.getStringExtra(ERROR_MESSAGE);
            if(errorCode != 1) {
                Log.e(TAG, "error_code is: " + errorCode);
                Log.e(TAG, "ErrorMessage is:" + errorMessage);
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                return;
            }
            if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY) {

                String profile = data.getStringExtra(SIGNED_PROFILE);

                if(!TextUtils.isEmpty(profile)){
                    profileInfo = gson.fromJson(profile, HepProfile.class);
                    cellphoneTextView.setText(profileInfo.cellphone);
                    nameTextView.setText(profileInfo.name);
                    newidTextView.setText(profileInfo.newid);
                    Log.e(TAG, "Profile:" + profileInfo);
                    if(!TextUtils.isEmpty(profileInfo.avatarPath)) {
                        Picasso.get().load(profileInfo.avatarPath).into(imageView);
                    }
                }
            }

            // pay result
            if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY_PAY){
                String txid = data.getStringExtra("txid");
                Toast.makeText(this, "txid is:" + txid, Toast.LENGTH_SHORT).show();
            }

            if(requestCode == NewPaySDK.REQUEST_CODE_PUSH_ORDER) {
                String res = data.getStringExtra(SIGNED_PROOF);
                Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public class ErrorCode {
        static final int SUCCESS = 1;

        static final int CANCEL = 2;

        static final int NO_NEWPAY = 100;
        static final int NO_PROFILE = 101;
        static final int NO_BUNDLE_SOURCE = 102;
        static final int SIGNATURE_ERROR = 103;
        static final int SELLER_NEWID_ERROR = 104;
        static final int PROTOCOL_VERSION_LOW = 105;
        static final int NO_ACTION = 106;
        static final int APPID_ERROR = 107;
        static final int NO_ORDER_INFO = 108;
        static final int NEWID_ERROR = 109;
        static final int NO_WALLET = 110;
        static final int UNKNOWN_ERROR = 1000;
    }
}
