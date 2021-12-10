package com.example.evoting.ecdsa;


import com.example.evoting.blind.signature.ecdsa.UserSender;
import com.example.evoting.ecdsa.utils.BinaryAscii;
import com.example.evoting.ecdsa.utils.RandomInteger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;
import java.lang.*;

public class Ecdsa {


    public BigInteger saveCPrim;
    /**
     *
     * @param message message
     * @param privateKey privateKey
     * @param hashfunc hashfunc
     * @return Signature
     */

    public static Signature signMessage(String message, PrivateKey privateKey, MessageDigest hashfunc) {
        byte[] hashMessage = hashfunc.digest(message.getBytes());
        BigInteger numberMessage = BinaryAscii.numberFromString(hashMessage);
        Curve curve = privateKey.curve;
        BigInteger randNum = RandomInteger.between(BigInteger.ONE, curve.N);
        Point randomSignPoint = Math.multiply(curve.G, randNum, curve.N, curve.A, curve.P);
        BigInteger r = randomSignPoint.x.mod(curve.N);
        BigInteger s = ((numberMessage.add(r.multiply(privateKey.secret))).multiply(Math.inv(randNum, curve.N))).mod(curve.N);
        return new Signature(r, s);
    }

    /**
     *
     * @param message message
     * @param privateKey privateKey
     * @return Signature
     */
    public static Signature sign(String message, PrivateKey privateKey) {
        try {
            return signMessage(message, privateKey, MessageDigest.getInstance("SHA-256"));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not find SHA-256 message digest in provided java environment");
        }
    }

    /**
     *
     * @param message message
     * @param signature signature
     * @param publicKey publicKey
     * @param hashfunc hashfunc
     * @return boolean
     */
    public static boolean verifyMessage(String message, Signature signature, PublicKey publicKey, MessageDigest hashfunc) {
        byte[] hashMessage = hashfunc.digest(message.getBytes());
        BigInteger numberMessage = BinaryAscii.numberFromString(hashMessage);
        Curve curve = publicKey.curve;
        BigInteger r = signature.r;
        BigInteger s = signature.s;

        if (r.compareTo(new BigInteger(String.valueOf(1))) < 0) {
            return false;
        }
        if (r.compareTo(curve.N) >= 0) {
            return false;
        }
        if (s.compareTo(new BigInteger(String.valueOf(1))) < 0) {
            return false;
        }
        if (s.compareTo(curve.N) >= 0) {
            return false;
        }
        
        BigInteger w = Math.inv(s, curve.N);
        Point u1 =Math.multiply(curve.G, numberMessage.multiply(w).mod(curve.N), curve.N, curve.A, curve.P);
        Point u2 = Math.multiply(publicKey.point, r.multiply(w).mod(curve.N), curve.N, curve.A, curve.P);
        Point v = Math.add(u1, u2, curve.A, curve.P);
        if (v.isAtInfinity()) {
            return false;
        }

        return v.x.mod(curve.N).equals(r);
    }

    /**
     * 
     * @param message message
     * @param signature signature
     * @param publicKey publicKey
     * @return boolean
     */
    public static boolean verify(String message, Signature signature, PublicKey publicKey) {
        try {
            return verifyMessage(message, signature, publicKey, MessageDigest.getInstance("SHA-256"));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not find SHA-256 message digest in provided java environment");
        }
    }


    // ---------------------------------------------------------------------------
    // SING AND VERIFY WITH BLIND SIGNATURES
    public static Signature signMessageBlindSignature(String message, PrivateKey privateKey, MessageDigest hashfunc) {
        // step1
        // q = N
        Curve curve = privateKey.curve;
        BigInteger k1 = RandomInteger.between(BigInteger.TWO, curve.N);
        Point K1 = Math.multiply(curve.G, k1, curve.N, curve.A, curve.P);

        // step2
        BigInteger k2 = RandomInteger.between(BigInteger.TWO, curve.N);
        Point K = Math.multiply(K1, k2, curve.N, curve.A, curve.P);
        BigInteger r = K.x;

        BigInteger p, t;
        do {
            p = getRandomPrime();
            t = getRandomPrime();
        } while (gcdByBruteForce(p.subtract(new BigInteger("1")).intValue(), curve.N.intValue()) != 1 || gcdByBruteForce(t.subtract(new BigInteger("1")).intValue(), curve.N.intValue()) != 1);

        BigInteger NPk = p.multiply(curve.N).multiply(t);
        BigInteger nSquare = NPk.multiply(NPk);
        BigInteger gPK = (new BigInteger("1").add(NPk)).modPow(p.multiply(t), nSquare);
        BigInteger r1 = RandomInteger.between(BigInteger.TWO, nSquare);
        BigInteger r2 = RandomInteger.between(BigInteger.TWO, nSquare);

        BigInteger C1_1 = gPK.modPow(new BigInteger(message), nSquare);
        BigInteger C1_2 = r1.modPow(NPk, nSquare);
        BigInteger C1 = (C1_1.multiply(C1_2)).mod(nSquare);

        BigInteger C2_1 = gPK.modPow(K.x, nSquare);
        BigInteger C2_2 = r2.modPow(NPk, nSquare);
        BigInteger C2 = (C2_1.multiply(C2_2)).mod(nSquare);

        boolean test1 = zeroKnowledgeProof(curve, NPk, gPK, new BigInteger(message), r1, C1);
        boolean test2 = zeroKnowledgeProof(curve, NPk, gPK, K.x, r2, C2);

        BigInteger s = null;

        if(test1 && test2) {
            // step 3
            BigInteger rFinal = RandomInteger.between(BigInteger.TWO, nSquare);
            BigInteger C2_sk = C2.modPow(privateKey.secret, nSquare);
            BigInteger C1C2 = (C1.multiply(C2_sk)).mod(nSquare);
            BigInteger k1Inverse = k1.modInverse(curve.N);
            BigInteger rN = rFinal.modPow(NPk, nSquare);
            BigInteger C1C2f = C1C2.modPow(k1Inverse, nSquare);
            BigInteger cFinal = (C1C2f.multiply(rN)).mod(nSquare);

            // step 4
            BigInteger exp = (p.subtract(new BigInteger("1"))).multiply(curve.N.subtract(new BigInteger("1"))).multiply(t.subtract(new BigInteger("1")));
            BigInteger D = cFinal.modPow(exp, nSquare);
            s = k2.modInverse(curve.N).multiply(D.mod(curve.N));
        }

        // Kx = r, s = s
        return new Signature(r, s);
    }

    /**
     *
     * @param message message
     * @param privateKey privateKey
     * @return Signature
     */
    public static Signature signBlindSignature(String message, PrivateKey privateKey) {
        try {
            return signMessageBlindSignature(message, privateKey, MessageDigest.getInstance("SHA-256"));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not find SHA-256 message digest in provided java environment");
        }
    }

    /**
     *
     * @param message message
     * @param signature signature
     * @param publicKey publicKey
     * @param hashfunc hashfunc
     * @return boolean
     */
    public static boolean verifyMessageBlindSignature(String message, Signature signature, PublicKey publicKey, MessageDigest hashfunc) {
        // signature Kx,s
        // Kx = r, s = s
        Curve curve = publicKey.curve;
        BigInteger w = signature.s.modInverse(curve.N);
        Point u1 =Math.multiply(curve.G, new BigInteger(message).multiply(w).mod(curve.N), curve.N, curve.A, curve.P);
        Point u2 = Math.multiply(publicKey.point, signature.r.multiply(w).mod(curve.N), curve.N, curve.A, curve.P);
        Point v = Math.add(u1, u2, curve.A, curve.P);

        return v.x.equals(signature.r);
    }

    /**
     *
     * @param message message
     * @param signature signature
     * @param publicKey publicKey
     * @return boolean
     */
    public static boolean verifyBlindSignature(String message, Signature signature, PublicKey publicKey) {
        try {
            return verifyMessageBlindSignature(message, signature, publicKey, MessageDigest.getInstance("SHA-256"));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not find SHA-256 message digest in provided java environment");
        }
    }

    public static BigInteger getRandomPrime() {
        int num = 0;
        Random rand = new Random(); // generate a random number
        num = rand.nextInt(1000) + 1;

        while (!isPrime(num)) {
            num = rand.nextInt(1000) + 1;
        }
        return BigInteger.valueOf(num);
    }

    private static boolean isPrime(int inputNum){
        if (inputNum <= 3 || inputNum % 2 == 0)
            return inputNum == 2 || inputNum == 3;
        int divisor = 3;
        while ((divisor <= java.lang.Math.sqrt(inputNum)) && (inputNum % divisor != 0))
            divisor += 2;
        return inputNum % divisor != 0;
    }

    private static int gcdByBruteForce(int n1, int n2) {
        int gcd = 1;
        for (int i = 1; i <= n1 && i <= n2; i++) {
            if (n1 % i == 0 && n2 % i == 0) {
                gcd = i;
            }
        }
        return gcd;
    }


    public static boolean zeroKnowledgeProof(Curve curve, BigInteger NPk, BigInteger gPK, BigInteger message, BigInteger r, BigInteger C) {
        boolean checkIfCPrim = true, checkIfSec = true;
        BigInteger nSquare = NPk.multiply(NPk);
        BigInteger mPrim = RandomInteger.between(BigInteger.ONE, curve.N);
        BigInteger rPrim = RandomInteger.between(BigInteger.ONE, nSquare);
        BigInteger cPrim_1 = gPK.modPow(mPrim, nSquare);
        BigInteger cPrim_2 = rPrim.modPow(NPk, nSquare);
        BigInteger cPrim = (cPrim_1.multiply(cPrim_2)).mod(nSquare);

        while(true) {
            checkIfSec = true;
            long b = zeroKnowLedgeSubmitCPrim();
            if(b == 0) {
                checkIfCPrim = zeroKnowLedgeSubmitPrim(mPrim, rPrim, NPk, gPK, cPrim);
            } else {
                BigInteger mSec = message.add(mPrim.mod(curve.N));
                BigInteger rSec = r.mod(nSquare).multiply(rPrim.mod(nSquare));
                checkIfSec = zeroKnowLedgeSubmitSec(mSec, rSec, NPk, gPK, C, cPrim);
            }
            if(checkIfCPrim && checkIfSec) {
                return true;
            }
        }
    }

    public static long zeroKnowLedgeSubmitCPrim(){
        return (long)((java.lang.Math.random() * 2));
    }

    public static boolean zeroKnowLedgeSubmitPrim(BigInteger mPrim, BigInteger rPrim, BigInteger NPk, BigInteger gPK, BigInteger cPrim){
        BigInteger nSquare = NPk.multiply(NPk);
        BigInteger cPrim_1 = gPK.modPow(mPrim, nSquare);
        BigInteger cPrim_2 = rPrim.modPow(NPk, nSquare);
        BigInteger cPrimExecuted = (cPrim_1.multiply(cPrim_2)).mod(nSquare);
        return cPrim.equals(cPrimExecuted);
    }

    public static boolean zeroKnowLedgeSubmitSec(BigInteger mSec, BigInteger rSec, BigInteger NPk, BigInteger gPK, BigInteger C, BigInteger cPrim) {
        BigInteger nSquare = NPk.multiply(NPk);
        BigInteger cSec_1 = gPK.modPow(mSec, nSquare);
        BigInteger cSec_2 = rSec.modPow(NPk, nSquare);
        BigInteger CSec = (cSec_1.multiply(cSec_2)).mod(nSquare);
        return C.multiply(cPrim).equals(CSec);
    }
}
