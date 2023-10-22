package org.sda;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sda.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();


    }
}