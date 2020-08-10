package com.atguigu.guli.service.trade.feign;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.base.dto.CourseDto;
import com.atguigu.guli.service.trade.feign.fallback.EduCourseServiceFallBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月10日
 */
@Service
@FeignClient(value = "service-edu", fallback = EduCourseServiceFallBackImpl.class)
public interface EduCourseService {

    @GetMapping(value = "/inner/get-course-dto/{courseId}")
    CourseDto getCourseDtoById(@PathVariable(value = "courseId") String courseId);

    @GetMapping("/api/edu/course/inner/update-buy-count/{id}")
    R updateBuyCountById(@PathVariable("id") String id);
}
