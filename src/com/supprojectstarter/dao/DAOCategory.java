package com.supprojectstarter.dao;

import com.supprojectstarter.bean.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class DAOCategory extends DAO<Category> {
    private static final String JPQL_SELECT_BY_NAME = "SELECT c FROM Category c WHERE c.name=:name";
    private static final String PARAM_NAME = "name";
    private static DAOCategory instance = new DAOCategory();

    private DAOCategory() {
    }

    public static DAOCategory getInstance() {
        return instance;
    }

    @Override
    public int insert(Category obj) {
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
    public void update(Category obj) {
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
    public void delete(Category obj) {
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
    public void restore(Category obj) {
    }

    @Override
    public Category find(int id) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        return em.find(Category.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Category> findAll() {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT c FROM Category AS c").getResultList();
    }

    @Override
    public Category findByName(String string) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Query req = em.createQuery(JPQL_SELECT_BY_NAME);
        req.setParameter(PARAM_NAME, string);
        Category category;
        try {
            category = (Category) req.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return category;
    }
}
