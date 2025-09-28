package com.Mapify.Mapify.model;

import jakarta.persistence.*;

@Entity
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Users sender;

    @ManyToOne
    private Users receiver;

    private String status;




    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Friendship() {
    }

    public Friendship(Users sender, Users receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = "Pending";
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getSender() {
        return sender;
    }

    public void setSender(Users sender) {
        this.sender = sender;
    }

    public Users getReceiver() {
        return receiver;
    }

    public void setReceiver(Users receiver) {
        this.receiver = receiver;
    }
}
