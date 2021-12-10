package com.example.evoting.controller;

import com.example.evoting.blind.signature.ecdsa.BlindSignatureECDSA;
import com.example.evoting.ecdsa.*;
import com.example.evoting.ecdsa.PrivateKey;
import com.example.evoting.ecdsa.PublicKey;
import com.example.evoting.ecdsa.Signature;
import com.example.evoting.model.Block;
import com.example.evoting.model.Frequency;
import com.example.evoting.model.User;
import com.example.evoting.model.UserPrivateKey;
import com.example.evoting.repository.BlockRepository;
import com.example.evoting.repository.FrequencyRepository;
import com.example.evoting.repository.UserPrivateKeyRepository;
import com.example.evoting.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@CrossOrigin(origins =  "http://localhost:4200")
public class BlockController {

    private final BlockRepository blockRepository;
    private final FrequencyRepository frequencyRepository;
    private final UserRepository userRepository;
    private final UserPrivateKeyRepository userPrivateKeyRepository;

    public BlockController(BlockRepository blockRepository, FrequencyRepository frequencyRepository, UserRepository userRepository, UserPrivateKeyRepository userPrivateKeyRepository) {
        this.blockRepository = blockRepository;
        this.frequencyRepository = frequencyRepository;
        this.userRepository = userRepository;
        this.userPrivateKeyRepository = userPrivateKeyRepository;
    }

    @GetMapping("/blocks")
    public List<Block> getAllBlocks() {
        return (List<Block>) blockRepository.findAll();
    }

    @PostMapping("/blocks/create")
    public long addBlock(@RequestBody Block block) {
        blockRepository.save(block);
        return block.getId();
    }

    @PostMapping("blocks/blind-signature/{id}")
    public boolean signBlindSignature(@PathVariable String id) throws InvalidAlgorithmParameterException, UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidKeySpecException {
        BlindSignatureECDSA digiSig = new BlindSignatureECDSA();
//        JSONObject obj = digiSig.isVerified(id);
//        return digiSig.receiver(obj);
//        if(digiSig.isVerified(id)) {
//            Frequency f = frequencyRepository.findFrequencyByCandidateId(Long.parseLong(id));
//            f.setCounter(f.getCounter() + 1);
//            frequencyRepository.save(f);
//            return true;
//        }

//
        User user = userRepository.findByIsLogged(true);
        PrivateKey privateKey = new PrivateKey();
        privateKey.secret = userPrivateKeyRepository.findUserPrivateKeyByUserId(user.getId()).getPrivateKey();
        PublicKey publicKey = privateKey.publicKey();

        // SING AND VERIFY WITH ECDSA
//        Signature signature = Ecdsa.sign(id, privateKey);
//        boolean verified = Ecdsa.verify(id, signature, publicKey) ;

        // SING AND VERIFY WITH ECDSA WITH BLIND SIGNATURES
        Signature signature = Ecdsa.signBlindSignature(id, privateKey);
        boolean verified = Ecdsa.verifyBlindSignature(id, signature, publicKey) ;

        if(verified) {
            Frequency f = frequencyRepository.findFrequencyByCandidateId(Long.parseLong(id));
            f.setCounter(f.getCounter() + 1);
            frequencyRepository.save(f);
            return true;
        }

        return false;
    }

    @PostMapping("/blocks/add-private-key")
    void addPrivateKey(@RequestBody String privateKey) {
        User user = userRepository.findByIsLogged(true);
        UserPrivateKey userPrivateKey = new UserPrivateKey(user.getId(), new BigInteger(privateKey));
        userPrivateKeyRepository.save(userPrivateKey);
    }

}
