package com.camel.framework.page;

import java.util.List;

import com.camel.framework.page.logic.ComplexLogic;
import com.camel.framework.page.logic.ConsecutiveLogic;
import com.camel.framework.page.logic.PageLogic;
import com.camel.framework.page.logic.SimpleLogic;

public class SimplePage implements Pageable {

    private final int     defaultPageSize = 10;
    private List<Element> elements;

    private Long        totalCount      = 0L;
    private Integer     pageSize        = 10;
    private Integer     pageNo          = 1;
    private Integer     step            = -1;

    private PageLogic     logic;
    private PageType      pageType;
    
    public SimplePage() {
    }

    public SimplePage(Integer pageNo, Integer pageSize, Long totalCount) {
        setTotalCount(totalCount);
        setPageSize(pageSize);
        setPageNo(pageNo);
        // must at here
        setPageType(PageType.SIMPLE);
        setStep(logic.getStep());
    }

    public SimplePage(Integer pageNo, Integer pageSize, Long totalCount, PageType pageType) {
        setTotalCount(totalCount);
        setPageSize(pageSize);
        setPageNo(pageNo);
        // must at here
        setPageType(pageType);
        setStep(logic.getStep());
    }

    public SimplePage(Integer pageNo, Integer pageSize, Long totalCount, Integer step, PageType pageType) {
        setTotalCount(totalCount);
        setPageSize(pageSize);
        setPageNo(pageNo);
        setStep(step);
        // must at here
        setPageType(pageType);
    }

    /* -------------------------------------------------------- */

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
        selector(pageType);
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount < 0L ? 0L : totalCount;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (null == pageSize || pageSize < 1) ? defaultPageSize : pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = (null == pageNo || pageNo < 1) ? 1 : pageNo;
        this.pageNo = this.pageNo > getTotalPage() ? getTotalPage() : this.pageNo;
    }

    private void setStep(Integer step) {
        this.step = step;
    }

    private void selector(final PageType pageType) {
        switch (pageType) {
            case COMPLEX:
                this.logic = new ComplexLogic(getTotalCount(), getTotalPage(), getPageSize(), getPageNo(), getStep());
                break;
            case CONSECUTIVE:
                this.logic = new ConsecutiveLogic(getTotalCount(), getTotalPage(), getPageSize(), getPageNo(),
                        getStep());
                break;
            case SIMPLE:
            default:
                this.logic = new SimpleLogic(getTotalCount(), getTotalPage(), getPageSize(), getPageNo(), getStep());
        }
    }

    /* -------------------------------------------------------- */

    @Override
    public Long getTotalCount() {
        return totalCount;
    }

    @Override
    public PageType getPageType() {
        return pageType;
    }

    @Override
    public Integer getTotalPage() {
        Long page = totalCount / pageSize;
        return Integer.valueOf(String.valueOf(totalCount % pageSize == 0 ? page : page + 1));
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    @Override
    public Integer getPageNo() {
        return pageNo;
    }

    @Override
    public Integer getStep() {
        return step;
    }

    @Override
    public Boolean hasPrev() {
        return pageNo > 1;
    }

    @Override
    public Boolean hasNext() {
        return getTotalPage() > getPageNo();
    }

    @Override
    public List<Element> getElements() {
        elements = logic.execute();
        return elements;
    }

}
