package com.camel.framework.dao.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.camel.framework.dao.base.IBaseMongoDao;

/**
 * MongoDB的Dao层通用抽象实现类
 * @author Shuah
 * @param <T> PO对象
 * @param <R> 继承MongoRepository的子类
 * @date 2014年3月12日
 */
public abstract class BaseMongoDaoImpl<T, R extends MongoRepository<T, String>> implements IBaseMongoDao<T> {
    /**
     * 继承MongoRepository的子类
     */
    @Autowired
    private R repository;
    
    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    @Override
    public T get(String id) {
        return repository.findOne(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

//    @Override
//    public Page<T> findAll(Pageable pageable) {
//        return repository.findAll(pageable);
//    }

    @Override
    public long count() {
        return repository.count();
    }

    /**
     * 返回R对象
     * @return R
     */
    protected R getRepository() {
        return repository;
    }
}
