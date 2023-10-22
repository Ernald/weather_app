package org.sda.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "weather_data")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private Double temperature;
    private Double humiduty;
    private Double pressure;
    private Double windspeed;
    private Double windDirection;

    public WeatherData() {
    }

    public WeatherData(Long id, Date date, Double temperature, Double humiduty, Double pressure, Double windspeed, Double windDirection) {
        this.id = id;
        this.date = date;
        this.temperature = temperature;
        this.humiduty = humiduty;
        this.pressure = pressure;
        this.windspeed = windspeed;
        this.windDirection = windDirection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumiduty() {
        return humiduty;
    }

    public void setHumiduty(Double humiduty) {
        this.humiduty = humiduty;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(Double windspeed) {
        this.windspeed = windspeed;
    }

    public Double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Double windDirection) {
        this.windDirection = windDirection;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", date=" + date +
                ", temperature=" + temperature +
                ", humiduty=" + humiduty +
                ", pressure=" + pressure +
                ", windspeed=" + windspeed +
                ", windDirection=" + windDirection +
                '}';
    }
}
