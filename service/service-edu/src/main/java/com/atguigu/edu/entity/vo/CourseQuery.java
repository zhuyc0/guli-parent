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
@EqualsAndHashCode
@Accessors(chain = true)
public class CourseQuery implements Serializable {
    private static final long serialVersionUID = -4926234044886820320L;

    private String title;
    private String status;
    private Long page = 1L;
    private Long limit = 10L;
}
