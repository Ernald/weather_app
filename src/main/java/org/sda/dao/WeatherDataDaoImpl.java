package org.sda.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.sda.model.Location;
import org.sda.model.WeatherData;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class WeatherDataDaoImpl implements WeatherDataDao{
    private SessionFactory sessionFactory;
    Session session = sessionFactory.getCurrentSession();
    public WeatherDataDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    @Override
    public WeatherData findById(Long id) {
        session = sessionFactory.getCurrentSession();
        return session.get(WeatherData.class, id);
    }

    @Override
    public List<WeatherData> findByLocation(Location location) {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<WeatherData> query = builder.createQuery(WeatherData.class);
            Root<WeatherData> root = query.from(WeatherData.class);
            query.select(root).where(builder.equal(root.get("location"),location));

            Query<WeatherData> q = session.createQuery(query);
            return q.getResultList();
        }
    }

    @Override
    public void save(WeatherData weatherData) {
        session = sessionFactory.getCurrentSession();
        session.save(weatherData);

    }

    @Override
    public void update(WeatherData weatherData) {
        session = sessionFactory.getCurrentSession();
        session.update(weatherData);
    }

    @Override
    public void delete(WeatherData weatherData) {
        session = sessionFactory.getCurrentSession();
        session.delete(weatherData);
    }
}
