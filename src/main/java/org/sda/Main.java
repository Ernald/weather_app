package org.sda;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.sda.consullapp.WeatherDataGUI;
import org.sda.dao.LocationDaoImpl;

import org.sda.util.HibernateUtil;

import javax.swing.*;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WeatherDataGUI app = new WeatherDataGUI();
                app.display();
            }
        });


    }
}