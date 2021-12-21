package com.hr_java.Model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 薪酬标准
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("salary")
@ApiModel(value = "Salary对象", description = "薪酬标准")
public class Salary implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("薪酬标准编号")
    @TableId("salaryId")
    private Long salaryId;

    @ApiModelProperty("登记人")
    @TableField("registerName")
    private String registerName;

    @ApiModelProperty("制订人")
    @TableField("MRUName")
    private String mRUName;

    @ApiModelProperty("登记时间")
    @TableField("registerTime")
    private LocalDateTime registerTime;

    @ApiModelProperty("基础工资")
    @TableField("basePay")
    private BigDecimal basePay;


}
