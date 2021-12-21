package com.hr_java.Model.entity;

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
 * 状态表
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("status")
@ApiModel(value = "Status对象", description = "状态表")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("statusID")
    private Integer statusID;

    @TableField("msg")
    private String msg;

    @ApiModelProperty("默认当前系统时间")
    @TableField("time")
    private LocalDateTime time;


}
