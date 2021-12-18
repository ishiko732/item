package com.hr_java.entity;

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
 * 复核发放
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("recheckSerial")
@ApiModel(value = "RecheckSerial对象", description = "复核发放")
public class RecheckSerial implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("rSerialId")
    private Integer rSerialId;

    @TableField("statusID")
    private Integer statusID;

    @TableField("payrollID")
    private String payrollID;

    @TableField("recheckTime")
    private LocalDateTime recheckTime;

    @TableField("message")
    private String message;

    @TableField("checkUserName")
    private String checkUserName;
}
