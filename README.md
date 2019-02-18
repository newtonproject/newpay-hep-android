# NewSdk

# 1.add gradle dependency on your app build.gradle
```
sdk core.
implementation 'org.newtonproject.newpay.sdk:newpay:1.0.5'

The signaure tools in Demo. On product environment, the signaure information must from server. 
implementation files('libs/crypto-3.3.1-android.jar')
implementation files('libs/utils-3.3.1-android.jar')
implementation 'com.madgag.spongycastle:core:1.58.0.0'
implementation "com.madgag.spongycastle:prov:1.58.0.0"
```
# 2. init NewPaySDK on application
```
// in release environment
NewPaySDK.init(getApplication(), $yourAppId);

// in testnet, beta, dev and etc. environment
NewPaySDK.init(getApplication(), $yourAppId, Environment.DEVNET);
```
# 3. request profile information
```
NewPaySDK.requestProfile(Activity activity);
```
# 4. get profile information and sigmessage
```
if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY && resultCode == RESULT_OK) {
            String profile = data.getStringExtra("profile");
            String sigMessage = data.getStringExtra("signature");
            Log.e(TAG, "onActivityResult: " + data.toString() );
            if(!TextUtils.isEmpty(profile)){
                ProfileInfo profileInfo = gson.fromJson(profile, ProfileInfo.class);
                cellphoneTextView.setText(profileInfo.cellphone);
                nameTextView.setText(profileInfo.name);
                newidTextView.setText(profileInfo.newid);
                Uri avatarUri = data.getData();
                imageView.setImageURI(avatarUri);
            }else{
                String error = data.getStringExtra("error");
                Log.e(TAG, "onActivityResult: " + error);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
            if(!TextUtils.isEmpty(sigMessage)) {
                SigMessage sig = gson.fromJson(sigMessage, SigMessage.class);
                Log.e(TAG, "onActivityResult: " + sig.toString());
            }
        }
        if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "user canceled", Toast.LENGTH_LONG).show();
        }
        // pay result
        if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY_PAY && resultCode == RESULT_OK){
            if(data != null) {
                String txid = data.getStringExtra("txid");
                Toast.makeText(this, "txid is:" + txid, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "error data is null", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY_PAY && resultCode == RESULT_CANCELED){
            Toast.makeText(this, "User canceled", Toast.LENGTH_SHORT).show();
        }
```

# 5. request push order
```
  NewPaySDK.placeOrder(Activity activity, SigMessage sigMessage);

```



### Change log

- 2019.2.12 1.0.0 

> add multiple environment init for NewMall.

- 2019.2.15 1.0.2

> Delete the initialize's parameter(PrivateKey)

> Change the funcation name: NewPayApi -> NewPaySDK.

- 2019.2.18 1.0.5

> Update procotol check. and package name check.

