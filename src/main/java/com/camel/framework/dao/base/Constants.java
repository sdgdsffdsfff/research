package com.camel.framework.dao.base;

public interface Constants {

    interface MyBatis {
        String NAMESPACE_PREFIX    = "com.camel.mybatis";
        String GET                 = "get";
        String GET_COUNT           = "getCount";
        String GET_MAX_ID          = "getMaxId";
        String GET_BY_ID           = "getById";
        String GET_BY_ALIAS        = "getByAlias";
        String GET_BY_ENTITY       = "getByEntity";
        String SEARCH              = "search";
        String SAVE                = "save";
        String SAVE_BATCH          = "saveBatch";
        String UPDATE              = "update";
        String UPDATE_PART         = "updatePart";
        String DELETE_BY_PK        = "deleteByPk";
        String DELETE_BATCH_BY_PKS = "deleteBatchByPks";
        String GET_PREVIOUS        = "getPrevious";
        String GET_NEXT            = "getNext";

    }

    interface Page {
        String  PAGE_NO           = "pageNo";
        String  PAGE_SIZE         = "pageSize";
        String  START             = "start";
        String  END               = "end";
        Integer DEFAULT_PAGE_SIZE = 10;

    }

    interface Encoding {
        String UTF8 = "UTF-8";
    }

}
