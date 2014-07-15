package com.camel.framework.dao.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.camel.framework.page.Pagination;

public interface EntityDao<E, PK extends Serializable> {

    E get(PK pk) throws DataAccessException;

    long count(Object query) throws DataAccessException;

    List<E> search(Object query) throws DataAccessException;

    Pagination<E> page(Object query) throws DataAccessException;

    int save(E entity) throws DataAccessException;

    int save(List<E> entities) throws DataAccessException;

    int update(E entity) throws DataAccessException;
    
    /**
     * 局部更新数据
     * @param entity 需更新的数据
     * @return int
     * @throws DataAccessException
     */
    int updatePart(E entity) throws DataAccessException;

    int delete(PK pk) throws DataAccessException;

    int delete(List<PK> pks) throws DataAccessException;

    E searchOne(Object parameter) throws DataAccessException;

}
