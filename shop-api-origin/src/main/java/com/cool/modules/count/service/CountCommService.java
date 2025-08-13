package com.cool.modules.count.service;

import static com.mybatisflex.core.query.QueryMethods.left;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.FunctionQueryColumn;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CountCommService {
    /**
     * 获得时间范围
     * @param unit 时间单位 "day", "week", "month", "year"
     * @return 包含起始时间和结束时间的 Map
     */
    public Map<String, String> getTimeRange(String unit) {
        DateTime start, end;

        switch (unit) {
            case "week" -> {
                start = DateUtil.beginOfWeek(DateUtil.date());
                end = DateUtil.endOfWeek(DateUtil.date());
            }
            case "month" -> {
                start = DateUtil.beginOfMonth(DateUtil.date());
                end = DateUtil.endOfMonth(DateUtil.date());
            }
            case "year" -> {
                start = DateUtil.beginOfYear(DateUtil.date());
                end = DateUtil.endOfYear(DateUtil.date());
            }
            default -> { // "day"
                start = DateUtil.beginOfDay(DateUtil.date());
                end = DateUtil.endOfDay(DateUtil.date());
            }
        }
        Map<String, String> timeRange = new HashMap<>();
        timeRange.put("start", start.toString("yyyy-MM-dd HH:mm:ss"));
        timeRange.put("end", end.toString("yyyy-MM-dd HH:mm:ss"));
        return timeRange;
    }

    public List<String> getRecentlyDates(int recently) {
        List<String> dates = CollUtil.newArrayList();
        for (int i = 0; i < recently; i++) {
            DateTime date = DateUtil.offsetDay(DateUtil.date(), -i);
            dates.add(date.toString("YYYY-MM-dd"));
        }
        return CollUtil.reverse(dates); // 翻转列表，使得日期从过去到今天排序
    }


    /**
     * 图表
     */
    public Map<String, Object> chart(BaseMapper baseMapper, int dayCount, FunctionQueryColumn functionQueryColumn, QueryColumn createTime) {

        Map<String, Object> result = new HashMap<>();
        List<String> recentlyDates = getRecentlyDates(dayCount);
        List<Map> mapList = baseMapper.selectListByQueryAs(QueryWrapper.create()
            .select(functionQueryColumn.as("total"),
                left(createTime, 10).as("time"))
            .where(createTime.ge(DateUtil.offsetDay(DateUtil.date(), -dayCount)))
            .groupBy(left(createTime, 10)), Map.class);
        List<Object> datas = new ArrayList();
        List<String> dates = new ArrayList();
        for (String date : recentlyDates) {
            Object total = 0;
            for (Map map : mapList) {
                if (date.equals(map.get("time"))) {
                    total = map.get("total");
                    break;
                }
            }
            datas.add(total);
            dates.add(date);
        }
        result.put("datas", datas);
        result.put("dates", dates);
        return result;
    }
}
