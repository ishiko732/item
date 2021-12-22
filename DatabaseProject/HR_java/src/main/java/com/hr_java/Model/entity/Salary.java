package com.hr_java.Model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

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

    @TableField(exist = false)
    private Set<Subsidy> subsidies;

    @ApiModelProperty("养老保险")
    @TableField(value = "pi",exist = false)
    private double pi;

    @ApiModelProperty("医疗保险")
    @TableField(value = "mi",exist = false)
    private double mi;

    @ApiModelProperty("失业保险")
    @TableField(value = "ui",exist = false)
    private double ui;

    @ApiModelProperty("住房公积金")
    @TableField(value = "housingFund",exist = false)
    private double housingFund;

    @ApiModelProperty("薪酬总额")
    @TableField(value = "total_Salary",exist = false)
    private double total_Salary;
}
