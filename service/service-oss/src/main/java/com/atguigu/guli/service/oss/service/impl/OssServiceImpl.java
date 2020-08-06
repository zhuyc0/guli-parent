package com.atguigu.guli.service.oss.service.impl;

import com.aliyun.oss.OSS;
import com.atguigu.guli.common.base.util.MyFileUtils;
import com.atguigu.guli.service.oss.config.OssProperties;
import com.atguigu.guli.service.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Resource
    private OSS ossClient;

    @Autowired
    private OssProperties properties;

    @Override
    public String upload(InputStream inputStream, String module, String originalFilename) {
        //构建objectName：文件路径 avatar/2020/04/15/default.jpg

        String folder = DTF.format(LocalDate.now());
        String fileName = UUID.randomUUID().toString();
        String fileExtension = MyFileUtils.getFileExtension(originalFilename);
        String key = module +"/" + folder + "/" + fileName + fileExtension;

        String endpoint = properties.getEndpoint();
        String bucketName = properties.getBucketName();
        // 上传文件流。
        ossClient.putObject(bucketName, key, inputStream);

        return "https://" + bucketName + "." + endpoint + "/" + key;
    }

    @Override
    public void removeFile(String url) {
        //读取配置信息
        String endpoint = properties.getEndpoint();
        String bucketName = properties.getBucketName();

        // 删除文件。
        //https://guli-file-191125.oss-cn-beijing.aliyuncs.com/
        String host = "https://" + bucketName + "." + endpoint + "/";
        String objectName = url.substring(host.length());
        ossClient.deleteObject(bucketName, objectName);
    }
}
