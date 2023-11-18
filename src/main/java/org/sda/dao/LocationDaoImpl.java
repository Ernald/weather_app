package org.sda.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.sda.model.Location;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Queue;
import java.util.UUID;



public class LocationDaoImpl implements LocationDao{
    private SessionFactory sessionFactory;
    public LocationDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    @Override
    public Location findById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Location.class, id);
    }

    @Override
    public List<Location> findAll() {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery <Location> query = builder.createQuery(Location.class);
            Root<Location> root = query.from(Location.class);
            query.select(root);

            Query<Location> q = session.createQuery(query);
            return q.getResultList();
        }
        /*try (Session session = sessionFactory.openSession()) {
    String hql = "FROM Location"; // Define the HQL query
    Query<Location> query = session.createQuery(hql, Location.class); // Create a query object
    return query.list(); // Execute the query and return the results
    }*/
    }

    @Override
    public void save(Location location) {
        Session session = sessionFactory.getCurrentSession();
        session.save(location);
    }

    @Override
    public void update(Location location) {
        Session session = sessionFactory.getCurrentSession();
        session.update(location);
    }

    @Override
    public void delete(Location location) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(location);
    }
}
