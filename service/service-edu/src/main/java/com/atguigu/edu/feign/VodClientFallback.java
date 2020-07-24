package com.atguigu.edu.feign;

import com.atguigu.commonutils.R;
import com.atguigu.edu.feign.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月24日
 */
@Component
public class VodClientFallback implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
