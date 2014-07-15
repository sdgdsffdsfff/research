package com.camel.framework.page.logic;

import java.util.ArrayList;
import java.util.List;

import com.camel.framework.page.Element;
import com.camel.framework.page.ElementType;

public final class ConsecutiveLogic extends PageLogic {
    
    private int mystep = 10;

    public ConsecutiveLogic(Long totalCount, Integer totalPage, Integer pageSize, Integer pageNo) {
        super(totalCount, totalPage, pageSize, pageNo);
    }
    
    public ConsecutiveLogic(Long totalCount, Integer totalPage, Integer pageSize, Integer pageNo, Integer step) {
        super(totalCount, totalPage, pageSize, pageNo, step);
    }

    private int rate() {
        return getStep()/2 - 2;
    }
    
    @Override
    public Integer getStep() {
        int i = super.getStep() > mystep ? super.getStep() : mystep;
        return i > totalPage ? totalPage : i; 
    }

    @Override
    protected Element first() {
        return pageNo > 1 ? point(pageNo - 1, ElementType.FIRST) : null;
    }

    @Override
    public List<Element> previous() {
        List<Element> es = new ArrayList<Element>();
        
        if (getStep() < totalPage) {
            if (pageNo <= rate() + 1) {
                current(es, 1, getStep());
                es.add(one(totalPage));
            } else if (pageNo <= totalPage - (getStep() - rate())) {
                es.add(one(1));
                current(es, pageNo - rate(), (pageNo - rate()) + getStep() - 1);
                es.add(one(totalPage));
            } else if (pageNo > totalPage - (getStep() - rate())) {
                es.add(one(1));
                current(es, pageNo - rate(), totalPage);
            }
        } else {
            current(es, 1, totalPage);
        }
        
        return es;
    }

    @Override
    public List<Element> next() {
        return null;
    }
    
    @Override
    protected Element last() {
        return pageNo < totalPage ? point(pageNo + 1, ElementType.LAST) : null;
    }
}
