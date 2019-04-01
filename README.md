
# NewPaySdk Documentation

# 1.Dependencies
Add the dependencies to your app-level `build.gradle` file.

```
implementation 'org.newtonproject.newpay.sdk:newpay:1.0.6'

//The signaure tools in Demo. On product environment, the signaure information must be from server.

implementation files('libs/crypto-3.3.1-android.jar')
implementation files('libs/utils-3.3.1-android.jar')
implementation 'com.madgag.spongycastle:core:1.58.0.0'
implementation "com.madgag.spongycastle:prov:1.58.0.0"
```

# 2. Init NewPaySDK
To init the SDK just call the `init` as below :
```
// in release environment
NewPaySDK.init(getApplication(), $yourAppId);

// in testnet, beta, dev, etc. environment
NewPaySDK.init(getApplication(), $yourAppId, Environment.DEVNET);
```

# 3. Get Profile and Sigmessage
In order to get the profile information, call the requestProfile function and catch the result in `onActivityResult`.
In Any case the SDK returns In any cases the SDK return the requestCode `NewPaySDK.REQUEST_CODE_NEWPAY`.
```
NewPaySDK.requestProfile(Activity activity);

...
	//RequestCode = NewPaySDK.REQUEST_CODE_NEWPAY
	if(resultCode == RESULT_OK) {
            String profile = data.getStringExtra("profile");
            String sigMessage = data.getStringExtra("signature");
	}
	
	if(resultCode == RESULT_CANCELED) {
        //Treat error
    }
        
	//RequestCode = NewPaySDK.REQUEST_CODE_NEWPAY_PAY
    if(resultCode == RESULT_OK){
            if(data != null) {
                String txid = data.getStringExtra("txid");
            }
	}
	if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY_PAY && resultCode == RESULT_CANCELED){
        //Treat error
}

```

# 4. Request Push Order

```
  NewPaySDK.placeOrder(Activity activity, SigMessage sigMessage);

```
