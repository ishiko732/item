package com.hr_java.Model.entity;

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
 * 发放薪水
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("serial")
@ApiModel(value = "Serial对象", description = "发放薪水")
public class Serial implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "serialID", type = IdType.AUTO)
    private Integer serialID;

    @TableField("uid")
    private Long uid;

    @ApiModelProperty("发放单编号")
    @TableField("payrollID")
    private String payrollID;

    @TableField("payrollTime")
    private LocalDateTime payrollTime;


}
