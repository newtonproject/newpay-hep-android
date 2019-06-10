package com.newmall.server;

import com.newmall.constant.Action;

import org.newtonproject.newpay.android.sdk.bean.NewAuthLogin;
import org.newtonproject.newpay.android.sdk.bean.NewAuthPay;
import org.newtonproject.newpay.android.sdk.bean.NewAuthProof;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weixuefeng@lubangame.com
 * @version $Rev$
 * @time: 2019-06-10--11:32
 * @description
 * @copyright (c) 2018 Newton Foundation. All rights reserved.
 */
public class Request {
    private static final String privateKey = "0x298b9bee0a0431e8f1a81323df6810b72467db21f9c252cb6d134d149005a386";
    private static final String SIGN_TYPE = "secp256r1";

    public static NewAuthLogin authLogin() {
        NewAuthLogin params = new NewAuthLogin();
        HashMap<String, String> map = getBaseParams();
        params.dappId = map.get("dapp_id");
        params.protocol = map.get("protocol");
        params.version = map.get("version");
        params.ts = map.get("ts");
        params.nonce = map.get("nonce");

        // extra params
        String action = Action.REQUEST_PROFILE;
        String scope = "2";
        String memo = "Demo request login";
        params.action = action;
        params.scope = scope;
        params.memo = memo;
        map.put("action", action);
        map.put("scope", scope);
        map.put("memo", memo);
        // sign params
        params.signature = sign(map);
        params.signType = SIGN_TYPE;
        return params;
    }

    public static NewAuthPay authPay(String orderNumber, String totalPrice) {
        NewAuthPay params = new NewAuthPay();
        HashMap<String, String> map = getBaseParams();
        params.dappId = map.get("dapp_id");
        params.protocol = map.get("protocol");
        params.version = map.get("version");
        params.ts = map.get("ts");
        params.nonce = map.get("nonce");

        // extra params
        String action = Action.REQUEST_PAY;
        String description = "Demo request login";
        String priceCurrency = "NEW";
        String sellerNewid = "SellerNewID";
        String customer = "CustomerNewID";
        String broker = "BrokerNewId";

        params.action = action;
        params.description = description;
        params.orderNumber = orderNumber;
        params.priceCurrency = priceCurrency;
        params.totalPrice = totalPrice;
        params.seller = sellerNewid;
        params.customer = customer;
        params.broker = broker;


        map.put("action", action);
        map.put("description", description);
        map.put("price_currency", priceCurrency);
        map.put("total_price", totalPrice);
        map.put("order_number", orderNumber);
        map.put("seller", sellerNewid);
        map.put("customer", customer);
        map.put("broker", broker);
        // sign params
        params.signature = sign(map);
        params.signType = SIGN_TYPE;
        return params;
    }

    public static NewAuthProof authProof() {
        NewAuthProof params = new NewAuthProof();
        HashMap<String, String> map = getBaseParams();
        params.dappId = map.get("dapp_id");
        params.protocol = map.get("protocol");
        params.version = map.get("version");
        params.ts = map.get("ts");
        params.nonce = map.get("nonce");

        // extra params
        String action = Action.PUSH_ORDER;
        // todo:you should push order info to hep node and get proof hash
        String proofHash = "proofHash";

        params.action = action;
        params.proofHash = proofHash;

        map.put("action", action);
        map.put("proof_hash", proofHash);
        // sign params
        params.signature = sign(map);
        params.signType = SIGN_TYPE;
        return params;
    }

    private static HashMap<String, String> getBaseParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put("dapp_id", "YourDappId");
        map.put("protocol", "HEP");
        map.put("version", "1.0");
        map.put("ts", System.currentTimeMillis() + "");
        map.put("nonce", "randomString");
        map.put("environment", "devnet"); // devnet, testnet, mainnet
        return map;
    }

    private static String sign(HashMap<String, String> map) {
        String message = sortParameters(map);
        return getSignature(privateKey, message);
    }

    public static String sortParameters(Map<String, String> params) {
        String[] keys = params.keySet().toArray(new String[]{});
        Arrays.sort(keys);
        ArrayList<String> signPair = new ArrayList<String>();
        for (String key : keys) {
            signPair.add(key + "=" + params.get(key));
        }
        return joinChar(signPair, "&");
    }

    private static String joinChar(ArrayList<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if(i < list.size() -1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    private static String getSignature(String privateKey, String message) {
        Sign.SignatureData sig = Sign.signMessage(message.getBytes(), ECKeyPair.create(Numeric.toBigInt(privateKey)));
        String r = Numeric.toHexString(sig.getR());
        String s = Numeric.toHexStringNoPrefix(sig.getS());
        String res = r + s;
        if(r.startsWith("0x") && !s.startsWith("0x") && res.length() == 130) {
            return res;
        }
        return null;
    }
}
