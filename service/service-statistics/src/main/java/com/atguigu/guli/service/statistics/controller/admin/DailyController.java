package com.atguigu.guli.service.statistics.controller.admin;


import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.statistics.service.DailyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-08-11
 */
@Api(tags = "统计分析管理")
@RestController
@RequestMapping("/admin/statistics/daily")
public class DailyController {
    @Autowired
    private DailyService dailyService;

    @ApiOperation("生成统计记录")
    @ApiImplicitParam(name = "day", value = "统计日期", example = "2020-08-11")
    @PostMapping("create/{day}")
    public R createStatisticsByDay(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return R.ok().message("数据统计生成成功");
    }

    @ApiOperation("显示统计数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "begin", value = "统计日期", example = "2020-08-11"),
            @ApiImplicitParam(name = "end", value = "统计日期", example = "2020-08-11"),
    })
    @GetMapping("show-chart/{begin}/{end}")
    public R showChart(@PathVariable String begin, @PathVariable String end) {
        Map<String, Map<String, Object>> map = dailyService.getChartData(begin, end);
        return R.ok().data("chartData", map);
    }
}

