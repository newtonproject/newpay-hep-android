# NewSdk

# 1.add gradle dependency on your app build.gradle
```
implementation 'org.newtonproject.newpay.android.sdk:NewPaySDK:1.0.0'
```
# 2. init NewPayApi on application
```
// in release environment
NewPayApi.init(getApplication(), yourPrivateKey, $yourtransaferId);

// in testnet, beta, dev and etc. environment
NewPayApi.init(getApplication(), yourPrivateKey, $yourtransaferId, Environment.DEVNET);
```
# 3. request profile information
```
NewPayApi.requestProfileFromNewPay(Activity activity);
```
# 4. get profile information and sigmessage
```
if(requestCode == NewPayApi.REQUEST_CODE_NEWPAY && resultCode == RESULT_OK) {
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
        if(requestCode == NewPayApi.REQUEST_CODE_NEWPAY && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "user canceled", Toast.LENGTH_LONG).show();
        }
        // pay result
        if(requestCode == NewPayApi.REQUEST_CODE_NEWPAY_PAY && resultCode == RESULT_OK){
            if(data != null) {
                String txid = data.getStringExtra("txid");
                Toast.makeText(this, "txid is:" + txid, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "error data is null", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == NewPayApi.REQUEST_CODE_NEWPAY_PAY && resultCode == RESULT_CANCELED){
            Toast.makeText(this, "User canceled", Toast.LENGTH_SHORT).show();
        }
```

# 5. request push order
```
  NewpayApi.requestPushOrder(Activity activity, ArrayList<Order> orders)

```



### Change log

# 1.0.0 add multiple environment init for NewMall.
