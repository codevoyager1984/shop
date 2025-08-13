package com.cool.modules.goods.service;

import com.cool.core.base.BaseService;
import com.cool.modules.goods.entity.GoodsSpecEntity;

/**
 * 规格
 */
public interface GoodsSpecService extends BaseService<GoodsSpecEntity> {

    /**
     * 更新库存
     */
    void updateStock(Long specId, Integer stock);
}
