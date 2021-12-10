package com.example.evoting.blind.signature.ecdsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class EvotingReceiver {

    BigInteger saveK2;
    BigInteger saveP;
    BigInteger saveT;

    public ArrayList<BigInteger> step2(BigInteger q, BigInteger K1, String voteId) {
        // After receiving K1 from the signer, the recipient R randomly choose an integer k2 from 2 to q − 1 and computes K
//        long max = q-1;
//        long min = 2;
//        long range = max - min + 1;
        Random rand = new Random();
        BigInteger k2 = new BigInteger(3, rand);
        this.saveK2 = k2;
        BigInteger K = k2.multiply(K1);

        // Next, the recipient R follows the modified Paillier cryptosystem, described
        //in the last session, to chooses two distinct large primes p, t, and computes the
        //public key (N, g).
        BigInteger p, t;
        while(true) {
            p = getRandomPrime();
            this.saveP = p;
            t = getRandomPrime();
            this.saveT = t;
            if(gcdByBruteForce(p.subtract(new BigInteger("1")).intValue(), q.intValue()) == 1 && gcdByBruteForce(t.subtract(new BigInteger("1")).intValue(), q.intValue()) == 1) {
                break;
            }
        }

        BigInteger N = p.multiply(q).multiply(t);
        BigInteger g = (new BigInteger("1").add(N)).modPow(p.multiply(t), N.multiply(N));
//        long g = (long)(Math.pow((1 + N), p * t)%(N * N));

        // encrypt h and Kx
//        long r1 = (long)(Math.random() * N * N) + 1;
//        long r2 = (long)(Math.random() * N * N) + 1;
        BigInteger r1 = new BigInteger(6, rand);
        BigInteger r2 = new BigInteger(6, rand);
        BigInteger C1 = g.pow(Integer.parseInt(voteId)).multiply(r1.modPow(N, N.multiply(N)));
        BigInteger C2 = g.pow(k2.intValue()).multiply(r2.modPow(N, N.multiply(N)));
//        long C1 = (long)(Math.pow(g, Double.parseDouble(voteId)) * Math.pow(r1, N)) % (N * N);
//        long C2 = (long)(Math.pow(g,k2) * Math.pow(r2, N)) % (N * N);
        // submits (N, g, C1, C2) to the signer S.
        ArrayList<BigInteger> list = new ArrayList<BigInteger>();

        // In addition, the recipient R needs to prove to the signer that the encryptions are constructed correctly by zero-knowledge proof
        boolean test1 = zeroKnowledgeProof(q, N, g, K,r1,C1);
        boolean test2 = zeroKnowledgeProof(q, N, g, k2,r2,C2);
        if (test1 && test2) {
            list.add(N);
            list.add(g);
            list.add(C1);
            list.add(C2);
        }
        return list;

    }

    public BigInteger step4(BigInteger C, BigInteger q, BigInteger N){
        // After receiving C, the recipient R computes s
        BigInteger exp = (this.saveP.subtract(new BigInteger("1"))).multiply(q.subtract(new BigInteger("1"))).multiply(this.saveT.subtract(new BigInteger("1")));
        BigInteger D = C.modPow(exp, N.multiply(N));
        return saveK2.modInverse(q).multiply(D);
    }

    public BigInteger verify(BigInteger C, BigInteger q, BigInteger N, BigInteger s){
        // After receiving C, the recipient R computes s
        BigInteger exp = (this.saveP.subtract(new BigInteger("1"))).multiply(q.subtract(new BigInteger("1"))).multiply(this.saveT.subtract(new BigInteger("1")));
        BigInteger D = C.modPow(exp, N.multiply(N));
        return s.modInverse(q).multiply(D);
    }

    public boolean zeroKnowledgeProof(BigInteger q, BigInteger N, BigInteger g, BigInteger m, BigInteger r, BigInteger C) {
        UserSender sender = new UserSender();
        Random rand = new Random();
        boolean checkIfCPrim = true, checkIfSec = true;
        BigInteger mPrim = new BigInteger(3, rand);
        BigInteger rPrim = new BigInteger(6, rand);
        BigInteger cPrim = g.pow(mPrim.intValue()).multiply(rPrim.modPow(N, N.multiply(N)));

        while(true) {
            checkIfSec = true;
            long b = sender.zeroKnowLedgeSubmitCPrim(cPrim);
            if(b == 0) {
                checkIfCPrim = sender.zeroKnowLedgeSubmitPrim(mPrim, rPrim, N, g);
            } else {
                BigInteger mSec = m.add(mPrim.mod(q));
                BigInteger rSec = r.add(rPrim.mod(N.multiply(N)));
                checkIfSec = sender.zeroKnowLedgeSubmitSec(mSec, rSec, N, g, C);
            }
            if(checkIfCPrim && checkIfSec) {
                return true;
            }
        }
    }

    public BigInteger getRandomPrime() {
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
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0))
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
}
