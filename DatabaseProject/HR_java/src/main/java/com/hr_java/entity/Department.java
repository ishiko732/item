package com.hr_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 机构
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("department")
@ApiModel(value = "Department对象", description = "机构")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "deptID", type = IdType.AUTO)
    private Integer deptID;

    @TableField("parentId")
    private Integer parentId;

    @TableField("name")
    private String name;


}
