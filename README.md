# NewPaySDK Android Documentation

## 1.Dependencies

Add the dependencies to your app-level `build.gradle` file.

```java
implementation 'org.newtonproject.newpay.sdk:newpay:1.0.6'

//The signature tools in Demo. On production environment, the signature information must be from server.

implementation files('libs/crypto-3.3.1-android.jar')
implementation files('libs/utils-3.3.1-android.jar')
implementation 'com.madgag.spongycastle:core:1.58.0.0'
implementation "com.madgag.spongycastle:prov:1.58.0.0"
```

## 2. Init NewPaySDK

```java
// in release environment
NewPaySDK.init(getApplication(), $yourAppId);

// in testnet, beta, dev, etc. environment
NewPaySDK.init(getApplication(), $yourAppId, Environment.DEVNET);
```

## 3. Get Profile and Sigmessage

To get the profile information, call the requestProfile function and catch the result in `onActivityResult`.
In any case the SDK returns the requestCode `NewPaySDK.REQUEST_CODE_NEWPAY`.

```java
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

## 4. Request Push Order

```java
  NewPaySDK.placeOrder(Activity activity, SigMessage sigMessage);

```
