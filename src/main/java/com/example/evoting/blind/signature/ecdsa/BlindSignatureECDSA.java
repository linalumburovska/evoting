package com.example.evoting.blind.signature.ecdsa;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;

import org.json.JSONObject;

public class BlindSignatureECDSA {

    public boolean isVerified(String voteId) {
        // SIGN
        UserSender sender = new UserSender();
        EvotingReceiver receiver = new EvotingReceiver();

        Random rand = new Random();
        BigInteger q = new BigInteger(3, rand);
        BigInteger G = new BigInteger(4, rand);
        BigInteger sk = new BigInteger(3, rand);

        BigInteger K1 = sender.step1(q, G);
        ArrayList<BigInteger> list = receiver.step2(q,K1, voteId);
        BigInteger C = sender.step3(list, q, K1, voteId, sk);
        BigInteger s = receiver.step4(C, q, list.get(0));

        System.out.println(K1);
        System.out.println(s);
        // (K1, s) is a valid ECDSA signature.

        // VERIFY
        // if this method returns true, the signature is valid and in the controller the value for the vote is increase by one
        System.out.println(calculateVerification(C, q, list.get(0),s, list, K1, G, sk));

        return true;
    }

    public boolean calculateVerification(BigInteger C, BigInteger q, BigInteger N, BigInteger s, ArrayList<BigInteger> list, BigInteger K1, BigInteger G, BigInteger sk) {
        EvotingReceiver receiver = new EvotingReceiver();
        BigInteger u = receiver.verify(C, q, list.get(0),s);
        BigInteger v = s.modInverse(q).multiply(K1);
        BigInteger test = u.multiply(G).add(v.multiply(sk).multiply(G));

        BigInteger k1Prim = test.divide(sk.multiply(G));

        // the signature is valid
        return Objects.equals(K1, k1Prim);
    }

//    private static final String SPEC = "secp256r1";
//    private static final String ALGO = "SHA256withECDSA";
//
//    public JSONObject sender(String voteId) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
//
//        ECGenParameterSpec ecSpec = new ECGenParameterSpec(SPEC);
//        KeyPairGenerator g = KeyPairGenerator.getInstance("EC");
//        g.initialize(ecSpec, new SecureRandom());
//        KeyPair keypair = g.generateKeyPair();
//        PublicKey publicKey = keypair.getPublic();
//        PrivateKey privateKey = keypair.getPrivate();
//
//        Signature ecdsaSign = Signature.getInstance(ALGO);
//        ecdsaSign.initSign(privateKey);
//        ecdsaSign.update(voteId.getBytes("UTF-8"));
//        byte[] signature = ecdsaSign.sign();
//        String pub = Base64.getEncoder().encodeToString(publicKey.getEncoded());
//        String sig = Base64.getEncoder().encodeToString(signature);
//
//        JSONObject obj = new JSONObject();
//        obj.put("publicKey", pub);
//        obj.put("signature", sig);
//        obj.put("message", voteId);
//        obj.put("algorithm", ALGO);
//
//        return obj;
//    }
//
//    public boolean receiver(JSONObject obj) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
//
//        Signature ecdsaVerify = Signature.getInstance(obj.getString("algorithm"));
//        KeyFactory kf = KeyFactory.getInstance("EC");
//
//        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(obj.getString("publicKey")));
//
//        KeyFactory keyFactory = KeyFactory.getInstance("EC");
//        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
//
//        ecdsaVerify.initVerify(publicKey);
//        ecdsaVerify.update(obj.getString("message").getBytes("UTF-8"));
//
//        return ecdsaVerify.verify(Base64.getDecoder().decode(obj.getString("signature")));
//    }

}