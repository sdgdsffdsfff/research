package com.camel.framework.dao.base;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

public class MybatisSqlSessionDaoSupport extends SqlSessionDaoSupport {

    @Resource(name = "sqlSessionFactory")
    public void setSuperSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        Assert.notNull(sqlSessionFactory, "sqlSessionFactory must be not null");
        super.setSqlSessionFactory(sqlSessionFactory);
    }

}
