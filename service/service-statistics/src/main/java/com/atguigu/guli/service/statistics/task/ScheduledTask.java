package com.atguigu.guli.service.statistics.task;

import com.atguigu.guli.service.statistics.service.DailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月11日
 */
@Slf4j
@Component
public class ScheduledTask {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private DailyService dailyService;

    /**
     * 每天凌晨1点执行定时任务
     * 注意只支持6位表达式
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void taskGenerateStatisticsData() {
        // 获取上一天的日期
        String day = DTF.format(LocalDate.now().plusDays(-1));
        dailyService.createStatisticsByDay(day);
        log.info("{}-taskGenerateStatisticsData 统计完毕", day);
    }
}
