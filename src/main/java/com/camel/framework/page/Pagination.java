package com.camel.framework.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pagination<T> extends SimplePage implements Serializable, Pageable {

    private static final long serialVersionUID = 1L;

    private List<T> data = new ArrayList<T>();
    
    public Pagination(Pagination<?> page) {
        super(page.getPageNo(), page.getPageSize(), page.getTotalCount());
    }

    public Pagination(Integer pageNo, Integer pageSize, Long totalCount) {
        super(pageNo, pageSize, totalCount, PageType.CONSECUTIVE);
    }

    public Pagination(Integer pageNo, Integer pageSize, Long totalCount, PageType pageType) {
        super(pageNo, pageSize, totalCount, pageType);
    }

    public Pagination(Integer pageNo, Integer pageSize, Long totalCount, Integer step, PageType pageType) {
        super(pageNo, pageSize, totalCount, step, pageType);
    }

    public Integer getFirstResult() {
        return (getPageNo() - 1) * getPageSize();
    }

    /**
     * at present for MyBatis&MySql's limit
     * <p>
     * if for Hibernate, you should change return value
     */
    public Integer getMaxResult() {
        return getPageSize();
        // for Hibernate
        // return pageNo * pageSize > totalCount ? totalCount : pageNo * pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
