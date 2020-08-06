package com.atguigu.guli.service.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
@Configuration
public class OssConfig {

	@Autowired
	private OSS ossClient;

	@Autowired
	private OssProperties properties;

    /**
     * You can use these code to check or create oss bucket. Or manage buckets in
     * <a href="https://oss.console.aliyun.com/bucket">oss console</a>.
     */

	@Bean
	public ApplicationRunner ossBucketInitRunner() {
		return args -> {
			if (!ossClient.doesBucketExist(properties.getBucketName())) {
				CreateBucketRequest request = new CreateBucketRequest(properties.getBucketName());
				request.setCannedACL(CannedAccessControlList.PublicRead);
				ossClient.createBucket(request);
			}
		};
	}
}
