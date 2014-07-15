package com.camel.framework.page;

import java.util.List;

public interface Pageable {

    PageType getPageType();

    Long getTotalCount();

    Integer getTotalPage();

    Integer getPageSize();

    Integer getPageNo();

    Integer getStep();
    
    Boolean hasNext();
    
    Boolean hasPrev();

    List<Element> getElements();

}
