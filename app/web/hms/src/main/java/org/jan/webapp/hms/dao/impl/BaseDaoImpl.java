package org.jan.webapp.hms.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jan.webapp.hms.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * an implemention of {@link BaseDao}.
 *
 * @author Jan.Wang
 *
 * @param <T>
 */
@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {

    @Inject
    private SessionFactory sessionFactory;

    /*
     * Gets current session.
     */
    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    /*
     * Creates a query.
     */
    private Query createQuery(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (null != params && !params.isEmpty()) {
            for (String key : params.keySet())
                q.setParameter(key, params.get(key));
        }
        return q;
    }

    private Query createQuery(String hql, Object[] params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (null != params && params.length > 0) {
            for (int i = 0; i < params.length; i++)
                q.setParameter(i, params[i]);
        }
        return q;
    }

    private Query createQuery(String hql, List<Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (null != params && params.size() > 0) {
            for (int i = 0; i < params.size(); i++)
                q.setParameter(i, params.get(i));
        }
        return q;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#save(java.lang.Object)
     */
    @Override
    public Serializable save(T t) {
        return this.getCurrentSession().save(t);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#get(java.lang.Class, java.io.Serializable)
     */
    @Override
    public T get(Class<T> clazz, Serializable id) {
        return (T) this.getCurrentSession().get(clazz, id);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#get(java.lang.String)
     */
    @Override
    public T get(String hql) {
        List<T> l = find(hql);
        if(null != l && l.size() > 0)
            return l.get(0);
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#get(java.lang.String, java.util.Map)
     */
    @Override
    public T get(String hql, Map<String, Object> params) {
        List<T> l = find(hql, params);
        if (null != l && l.size() > 0)
            return l.get(0);
        return null;
    }

    @Override
    public T get(String hql, Object[] param) {
        List<T> l = this.find(hql, param);
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public T get(String hql, List<Object> param) {
        List<T> l = this.find(hql, param);
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#delete(java.lang.Object)
     */
    @Override
    public void delete(T t) {
        this.getCurrentSession().delete(t);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#update(java.lang.Object)
     */
    @Override
    public void update(T t) {
        this.getCurrentSession().update(t);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#saveOrUpdate(java.lang.Object)
     */
    @Override
    public void saveOrUpdate(T t) {
        this.getCurrentSession().saveOrUpdate(t);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#find(java.lang.String)
     */
    @Override
    public List<T> find(String hql) {
        return this.getCurrentSession().createQuery(hql).list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#find(java.lang.String, java.util.Map)
     */
    @Override
    public List<T> find(String hql, Map<String, Object> params) {
        return createQuery(hql, params).list();
    }

    @Override
    public List<T> find(String hql, Object[] params) {
        return createQuery(hql, params).list();
    }

    @Override
    public List<T> find(String hql, List<Object> params) {
        return createQuery(hql, params).list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#find(java.lang.String, java.util.Map, int, int)
     */
    @Override
    public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
        return createQuery(hql, params).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#find(java.lang.String, int, int)
     */
    @Override
    public List<T> find(String hql, int page, int rows) {
        return find(hql, null, page, rows);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#count(java.lang.String)
     */
    @Override
    public Long count(String hql) {
        return count(hql, null);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#count(java.lang.String, java.util.Map)
     */
    @Override
    public Long count(String hql, Map<String, Object> params) {
        return (Long) createQuery(hql, params).uniqueResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#executeHql(java.lang.String)
     */
    @Override
    public int executeHql(String hql) {
        return executeHql(hql, null);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jan.web.dao.BaseDao#executeHql(java.lang.String, java.util.Map)
     */
    @Override
    public int executeHql(String hql, Map<String, Object> params) {
        return createQuery(hql, params).executeUpdate();
    }

}
