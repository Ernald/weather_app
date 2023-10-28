package org.sda;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sda.dao.LocationDaoImpl;

import org.sda.util.HibernateUtil;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        //Comment

    }
}