package org.sda.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.sda.dao.WeatherDataDao;
import org.sda.model.Location;
import org.sda.model.WeatherData;

import java.util.List;

import static org.sda.util.HibernateUtil.sessionFactory;

public class WeatherDataServiceImpl implements WeatherDataService{
    private WeatherDataDao weatherDataDao;

    public WeatherDataServiceImpl(WeatherDataDao weatherDataDao){
        this.weatherDataDao = weatherDataDao;
    }

    @Override
    public WeatherData getWeatherDataById(Long id) {
        return weatherDataDao.findById(id);
    }

    @Override
    public List<WeatherData> getWeatherDataForLocation(Location location) {
        return weatherDataDao.findByLocation(location);
    }

    @Override
    public void addWeatherData(WeatherData weatherData) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(weatherData);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
        }finally {
            session.close();
        }
    }

    @Override
    public void updateWeatherdata(WeatherData weatherData) {
        weatherDataDao.update(weatherData);
    }

    @Override
    public void deleteWeatherData(WeatherData weatherData) {
        weatherDataDao.delete(weatherData);
    }
}
