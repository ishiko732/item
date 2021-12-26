package com.hr_java.Model.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hr_java.Model.entity.Serial;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@TableName("serial")
@ApiModel(value = "payroll", description = "薪酬发放表")
public class PayrollVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("payrollID")
    private String payrollID ;

    @TableField("dep1")
    private String dep1 ;

    @TableField("dep2")
    private String dep2 ;

    @TableField("dep3")
    private String dep3 ;

    @TableField("count")
    private Integer count ;

    @TableField("sum")
    private double sum ;

    @TableField(value = "serials",exist = false)
    private Set<SerialVO> serials;
}
