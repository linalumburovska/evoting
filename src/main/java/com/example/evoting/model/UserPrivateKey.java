package com.example.evoting.model;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class UserPrivateKey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long userId;
    public BigInteger privateKey;

    public UserPrivateKey() {}

    public UserPrivateKey(long userId, BigInteger privateKey) {
        this.userId = userId;
        this.privateKey = privateKey;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }
}

