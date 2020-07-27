package com.atguigu.edu.controller;


import com.atguigu.base.exce.GuliException;
import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.VideoEntity;
import com.atguigu.edu.feign.client.VodClient;
import com.atguigu.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zhuyc
 * @since 2020-07-23
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;


    @PostMapping("addVideo")
    public R addVideo(@RequestBody VideoEntity eduVideo) {
        //添加小节
        videoService.save(eduVideo);
        return R.ok();
    }


    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //删除小节，删除对应阿里云视频
        //根据小节id获取视频id，调用方法实现视频删除
        VideoEntity eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节里面是否有视频id
        if(StringUtils.hasText(videoSourceId)) {
            //根据视频id，远程调用实现视频删除
            R result = vodClient.removeAlyVideo(videoSourceId);
//            if(((Integer)20001).equals(result.getCode())) {
            if(!Objects.equals(20000,result.getCode())) {
                throw new GuliException(20001,"删除视频失败，熔断器...");
            }
        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }
}

