package com.example.evoting.model;

import javax.persistence.*;

@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long amount;
    private String fromAddress;
    private long timestamp;
    private String toAddress;
    private long blockId;

    public Transactions() {}

    public Transactions(long amount, String fromAddress, long timestamp, String toAddress, long blockId) {
        this.amount = amount;
        this.fromAddress = fromAddress;
        this.timestamp = timestamp;
        this.toAddress = toAddress;
        this.blockId = blockId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getAmount() {
        return amount;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public long getBlockId() {
        return blockId;
    }

    public void setBlockId(long blockId) {
        this.blockId = blockId;
    }
}
