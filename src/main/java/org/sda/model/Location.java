package org.sda.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Double latitude;
    private Double longitude;
    private String region;
    private String country;

    public Location() {
    }

    public Location(UUID id, Double latitude, Double longitude, String region, String country) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.region = region;
        this.country = country;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
