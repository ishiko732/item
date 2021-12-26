package com.hr_java.Model.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hr_java.Model.entity.Subsidy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("serial")
@ApiModel(value = "Serial对象", description = "薪酬发放详细内容")
public class SerialVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "serialID", type = IdType.AUTO)
    private Integer serialID;

    @ApiModelProperty("薪酬标准编号")
    @TableField("salaryId")
    private Long salaryId;

    @ApiModelProperty("发放单编号")
    @TableField("payrollID")
    private String payrollID;

    @TableField("uid")
    private Long uid;

    @TableField("name")
    private String name;

    @ApiModelProperty("基础工资")
    @TableField("basePay")
    private BigDecimal basePay;

    @TableField(exist = false)
    private Set<Subsidy> subsidies;

    @ApiModelProperty("养老保险")
    @TableField(value = "pi", exist = false)
    private double pi;

    @ApiModelProperty("医疗保险")
    @TableField(value = "mi", exist = false)
    private double mi;

    @ApiModelProperty("失业保险")
    @TableField(value = "ui", exist = false)
    private double ui;

    @ApiModelProperty("住房公积金")
    @TableField(value = "housingFund", exist = false)
    private double housingFund;

    @ApiModelProperty("薪酬总额")
    @TableField(value = "total_Salary", exist = false)
    private double total_Salary;

    @TableField("bounty")
    private Double bounty;

    @TableField("penalty")
    private Double penalty;


}
