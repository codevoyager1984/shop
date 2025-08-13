package com.cool.modules.recycle.service;

import com.cool.core.base.BaseService;
import com.cool.modules.recycle.entity.RecycleDataEntity;
import java.util.List;

public interface RecycleDataService extends BaseService<RecycleDataEntity> {
    /**
     * 恢复数据
     * 
     * @param ids
     * @return
     */
    Boolean restore(List<Long> ids);
}
