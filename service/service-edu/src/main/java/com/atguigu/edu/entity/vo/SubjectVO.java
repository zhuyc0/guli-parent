package com.atguigu.edu.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月22日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SubjectVO implements Serializable {
    private static final long serialVersionUID = -7403182796153747470L;

    private String id;
    private String pId;
    private String title;
    private List<SubjectVO> children;
}
