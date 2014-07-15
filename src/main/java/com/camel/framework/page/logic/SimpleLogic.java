package com.camel.framework.page.logic;

import java.util.ArrayList;
import java.util.List;

import com.camel.framework.page.Element;

public final class SimpleLogic extends PageLogic {

    public SimpleLogic(Long totalCount, Integer totalPage, Integer pageSize, Integer pageNo) {
        super(totalCount, totalPage, pageSize, pageNo);
    }

    public SimpleLogic(Long totalCount, Integer totalPage, Integer pageSize, Integer pageNo, Integer step) {
        super(totalCount, totalPage, pageSize, pageNo, step);
    }

    @Override
    public List<Element> previous() {
        List<Element> es = new ArrayList<Element>(1);

        boolean b = pageNo > 1;
        es.add(one(b ? pageNo - 1 : pageNo, b));

        return es;
    }

    @Override
    public List<Element> next() {
        List<Element> es = new ArrayList<Element>(1);

        boolean b = pageNo < totalPage;
        es.add(one(b ? pageNo + 1 : pageNo, b));

        return es;
    }

}
