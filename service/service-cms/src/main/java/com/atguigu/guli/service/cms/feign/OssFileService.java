package com.atguigu.guli.service.cms.feign;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.service.cms.feign.fallback.OssFileServiceFallBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月03日
 */
@Service
@FeignClient(value = "service-oss", fallback = OssFileServiceFallBackImpl.class)
public interface OssFileService {
    @DeleteMapping("/admin/oss/file/remove")
    R removeFile(@RequestBody String url);
}
