package com.example.evoting.blind.signature.ecdsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class UserSender {

    BigInteger saveCPrim;

    public BigInteger step1(BigInteger q, BigInteger G) {
        // The signer S randomly chooses an integer k1 from 2 to q − 1 and computes K1 and sends it to the recipient R
//        BigInteger max = q.subtract(new BigInteger("1"));
//        BigInteger min = new BigInteger("2");
//        BigInteger range = (max.subtract(min)).add(new BigInteger("1"));
        Random rand = new Random();
        BigInteger k1 = new BigInteger(3, rand);
        return k1.multiply(G);
    }

    public BigInteger step3(ArrayList<BigInteger> list, BigInteger q, BigInteger K1, String voteId, BigInteger sk) {
        // After receiving (N, g, C1, C2) and verifying that (C1, C2) are constructed correctly,
        // the signer S randomly choose r from 2 to N and computes C and sends C to the recipient R.
        BigInteger N = list.get(0);
        BigInteger g = list.get(1);
        BigInteger C1 = list.get(2);
        BigInteger C2 = list.get(3);
        Random rand = new Random();
        BigInteger r = new BigInteger(4, rand);

        return ((C1.multiply(C2.pow(sk.intValue())).pow((K1.modInverse(q)).intValue())).multiply(r.pow(N.intValue()))).mod(N.multiply(N));
    }


    public long zeroKnowLedgeSubmitCPrim(BigInteger cPrim){
        this.saveCPrim = cPrim;
        return (long)((Math.random() * 2));
    }

    public boolean zeroKnowLedgeSubmitPrim(BigInteger mPrim, BigInteger rPrim, BigInteger N, BigInteger g){
        return Objects.equals(this.saveCPrim, g.pow(mPrim.intValue()).multiply(rPrim.modPow(N, N.multiply(N))));
    }

    public boolean zeroKnowLedgeSubmitSec(BigInteger mSec, BigInteger rSec, BigInteger N, BigInteger g, BigInteger C) {
        return C.multiply(this.saveCPrim).equals(g.pow(mSec.intValue()).multiply(rSec.modPow(N, N.multiply(N))));
    }
}
