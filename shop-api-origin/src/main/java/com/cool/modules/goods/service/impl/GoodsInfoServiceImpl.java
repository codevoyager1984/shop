package com.cool.modules.goods.service.impl;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.goods.entity.GoodsInfoEntity;
import com.cool.modules.goods.mapper.GoodsInfoMapper;
import com.cool.modules.goods.service.GoodsInfoService;
import org.springframework.stereotype.Service;

/**
 * 商品信息
 */
@Service
public class GoodsInfoServiceImpl extends BaseServiceImpl<GoodsInfoMapper, GoodsInfoEntity> implements GoodsInfoService {
}