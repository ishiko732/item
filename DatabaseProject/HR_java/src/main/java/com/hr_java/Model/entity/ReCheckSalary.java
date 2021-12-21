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
 * 复核薪水标准
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("reCheckSalary")
@ApiModel(value = "ReCheckSalary对象", description = "复核薪水标准")
public class ReCheckSalary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rSalaryId", type = IdType.AUTO)
    private Integer rSalaryId;

    @TableField("statusID")
    private Integer statusID;

    @TableField("salaryId")
    private Long salaryId;

    @TableField("message")
    private String message;

    @TableField("recheckTime")
    private LocalDateTime recheckTime;

    @TableField("checkUserName")
    private String checkUserName;
}
