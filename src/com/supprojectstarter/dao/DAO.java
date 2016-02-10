package com.supprojectstarter.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract class DAO<T> {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("My-PU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public abstract int insert(T obj);

    public abstract void update(T obj);

    public abstract void delete(T obj);

    public abstract void restore(T obj);

    public abstract T find(int id);

    public abstract T findByName(String string);

    public abstract List<T> findAll();
}
