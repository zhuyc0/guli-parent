package com.atguigu.edu.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月23日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VideoVO implements Serializable {
    private static final long serialVersionUID = 7404750468497538940L;

    private String id;

    /**
     * 课程ID
     */
    private String cId;

    /**
     * 章节ID
     */
    private String cpId;

    private String title;
}
