package com.atguigu.edu.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月23日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChapterVO implements Serializable {
    private static final long serialVersionUID = 1123692976379366033L;

    private String id;

    /**
     * 课程ID
     */
    private String cId;

    private String title;

    private List<VideoVO> children;
}
