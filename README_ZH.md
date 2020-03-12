中文/[English](README.md)
# NewPaySDK Android 文档 

## 1.添加依赖项

在项目的 `build.gradle` 文件添加下面依赖.

```java
implementation 'org.newtonproject.newpay.sdk:newpay:2.0.6'

//签名工具，注意生产环境的签名应该由服务端完成
implementation files('libs/crypto-3.3.1-android.jar')
implementation files('libs/utils-3.3.1-android.jar')
implementation 'com.madgag.spongycastle:core:1.58.0.0'
implementation "com.madgag.spongycastle:prov:1.58.0.0"
```

## 2. 初始化 NewPaySDK

```java
// 默认正式环境
NewPaySDK.init(getApplication());

// 其他环境需要传递不同的参数，支持 DEV, TESTNT, BETA
NewPaySDK.init(getApplication(), Environment.DEVNET);
```

## 3. 获取用户基础信息

获取用户信息，使用`NewPaySDK.requestProfile()`函数，结果会在`onActivityResult`中返回，注意 `requestCode == NewPaySDK.REQUEST_CODE_NEWPAY`

#### 从自己服务端获取联合登录需要的参数信息
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
    "scope": "1", // 1 大致信息，没有手机号, 2 更详细的用户信息，包括手机号.
    "memo": "request memo",
    "sign_type": "secp256r1",
    "signature": "0x......."
}
```

#### 发送请求登录的参数到 NewPay
```java
NewPaySDK.requestProfile(Context context, NewAuthLogin authLogin);
```

#### 接收从newpay返回来的用户信息
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
#### 根据签名和参数,在自己服务端验证用户传回来的信息
```java POST PROFILE TO API
{
    "signature": "0x...",
    "sign_type": "secp256r1",
    "uuid": "uuid random string",
    "name": "profile name",
    "country_code": "user's country code",
    "cellphone": "user's cellphone",
    "avatar": "avatar path",
    "address": "user's address",
    "newid": "user's newid ",
    "invite_code": "user's invite code"
}
```
## 4. 请求支付

#### 从服务端获取支付需要的参数信息
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

#### 发送支付参数到NewPay
```java
  NewPaySDK.pay(Activity activity, NewAuthPay pay);
```

#### 接收支付结果信息
```java
if(requestCode == NewPaySDK.REQUEST_CODE_NEWPAY_PAY){
    String res = data.getStringExtra(SIGNED_PAY);
    ConfirmedPayment payment = gson.fromJson(res, ConfirmedPayment.class);
    Toast.makeText(this, "txid is:" + payment.txid, Toast.LENGTH_SHORT).show();
}
```

#### 服务端验证支付结果信息
```java POST PAY INFORMATION TO API
{
    "signature": "0x...",
    "sign_type": "secp256r1",
    "txid": "transaction id",
    "ts": "timestamp",
    "nonce": "random string",
    "order_number": "order number",
    "dapp_id": "dapp id",
    "uuid": "session id, random string"
}
```
## 5. 请求上链交易信息

#### 获取上链需要的参数信息
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

#### 发送上链信息到NewPay
``` java
NewPaySDK.placeOrder(Activity activity, NewAuthProof authProof);
```

#### 从NewPay接收上链结果信息
```java
if(requestCode == NewPaySDK.REQUEST_CODE_PUSH_ORDER) {
    String res = data.getStringExtra(SIGNED_PROOF);
    ConfirmedProof proof = gson.fromJson(res, ConfirmedProof.class);
    Toast.makeText(this, proof.proofHash, Toast.LENGTH_SHORT).show();
}
```

#### 验证上链信息
```java POST PROOF INFORMATION TO API
{
    "signature": "0x...",
    "sign_type": "secp256r1",
    "proof_hash": "proof hash",
    "ts": "timestamp",
    "nonce": "random string",
    "dapp_id": "dapp id",
    "uuid": "session id, random string"
}
```
## 6. 请求NewPay签名信息，一般为字符串

### 获取签名需要的参数，用户输入的 `message`，到服务端进行签名
```java
Observable<BaseResponse<NewSignMessage>> getSignMessage(@Field("message") String message, @Field("os") String os);

{
    "uuid": "session_id,random string",
    "dapp_id": "your dapp id",
    "protocol": "HEP",
    "version": "1.0",
    "ts": "timestamp",
    "nonce": "random string",
    "action": "hep.sign.message",
    "sign_type": "secp256r1",
    "signature": "0x.......",
    "message": "need sign message"
}
```
#### 发送签名信息到NewPay
``` java
NewPaySDK.requestSignMessage(Activity activity, NewSignMessage newSignMessage);
```

#### 接收签名结果
```java
 if(requestCode == NewPaySDK.REQUEST_CODE_SIGN_MESSAGE) {
     String res = data.getStringExtra(SIGNED_SIGN_MESSAGE); //字段详见 demo
     Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
 }
```
## 7. 请求签署交易

### 获取需要签名的交易信息，包括基础交易信息和交易信息在自己dapp的签名
```java
 Observable<BaseResponse<NewSignTransaction>> getSignTransaction(@Body BaseTransaction transaction);
{
      "uuid": "session_id,random string",
      "dapp_id": "your dapp id",
      "protocol": "HEP",
      "version": "1.0",
      "ts": "timestamp",
      "nonce": "random string",
      "signType" = "secp256r1", 
      "signature" = "0x...", 
      "action" = "hep.sign.transaction",
      "amount" = "100", 
      "from" = "0x2342", 
      "to" = "0x1231231243", 
      "gasLimit" = "100", 
      "gasPrice" = "123221", 
      "transactionCount" = "12", 
      "data" = "0x123123"
}
```
#### 发送需要签署的交易信息到NewPay
``` java
NewPaySDK.requestSignTransaction(Activity activity, NewSignTransaction newSignTransaction);
```

#### 从newpay接收交易签名结果
```java
  if(requestCode == NewPaySDK.REQUEST_CODE_SIGN_TRANSACTION) {
       String res = data.getStringExtra(SIGNED_SIGN_TRANSACTION);
       Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
  }
```
