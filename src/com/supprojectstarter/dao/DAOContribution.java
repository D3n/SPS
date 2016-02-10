package com.supprojectstarter.dao;

import com.supprojectstarter.bean.Contribution;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class DAOContribution extends DAO<Contribution> {
    private static final String JPQL_SELECT_BY_USER = "SELECT c FROM Contribution c WHERE c.contributorUser.mailAddress=:mailAddress";
    private static final String PARAM_USER = "mailAddress";
    private static final String JPQL_SELECT_BY_PROJECT = "SELECT c FROM Contribution c WHERE c.project.id=:idProject";
    private static final String PARAM_PROJECT = "idProject";
    private static DAOContribution instance = new DAOContribution();

    private DAOContribution() {
    }

    public static DAOContribution getInstance() {
        return instance;
    }

    @Override
    public int insert(Contribution obj) {
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
    public void update(Contribution obj) {
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
    public void delete(Contribution obj) {
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
    public void restore(Contribution obj) {
        // TODO Auto-generated method stub
    }

    @Override
    public Contribution find(int id) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        return em.find(Contribution.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Contribution> findAll() {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT c FROM Contribution AS c").getResultList();
    }

    // @Override
    /*
    public Contribution findByName(String string) {
		Query req = getEntityManager().createQuery( JPQL_SELECT_BY_NAME );
		req.setParameter( PARAM_NAME , string );
		Contribution contrib = null ;
		
		try {
            contrib = (Contribution) req.getSingleResult();
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return contrib ;
	} */
    @SuppressWarnings("unchecked")
    public List<Contribution> findAllByProject(int idProject) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Query req = em.createQuery(JPQL_SELECT_BY_PROJECT);
        req.setParameter(PARAM_PROJECT, idProject);
        List<Contribution> contributions;
        try {
            contributions = req.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return contributions;
    }

    @SuppressWarnings("unchecked")
    public List<Contribution> findAllByUser(String mailAddress) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Query req = em.createQuery(JPQL_SELECT_BY_USER);
        req.setParameter(PARAM_USER, mailAddress);
        List<Contribution> contributions;
        try {
            contributions = req.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return contributions;
    }

    @Override
    public Contribution findByName(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}
