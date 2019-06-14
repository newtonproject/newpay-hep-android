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
import com.newmall.server.Request;
import com.squareup.picasso.Picasso;

import org.newtonproject.newpay.android.sdk.NewPaySDK;
import org.newtonproject.newpay.android.sdk.bean.ConfirmProof;
import org.newtonproject.newpay.android.sdk.bean.HepProfile;
import org.newtonproject.newpay.android.sdk.bean.ProfileInfo;
import org.newtonproject.newpay.android.sdk.constant.Environment;


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
    private static final String dappId = "05e6e4bbd71f4ab8ac382f8c0ccb8d0b";
    private HepProfile hepProfile;
    private ProfileInfo profileInfo;

    private static final int REQUEST_CODE_NEWPAY = 1000;
    private static final String privateKey = "0xfd216818cecbc78c0aeb274521b1501a01a2226a23a9a6922abb824b12dd86c4";
    private static final String publicKey = "0xb5de35a23f3b21b4c5750d02875af165796e5be673c684e53cf0f022bfe94e5e7df1867816d2869674006e08446bbe6cf21e401545e6e2ee43acc2d20b3ff168";
    Gson gson = new Gson();
    private Button request20Bt;
    private Button single;
    private Button multiple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        NewPaySDK.init(getApplication(), dappId);
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
                NewPaySDK.requestProfile(this, Request.authLogin());
                break;
            case R.id.request20Bt:
                NewPaySDK.pay(this, Request.authPay("orderNumber", "20"));
                break;
            case R.id.pushMultiple:
                pushSingle();
                break;
            case R.id.pushSingle:
                pushSingle();
                break;
            case R.id.dev:
                evn.setText("Dev");
                NewPaySDK.init(getApplication(), dappId, Environment.DEVNET);

                break;
            case R.id.beta:
                evn.setText("Beta");
                NewPaySDK.init(getApplication(), dappId, Environment.BETANET);

                break;
            case R.id.testnet:
                evn.setText("testnet");
                NewPaySDK.init(getApplication(), dappId, Environment.TESTNET);

                break;
            case R.id.mainnet:
                evn.setText("main");
                NewPaySDK.init(getApplication(), dappId, Environment.MAINNET);
                break;
        }
    }

    private void pushSingle() {
        NewPaySDK.placeOrder(this , Request.authProof());
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
                return;
            }
            if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY) {

                String profile = data.getStringExtra(SIGNED_PROFILE);

                if(!TextUtils.isEmpty(profile)){
                    hepProfile = gson.fromJson(profile, HepProfile.class);
                    profileInfo = hepProfile.getProfileInfo();

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
                ConfirmProof proof = gson.fromJson(res, ConfirmProof.class);
                Toast.makeText(this, proof.toString(), Toast.LENGTH_SHORT).show();
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
