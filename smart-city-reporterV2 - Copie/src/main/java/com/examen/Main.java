package com.examen;

import com.examen.signalement.repository.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {
    public static void testConnexion() {
        System.out.println("Tentative de connexion à la base de données...");

        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();

            System.out.println("Connexion réussie ! Session ouverte.");

            // On peut ajouter ici des opérations tests si besoin

            session.close();
            System.out.println("Session fermée.");

            HibernateUtil.shutdown();
        } catch (Exception e) {
            System.err.println("Échec de la connexion.");
            e.printStackTrace();
        }
    }
}
