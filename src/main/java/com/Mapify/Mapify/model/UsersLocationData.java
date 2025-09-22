package com.Mapify.Mapify.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UsersLocationData {

    @Id
    private int id;
    private long latitude;
    private long longitude;

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

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }
}
