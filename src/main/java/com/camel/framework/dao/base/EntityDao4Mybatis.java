package com.camel.framework.dao.base;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.camel.framework.page.Pagination;
import com.camel.framework.utils.Reflections;
import com.camel.framework.utils.Values;

public abstract class EntityDao4Mybatis<E, PK extends Serializable> extends MybatisSqlSessionDaoSupport implements EntityDao<E, PK> {

    private String namespace;

    public EntityDao4Mybatis() {
        Class<E> clazz = Reflections.getClassGenricType(getClass());
        String className = clazz.getName();
        int lastDot = className.lastIndexOf(".");
        namespace = Constants.MyBatis.NAMESPACE_PREFIX + "." + className.substring(lastDot + 1, className.length()) + ".";
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(PK pk) throws DataAccessException {
        return (E) getSqlSession().selectOne(namespace + Constants.MyBatis.GET_BY_ID, pk);
    }

    @Override
    public long count(Object parameter) throws DataAccessException {
        return Values.getLong(getSqlSession().selectOne(namespace + Constants.MyBatis.GET_COUNT, parameter));
    }

    @Override
    public List<E> search(Object parameter) throws DataAccessException {
        List<E> entities = getSqlSession().selectList(namespace + Constants.MyBatis.SEARCH, parameter);
        return null == entities ? Collections.<E>emptyList() : entities;
    }

    @Override
    public E searchOne(Object parameter) throws DataAccessException {
        return getSqlSession().selectOne(namespace + Constants.MyBatis.SEARCH, parameter);
    }

    @Override
    public Pagination<E> page(Object parameter) throws DataAccessException {
        Long totalCount = count(parameter);
        int pageNo = Values.getInteger(Reflections.getFieldValue(parameter, Constants.Page.PAGE_NO));
        int pageSize = Values.getInteger(Reflections.getFieldValue(parameter, Constants.Page.PAGE_SIZE));
        Pagination<E> page = new Pagination<E>(pageNo, pageSize, totalCount);

        if (totalCount > 0L) {
            Reflections.setFieldValue(parameter, Constants.Page.START, page.getFirstResult());
            Reflections.setFieldValue(parameter, Constants.Page.END, page.getMaxResult());
            page.setData(search(parameter));
        }

        return page;
    }

    @Override
    public int save(E entity) throws DataAccessException {
        return getSqlSession().insert(namespace + Constants.MyBatis.SAVE, entity);
    }

    @Override
    public int save(List<E> entities) throws DataAccessException {
        if (entities.size() > 1) {
            return getSqlSession().insert(namespace + Constants.MyBatis.SAVE_BATCH, entities);
        } else if (entities.size() > 0) {
            return save(entities.get(0));
        }
        return -1;
    }

    @Override
    public int update(E entity) throws DataAccessException {
        return getSqlSession().update(namespace + Constants.MyBatis.UPDATE, entity);
    }
    
    @Override
    public int updatePart(E entity) throws DataAccessException {
        return getSqlSession().update(namespace + Constants.MyBatis.UPDATE_PART, entity);
    }

    @Override
    public int delete(PK pk) throws DataAccessException {
        return getSqlSession().delete(namespace + Constants.MyBatis.DELETE_BY_PK, pk);
    }

    @Override
    public int delete(List<PK> pks) throws DataAccessException {
        if (pks.size() > 1) {
            return getSqlSession().delete(namespace + Constants.MyBatis.DELETE_BATCH_BY_PKS, pks);
        } else if (pks.size() > 0) {
            return delete(pks.get(0));
        }
        return -1;
    }

    public String getNamespace() {
        return namespace;
    }

}
