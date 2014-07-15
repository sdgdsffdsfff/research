package com.camel.framework.page.logic;

import java.util.ArrayList;
import java.util.List;

import com.camel.framework.page.Element;
import com.camel.framework.page.ElementType;

public abstract class PageLogic {

    protected final Long    totalCount;
    protected final Integer totalPage;
    protected final Integer pageSize;
    protected final Integer pageNo;
    private int             step = 3;

    public PageLogic(Long totalCount, Integer totalPage, Integer pageSize, Integer pageNo) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public PageLogic(Long totalCount, Integer totalPage, Integer pageSize, Integer pageNo, Integer step) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.step = step > 0 ? step : this.step;
    }

    public abstract List<Element> previous();

    public abstract List<Element> next();

    /* ------------------------------------------------- */

    protected Element first() {
        return point(1);
    }

    protected Element last() {
        return point(totalPage);
    }

    protected Element one(int page) {
        return Element.create(page);
    }

    protected Element one(int page, boolean enable) {
        return Element.create(page, enable);
    }

    protected void sequence(List<Element> es, int start, int end) {
        for (int i = start; i < end; i++) {
            es.add(Element.create(i));
        }
    }

    protected void current(List<Element> es, int start, int end) {
        for (int i = start; i <= end; i++) {
            es.add(Element.create(i, i != pageNo));
        }
    }

    protected Element point(int point) {
        return Element.create(point, point != pageNo, point == 1 ? ElementType.FIRST : ElementType.LAST);
    }

    protected Element point(int point, ElementType elementType) {
        return Element.create(point, point != pageNo && point > 0, elementType);
    }

    protected void omit(List<Element> es) {
        es.add(Element.create(-1, false, ElementType.NONE));
    }

    /* ---------------------------------------------- */

    public List<Element> execute() {
        List<Element> elements = new ArrayList<Element>();

        Element f = first();
        if (null != f) {
            elements.add(f);
        }

        List<Element> p = previous();
        if (null != p && p.size() > 0) {
            elements.addAll(p);
        }

        List<Element> n = next();
        if (null != n && n.size() > 0) {
            elements.addAll(n);
        }

        Element l = last();
        if (null != l) {
            elements.add(l);
        }

        return elements;
    }

    // sub class can override this setting
    public Integer getStep() {
        return step;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

}
