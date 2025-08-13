package com.cool.modules.goods.service.impl;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.goods.entity.GoodsTypeEntity;
import com.cool.modules.goods.mapper.GoodsTypeMapper;
import com.cool.modules.goods.service.GoodsTypeService;
import org.springframework.stereotype.Service;

/**
 * 商品类型
 */
@Service
public class GoodsTypeServiceImpl extends BaseServiceImpl<GoodsTypeMapper, GoodsTypeEntity> implements GoodsTypeService {
}