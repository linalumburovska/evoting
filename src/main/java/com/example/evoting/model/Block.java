package com.example.evoting.model;

import javax.persistence.*;

@Entity
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String hash;
    private long nonce;
    private String previousHash;
    private long timestamp;

    public Block() {}

    public Block(String hash, long nonce, String previousHash, long timestamp) {
        this.hash = hash;
        this.nonce = nonce;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNonce() {
        return nonce;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
