# NewPaySDK Android Documentation

## 1.Dependencies

Add the dependencies to your app-level `build.gradle` file.

```java
implementation 'org.newtonproject.newpay.sdk:newpay:2.0.1'

//The signature tools in Demo. On production environment, the signature information must be from server.

implementation files('libs/crypto-3.3.1-android.jar')
implementation files('libs/utils-3.3.1-android.jar')
implementation 'com.madgag.spongycastle:core:1.58.0.0'
implementation "com.madgag.spongycastle:prov:1.58.0.0"
```

## 2. Init NewPaySDK

```java
// in release environment
NewPaySDK.init(getApplication());

// in testnet, beta, dev, etc. environment
NewPaySDK.init(getApplication(), Environment.DEVNET);
```

## 3. Get Profile and SigMessage

To get the profile information, call the requestProfile function and catch the result in `onActivityResult`.
In any case the SDK returns the requestCode `NewPaySDK.REQUEST_CODE_NEWPAY`.

#### get the login parameters
```java
Observable<BaseResponse<NewAuthLogin>> getAuthLogin(@Field("os") String os);

{
    "uuid": "session_id,random string",
    "dapp_id": "your dapp id",
    "protocol": "HEP",
    "version": "1.0",
    "ts": "timestamp",
    "nonce": "random string",
    "action": "hep.auth.login",
    "scope": "1", // 1 is summary, 2 is detail for profile.
    "memo": "request memo",
    "sign_type": "secp256r1",
    "signature": "0x......."
}
```

#### send the login parameter to Newpay
```java
NewPaySDK.requestProfile(Context context, NewAuthLogin authLogin);
```

#### receive the user profile from newpay
```java
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
```

## 4. Request Pay

#### get the pay parameters
```java
Observable<BaseResponse<NewAuthPay>> getAuthPay(@Field("newid") String newid, @Field("os") String os);

{
    "uuid": "session_id,random string",
    "dapp_id": "your dapp id",
    "protocol": "HEP",
    "version": "1.0",
    "ts": "timestamp",
    "nonce": "random string",
    "action": "hep.pay.order",
    "description": "order description",
    "price_currency": "CNY", //NEW...
    "total_price": "100",
    "order_number": "order number",
    "seller": "sellerNewid",
    "customer": "customer Newid",
    "broker": "broker Newid",
    "sign_type": "secp256r1",
    "signature": "0x......."
}
```

#### send the pay parameter to newpay
```java
  NewPaySDK.pay(Activity activity, NewAuthPay pay);
```

#### receive the pay information from newpay
```java
if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY_PAY){
    String res = data.getStringExtra(SIGNED_PAY);
    ConfirmedPayment payment = gson.fromJson(res, ConfirmedPayment.class);
    Toast.makeText(this, "txid is:" + payment.txid, Toast.LENGTH_SHORT).show();
}

```
## 5. Request submit place order

#### get the proof parameters
```java
Observable<BaseResponse<NewAuthProof>> getAuthProof(@Field("newid") String newid, @Field("os") String os);

{
    "uuid": "session_id,random string",
    "dapp_id": "your dapp id",
    "protocol": "HEP",
    "version": "1.0",
    "ts": "timestamp",
    "nonce": "random string",
    "action": "hep.proof.submit",
    "proof_hash": "proof hash"
    "sign_type": "secp256r1",
    "signature": "0x......."
}
```

#### send the proof parameter to newpay
``` java
NewPaySDK.placeOrder(Activity activity, NewAuthProof authProof);

#### receive the proof information from newpay
```java
if(requestCode == NewPaySDK.REQUEST_CODE_PUSH_ORDER) {
    String res = data.getStringExtra(SIGNED_PROOF);
    ConfirmedProof proof = gson.fromJson(res, ConfirmedProof.class);
    Toast.makeText(this, proof.proofHash, Toast.LENGTH_SHORT).show();
}
```

## 6. Verify the response on server
