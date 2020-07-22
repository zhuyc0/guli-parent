package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.atguigu.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月22日
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    public static final DateTimeFormatter DT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Value("${spring.cloud.alicloud.oss.bucketName}")
    private String bucketName;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;

    @Resource
    private OSS ossClient;

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String dataPath = DT.format(LocalDate.now());
        String fileName = dataPath + "/" + uuid + fileSuffix(file.getOriginalFilename());
        try {
            ossClient.putObject(bucketName, fileName, file.getInputStream());
            return String.format("https://%s.%s/%s", bucketName, endpoint, fileName);
        } catch (IOException e) {
           log.error("上传到OSS失败,信息:{}", e.getMessage());
        }
        return null;
    }

    private String fileSuffix(String fileName) {
        if (StringUtils.hasText(fileName)) {
            int indexOf = fileName.lastIndexOf(".");
            if (indexOf > -1) {
                return fileName.substring(indexOf);
            }
        }
        return "";
    }
}
