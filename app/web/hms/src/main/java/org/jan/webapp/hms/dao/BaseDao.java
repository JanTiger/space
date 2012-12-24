package org.jan.webapp.hms.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Base dao for hibernate.
 *
 * @author Jan.Wang
 *
 * @param <T>
 */
public interface BaseDao<T> {

    /**
     * Saves the object.
     *
     * @param t
     * @return id
     */
    public Serializable save(T t);

    /**
     * Delets the object.
     *
     * @param t
     */
    public void delete(T t);

    /**
     * Updates the object.
     *
     * @param t
     */
    public void update(T t);

    /**
     * Saves or updates the object.
     *
     * @param t
     */
    public void saveOrUpdate(T t);

    /**
     * Gets the object by the specified id.
     *
     * @param clazz
     * @param id
     * @return a instance of the specified clazz.
     */
    public T get(Class<T> clazz, Serializable id);

    /**
     * Gets the object by hql.
     *
     * @param hql
     * @return an object
     */
    public T get(String hql);

    /**
     * Gets the object by hql with parameters.
     *
     * @param hql
     * @param params
     * @return an object
     */
    public T get(String hql, Map<String, Object> params);

    /**
     * Gets the list of objects by hql.
     *
     * @param hql
     * @return a list of objects
     */
    public List<T> find(String hql);

    /**
     * Gets the list of objects by hql with parameters.
     *
     * @param hql
     * @param params
     * @return a list of objects
     */
    public List<T> find(String hql, Map<String, Object> params);

    /**
     * Gets the list of objects by hql in the page.
     *
     * @param hql
     * @param page
     * @param rows
     * @return a list of objects
     */
    public List<T> find(String hql, int page, int rows);

    /**
     * Gets the list of objects by hql in the page with parameters.
     *
     * @param hql
     * @param params
     * @param page
     * @param rows
     * @return a list of objects
     */
    public List<T> find(String hql, Map<String, Object> params, int page, int rows);

    /**
     * Counts number.
     *
     * @param hql
     * @return {@link Long}
     */
    public Long count(String hql);

    /**
     * Counts number with parameters.
     *
     * @param hql
     * @param params
     * @return {@link Long}
     */
    public Long count(String hql, Map<String, Object> params);

    /**
     * Executes the specified hql.
     *
     * @param hql
     * @return result for excuting
     */
    public int executeHql(String hql);

    /**
     * Executes the specified hql with parameters.
     *
     * @param hql
     * @param params
     * @return result for excuting
     */
    public int executeHql(String hql, Map<String, Object> params);

}
