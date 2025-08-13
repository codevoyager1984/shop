package com.cool.modules.goods.service.impl;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.goods.entity.GoodsSpecEntity;
import com.cool.modules.goods.entity.table.GoodsSpecEntityTableDef;
import com.cool.modules.goods.mapper.GoodsSpecMapper;
import com.cool.modules.goods.service.GoodsSpecService;
import com.mybatisflex.core.update.UpdateChain;
import org.springframework.stereotype.Service;

/**
 * 规格
 */
@Service
public class GoodsSpecServiceImpl extends BaseServiceImpl<GoodsSpecMapper, GoodsSpecEntity> implements GoodsSpecService {

    @Override
    public void updateStock(Long specId, Integer stock) {
        String sql = GoodsSpecEntityTableDef.GOODS_SPEC_ENTITY.STOCK.getName();
        if (stock > 0) {
            // 加库存
            sql += " + " + stock;
        } else {
            // 减库存
            sql += stock;
        }
        UpdateChain.of(GoodsSpecEntity.class)
            .setRaw(GoodsSpecEntity::getStock, sql)
            .where(GoodsSpecEntity::getId).eq(specId)
            .update();
    }
}