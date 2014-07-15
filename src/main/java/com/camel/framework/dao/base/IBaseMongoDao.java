package com.camel.framework.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.camel.framework.page.Pageable;

/**
 * MongoDB的Dao层通用接口
 * @author Shuah
 * @param <T> PO对象
 * @date 2014年3月12日
 */
public interface IBaseMongoDao<T> {
    
    /**
     * 保存数据
     * @param t 需保存的数据(若ID值未指定则默认生成)
     * @return 保存后的数据(包括ID值)
     */
    T save(T t);
    
    /**
     * 根据ID删除数据
     * @param id 主键ID
     */
    void delete(String id);
    
    /**
     * 根据ID获取数据
     * @param id 主键ID
     * @return T
     */
    T get(String id);
    
    /**
     * 查询所有数据
     * @return List<T>
     */
    List<T> findAll();
    
    /**
     * 查询所有数据并对数据进行排序 (若其他方法需进行查询排序，可在方法参数中增加Sort参数即可)
     * @param sort 排序规则对象
     * @return List<T>
     */
    List<T> findAll(Sort sort);
    
    /**
     * 查询所有数据并对数据进行分页查询(若其他方法需进行分页查询，可在方法参数中增加Pageable参数即可)
     * @param pageable 分页规则对象
     * @return Page<T>
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 获取数据总数
     * @return long
     */
    long count();

}
