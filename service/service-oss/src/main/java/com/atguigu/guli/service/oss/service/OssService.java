package com.atguigu.guli.service.oss.service;

import java.io.InputStream;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
public interface OssService {

    String upload(InputStream inputStream, String module, String originalFilename);

    void removeFile(String url);
}
