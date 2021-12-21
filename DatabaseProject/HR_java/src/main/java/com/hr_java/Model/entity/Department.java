package com.hr_java.Model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 * 机构
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Data
@TableName("department")
@ApiModel(value = "Department对象", description = "机构")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "deptID", type = IdType.AUTO)
    private Integer deptID;

    @TableField("name")
    private String name;

    @TableField("parentId")
    private Integer parentId;

    @TableField(exist = false)
    private List<Department> departments;


}
