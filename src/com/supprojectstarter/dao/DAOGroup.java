package com.supprojectstarter.dao;

import com.supprojectstarter.bean.Group;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class DAOGroup extends DAO<Group> {
    private static final String JPQL_SELECT_BY_NAME = "SELECT g FROM Group g WHERE g.label=:name";
    private static final String PARAM_NAME = "name";
    private static DAOGroup instance = new DAOGroup();

    private DAOGroup() {
    }

    public static DAOGroup getInstance() {
        return instance;
    }

    @Override
    public int insert(Group obj) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        EntityTransaction t = em.getTransaction();
        int id = -1;
        try {
            t.begin();
            em.persist(obj);
            em.flush();
            id = obj.getId();
            t.commit();
            return id;
        } finally {
            if (t.isActive()) {
                t.rollback();
                em.close();
            }
        }
    }

    @Override
    public void delete(Group obj) {
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
    public void restore(Group obj) {
    }

    @Override
    public Group find(int id) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        return em.find(Group.class, id);
    }

    @Override
    public void update(Group obj) {
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Group> findAll() {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT g FROM Group g").getResultList();
    }

    @Override
    public Group findByName(String string) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Query req = em.createQuery(JPQL_SELECT_BY_NAME);
        req.setParameter(PARAM_NAME, string);
        Group group;
        try {
            group = (Group) req.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return group;
    }
}
