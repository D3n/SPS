package com.supprojectstarter.dao;

import com.supprojectstarter.bean.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class DAOUser extends DAO<User> {
    private static final String JPQL_SELECT_BY_NAME = "SELECT u FROM User u WHERE u.name=:name";
    private static final String PARAM_NAME = "name";
    private static final String JPQL_SELECT_BY_EMAIL = "SELECT u FROM User u WHERE u.mailAddress=:email";
    private static final String PARAM_EMAIL = "email";
    private static final String JPQL_SELECT_BY_GROUP = "SELECT u FROM User u WHERE u.group.label=:label";
    private static final String PARAM_LABEL = "label";
    private static DAOUser instance = new DAOUser();

    private DAOUser() {
    }

    public static DAOUser getInstance() {
        return instance;
    }

    @Override
    public int insert(User obj) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(obj);
            t.commit();
            return 1;
        } finally {
            if (t.isActive()) {
                t.rollback();
                em.close();
            }
        }
    }

    @Override
    public void update(User obj) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.merge(obj);
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
                em.close();
            }
        }
    }

    @Override
    public void delete(User obj) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            obj = em.merge(obj);
            em.remove(obj);
            t.commit();
        } finally {
            if (t.isActive()) {
                t.rollback();
                em.close();
            }
        }
    }

    @Override
    public void restore(User obj) {
    }

    @Override
    public User find(int id) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT c FROM User AS c").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllByGroup(String label) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Query req = em.createQuery(JPQL_SELECT_BY_GROUP);
        req.setParameter(PARAM_LABEL, label);
        List<User> listUser;
        try {
            listUser = req.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return listUser;
    }

    @Override
    public User findByName(String string) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Query req = em.createQuery(JPQL_SELECT_BY_NAME);
        req.setParameter(PARAM_NAME, string);
        User user;
        try {
            user = (User) req.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return user;
    }

    public User findByEmail(String string) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Query req = em.createQuery(JPQL_SELECT_BY_EMAIL);
        req.setParameter(PARAM_EMAIL, string);
        User user;
        try {
            user = (User) req.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return user;
    }
}