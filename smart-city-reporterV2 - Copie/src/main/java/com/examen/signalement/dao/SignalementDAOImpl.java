package com.examen.signalement.dao;

import com.examen.signalement.exception.DataAccessException;
import com.examen.signalement.model.Signalement;
import com.examen.signalement.repository.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SignalementDAOImpl implements SignalementDAO {

    @Override
    public Signalement save(Signalement entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Fix: Attach the user if only ID is provided (TransientObjectException
            // prevention)
            if (entity.getUtilisateur() != null && entity.getUtilisateur().getId() != null) {
                try {
                    // Load the proxy for the existing user
                    // We use load() because we assume the user exists and we only need the
                    // reference
                    com.examen.signalement.model.Utilisateur userProxy = session
                            .load(com.examen.signalement.model.Utilisateur.class, entity.getUtilisateur().getId());
                    entity.setUtilisateur(userProxy);
                } catch (Exception e) {
                    // If user doesn't exist, this might fail, but let's proceed or log
                    System.err
                            .println("Warning: Could not load Utilisateur with ID " + entity.getUtilisateur().getId());
                }
            }

            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new DataAccessException("Error saving Signalement", e);
        }
    }

    @Override
    public Signalement findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Signalement.class, id);
        } catch (Exception e) {
            throw new DataAccessException("Error finding Signalement by ID: " + id, e);
        }
    }

    @Override
    public List<Signalement> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL to fetch all
            return session.createQuery("from Signalement", Signalement.class).list();
        } catch (Exception e) {
            throw new DataAccessException("Error finding all Signalements", e);
        }
    }

    @Override
    public void update(Signalement entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new DataAccessException("Error updating Signalement", e);
        }
    }

    @Override
    public void delete(Signalement entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new DataAccessException("Error deleting Signalement", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Signalement entity = session.get(Signalement.class, id);
            if (entity != null) {
                session.delete(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new DataAccessException("Error deleting Signalement by ID: " + id, e);
        }
    }
}
