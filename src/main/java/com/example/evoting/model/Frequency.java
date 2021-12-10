package com.example.evoting.model;

import javax.persistence.*;

@Entity
public class Frequency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long candidateId;
    private long counter;

    public Frequency() {}

    public Frequency(long candidateId, long counter) {
        this.candidateId = candidateId;
        this.counter = counter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCandidateId() {
        return candidateId;
    }

    public long getCounter() {
        return counter;
    }

    public void setCandidateId(long candidateId) {
        this.candidateId = candidateId;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }
}
