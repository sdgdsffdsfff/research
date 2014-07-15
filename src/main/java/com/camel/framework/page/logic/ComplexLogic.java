package com.camel.framework.page.logic;

import java.util.ArrayList;
import java.util.List;

import com.camel.framework.page.Element;
import com.camel.framework.page.ElementType;

public final class ComplexLogic extends PageLogic {

    public ComplexLogic(Long totalCount, Integer totalPage, Integer pageSize, Integer pageNo, Integer step) {
        super(totalCount, totalPage, pageSize, pageNo, step);
    }

    @Override
    protected Element first() {
        return pageNo > 1 ? point(pageNo - 1, ElementType.FIRST) : point(-1, ElementType.FIRST);
    }

    @Override
    public List<Element> previous() {
        List<Element> es = new ArrayList<Element>();

        if (pageNo < 2 * getStep()) {
            current(es, 1, pageNo < getStep() ? getStep() : pageNo + 1);
            omit(es);
        } else {
            sequence(es, 1, getStep());
            omit(es);
            if (pageNo <= totalPage - (2 * getStep() - 1)) {
                current(es, pageNo - getStep() / 2, pageNo + getStep() / 2);
                omit(es);
            }
        }

        return es;
    }

    @Override
    public List<Element> next() {
        List<Element> es = new ArrayList<Element>();

        if (pageNo <= totalPage - (2 * getStep() - 1)) {
            sequence(es, totalPage - getStep() / 2, totalPage + 1);
        } else if (pageNo >= totalPage - (getStep() / 2)) {
            current(es, totalPage - (getStep() - 1), totalPage);
        } else {
            current(es, pageNo - 1, totalPage);
        }

        return es;
    }

    @Override
    protected Element last() {
        return pageNo < totalPage ? point(pageNo + 1, ElementType.LAST) : point(-1, ElementType.LAST);
    }

}
