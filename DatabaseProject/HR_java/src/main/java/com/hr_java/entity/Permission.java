package com.hr_java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-17
 */
@Getter
@Setter
@TableName("permission")
@ApiModel(value = "Permission对象", description = "权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("权限名称")
    @TableField("name")
    private String name;

    @TableField("pid")
    private Integer pid;

    @TableField("role")
    private String role;

    @TableField("time")
    private LocalDateTime time;


}
