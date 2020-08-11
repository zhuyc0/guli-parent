package com.atguigu.guli.service.statistics.service;

import com.atguigu.guli.service.statistics.entity.DailyEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-11
 */
public interface DailyService extends IService<DailyEntity> {
    void createStatisticsByDay(String day);

    Map<String, Map<String, Object>> getChartData(String begin, String end);
}
