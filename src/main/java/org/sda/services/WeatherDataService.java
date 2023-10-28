package org.sda.services;

import org.sda.model.Location;
import org.sda.model.WeatherData;

import java.util.List;

public interface WeatherDataService {
    public WeatherData getWeatherDataById(Long id);
    public List<WeatherData> getWeatherDataForLocation(Location location);
    public void addWeatherData(WeatherData weatherData);
    public void updateWeatherdata(WeatherData weatherData);
    public void deleteWeatherData(WeatherData weatherData);
}
