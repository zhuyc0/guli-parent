package com.atguigu.guli.common.base.util;

import org.springframework.util.StringUtils;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
public class MyFileUtils {
    public static String getFileExtension(String originalFilename) {
        if (!StringUtils.hasText(originalFilename)){
            return "";
        }
        int i = originalFilename.lastIndexOf(".");
        if (i<0){
            return "";
        }
        return originalFilename.substring(i);
    }
}
