package com.cool.modules.count.service;

import static com.cool.modules.user.entity.table.UserInfoEntityTableDef.USER_INFO_ENTITY;
import static com.mybatisflex.core.query.QueryMethods.count;

import com.cool.modules.user.entity.UserInfoEntity;
import com.cool.modules.user.mapper.UserInfoMapper;
import com.mybatisflex.core.query.QueryWrapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountUserService {

    private final UserInfoMapper userInfoMapper;

    private final CountCommService countCommService;
    /**
     * 概况
     */
    public Map<String, Long> summary() {
        Map<String, Long> map = new HashMap<>();
        map.put("today", getUserCount(countCommService.getTimeRange("day")));
        map.put("week", getUserCount(countCommService.getTimeRange("week")));
        map.put("month", getUserCount(countCommService.getTimeRange("month")));
        map.put("year", getUserCount(countCommService.getTimeRange("year")));
        map.put("total", getUserCount(new HashMap<>()));
        return map;
    }

    private long getUserCount(Map<String, String> timeRange) {
        return userInfoMapper.selectCountByQuery(QueryWrapper.create()
            .ge(UserInfoEntity::getCreateTime, timeRange.get("start"))
            .le(UserInfoEntity::getCreateTime, timeRange.get("end")));
    }


    /**
     * 图表
     * @param dayCount 天数
     * @return 结果 Map，包含 datas 和 dates
     */
    public Map<String, Object> chart(int dayCount) {
        return countCommService.chart(userInfoMapper, dayCount, count(USER_INFO_ENTITY.ID), USER_INFO_ENTITY.CREATE_TIME);
    }
}
