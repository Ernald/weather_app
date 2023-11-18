package org.sda.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.sda.dao.LocationDao;
import org.sda.model.Location;

import java.util.List;
import java.util.UUID;

import static org.sda.util.HibernateUtil.sessionFactory;

public class LocationServiceImpl implements LocationService{
    private LocationDao locationDao;
    public LocationServiceImpl(LocationDao locationDao){
        this.locationDao = locationDao;
    }
    @Override
    public Location getLocationById(UUID id) {
        return locationDao.findById(id);
    }

    @Override
    public List<Location> getAllLocation() {
        return locationDao.findAll();
    }

    @Override
    public void addLocation(Location location) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(location);
            transaction.commit();
        }catch(Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
        }finally {
            session.close();
        }

    }

    @Override
    public void updateLocation(Location location) {
        locationDao.update(location);
    }

    @Override
    public void deleteLocation(Location location) {
        locationDao.delete(location);
    }
}
