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
```

#### send the proof parameter to newpay
``` java
NewPaySDK.placeOrder(Activity activity , NewAuthProof proof);
```

#### receive the proof information from newpay
```java
if(requestCode == NewPaySDK.REQUEST_CODE_PUSH_ORDER) {
    String res = data.getStringExtra(SIGNED_PROOF);
    ConfirmedProof proof = gson.fromJson(res, ConfirmedProof.class);
    Toast.makeText(this, proof.proofHash, Toast.LENGTH_SHORT).show();
}
```

## 6. Verify the response data
``` python
HepProfile， ConfirmedPayment, ConfirmedProof
1. verify profile
    auth_helper.validate_auth_callback(hep_profile)
2. verify pay signature
    is_valid = pay_helper.validate_pay_callback(confirmed_payment)
    if is_valid:
        valid_transaction = pay_helper.validate_transaction(confirmed_payment.txid)
        ---
        {'block_height': 904704,
         'from_address': 'NEW17xYWcvn5cp7rgYubVeenHZLGDJ5JtJapUPm',
         'order_number': '2d1682abec4c40a793a47127f2ad3301',
         'status': 1,
         'to_address': 'NEW17xYWcvn5cp7rgYubVeenHZLGDJ5JtJapUPm',
         'txid': '0xd7ab1ddcad52efd96298610030c985083cb6639b3f0f07c86f51ea7845a61237',
         'value': '100'
         }
3. verify proof signature
    is_valid = pay_helper.validate_proof_callback(confirmed_proof)
    if is_valid:
        valid_proof = pay_helper.validate_transaction(confirmed_payment.txid)

```
TBD
