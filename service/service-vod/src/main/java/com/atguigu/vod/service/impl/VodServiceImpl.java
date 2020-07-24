package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.base.exce.GuliException;
import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.util.ConstantVod;
import com.atguigu.vod.util.InitVodClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月24日
 */
@Service
public class VodServiceImpl implements VodService {

    @Override
    public R uploadVideoAly(MultipartFile file) {
        //accessKeyId, accessKeySecret
        //fileName：上传文件原始名称
        // 01.03.09.mp4
        String fileName = file.getOriginalFilename();
        //title：上传之后显示名称
        String title = UUID.randomUUID().toString().replaceAll("-", "");
        if(StringUtils.hasText(fileName)){
            title = fileName.substring(0, fileName.lastIndexOf("."));
        }

        UploadStreamRequest request;

        try {
            request = new UploadStreamRequest(ConstantVod.KEY, ConstantVod.SECRET, title, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return R.error().message("文件读取失败");
        }

        UploadVideoImpl uploader = new UploadVideoImpl();

        UploadStreamResponse response;
        try{
            response = uploader.uploadStream(request);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("上传文件失败");
        }
        String videoId;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
            return R.ok().data("videoId",videoId);
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            videoId = response.getVideoId();
            if (StringUtils.hasText(videoId)){
                return R.ok().data("videoId",videoId);
            }else {
                return R.error().message(String.format("code:%s,msg:%s", response.getCode(),response.getMessage()));
            }
        }
    }

    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVod.KEY, ConstantVod.SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //videoIdList值转换成 1,2,3
            String videoIds = StringUtils.arrayToDelimitedString(videoIdList.toArray(), ",");
            //向request设置视频id
            request.setVideoIds(videoIds);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
}
