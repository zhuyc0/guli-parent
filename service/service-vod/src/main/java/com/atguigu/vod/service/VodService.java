package com.atguigu.vod.service;

import com.atguigu.commonutils.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月24日
 */
public interface VodService {
    //上传视频到阿里云
    R uploadVideoAly(MultipartFile file);

    //删除多个阿里云视频的方法
    void removeMoreAlyVideo(List<String> videoIdList);
}
