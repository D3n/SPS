package com.supprojectstarter.dao;

import com.supprojectstarter.bean.Project;
import org.joda.time.DateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

public class DAOProject extends DAO<Project> {
    private static final String JPQL_SELECT_BY_NAME = "SELECT p FROM Project p WHERE p.name=:name";
    private static final String PARAM_NAME = "name";
    private static final String JPQL_SELECT_BY_CATEGORY = "SELECT p FROM Project p WHERE p.category.id=:idCateg";
    private static final String PARAM_CATEGORY = "idCateg";
    private static final String JPQL_SELECT_BY_USER = "SELECT p FROM Project p WHERE p.creatorUser.mailAddress =:mailAddress";
    private static final String PARAM_USER = "mailAddress";
    private static DAOProject instance = new DAOProject();

    private DAOProject() {
    }

    public static DAOProject getInstance() {
        return instance;
    }

    @Override
    public int insert(Project obj) {
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
    public void update(Project obj) {
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
    public void delete(Project obj) {
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
    public void restore(Project obj) {
    }

    @Override
    public Project find(int id) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        return em.find(Project.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Project> findAll() {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT p FROM Project p").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Project> findForHome() {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT p FROM Project p ORDER BY p.id ASC").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Project> findAllProjectsStartedAndNotEnded() {
        DateTime now = new DateTime();
        List<Project> projects = DAOProject.getInstance().findAll();
        Iterator<Project> iterator = projects.iterator();
        while (iterator.hasNext()) {
            Project p = iterator.next();
            if (p.StartDateTime().isAfter(now) || p.EndDate().isBefore(now)) {
                iterator.remove();
            }
        }
        return projects;
    }

    @Override
    public Project findByName(String string) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Query req = em.createQuery(JPQL_SELECT_BY_NAME);
        req.setParameter(PARAM_NAME, string);
        Project project;
        try {
            project = (Project) req.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return project;
    }

    @SuppressWarnings("unchecked")
    public List<Project> findAllByCategory(int idCateg) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Query req = em.createQuery(JPQL_SELECT_BY_CATEGORY);
        req.setParameter(PARAM_CATEGORY, idCateg);
        List<Project> projects;
        try {
            projects = req.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return projects;
    }

    @SuppressWarnings("unchecked")
    public List<Project> findAllByUser(String mailAddress) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Query req = em.createQuery(JPQL_SELECT_BY_USER);
        req.setParameter(PARAM_USER, mailAddress);
        List<Project> projects;
        try {
            projects = req.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return projects;
    }
}
