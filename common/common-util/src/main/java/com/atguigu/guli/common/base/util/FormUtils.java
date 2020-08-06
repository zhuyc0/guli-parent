package com.atguigu.guli.common.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月31日
 */
public class FormUtils {
    public static final Pattern p = Pattern.compile("^[1][3,45789][0-9]{9}$");
    /**
     * 手机号验证
     */
    public static boolean isMobile(String str) {
        // 验证手机号
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
