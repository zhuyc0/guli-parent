package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月22日
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
