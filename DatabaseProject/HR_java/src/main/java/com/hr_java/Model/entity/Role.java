package com.hr_java.Model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色权限
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-17
 */
@Data
@TableName("role")
@ApiModel(value = "Role对象", description = "角色权限")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("rid")
    private Integer rid;

    @TableField("name")
    private String name;

    @TableField("author")
    private Integer author;

    @TableField(exist = false)
    private Set<String> permissions;


}
