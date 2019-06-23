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

```java
NewPaySDK.requestProfile(Context context, NewAuthLogin authLogin);

---
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

```java
  NewPaySDK.pay(Activity activity, NewAuthPay pay);

---
if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY_PAY){
    String res = data.getStringExtra(SIGNED_PAY);
    ConfirmedPayment payment = gson.fromJson(res, ConfirmedPayment.class);
    Toast.makeText(this, "txid is:" + payment.txid, Toast.LENGTH_SHORT).show();
}

```
## 5. Request submit place order

``` java
NewPaySDK.placeOrder(Activity activity, NewAuthProof authProof);

---
if(requestCode == NewPaySDK.REQUEST_CODE_PUSH_ORDER) {
    String res = data.getStringExtra(SIGNED_PROOF);
    ConfirmedProof proof = gson.fromJson(res, ConfirmedProof.class);
    Toast.makeText(this, proof.proofHash, Toast.LENGTH_SHORT).show();
}
```

## 6. Verify the response on server
