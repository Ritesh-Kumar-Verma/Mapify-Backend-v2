package com.Mapify.Mapify.model;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;

@Entity
public class UsersLocationData {

    @Id
    private int id;
    private double latitude;
    private double longitude;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Users user;

    UsersLocationData(){

    }
    public UsersLocationData(Users user ,double latitude , double longitude){
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UsersLocationData{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
