package com.cool.modules.app.service.impl;

import com.cool.core.base.BaseServiceImpl;
import com.cool.modules.app.entity.AppGoodsEntity;
import com.cool.modules.app.mapper.AppGoodsMapper;
import com.cool.modules.app.service.AppGoodsService;
import org.springframework.stereotype.Service;

/**
 * 套餐
 */
@Service
public class AppGoodsServiceImpl extends BaseServiceImpl<AppGoodsMapper, AppGoodsEntity> implements AppGoodsService {
}