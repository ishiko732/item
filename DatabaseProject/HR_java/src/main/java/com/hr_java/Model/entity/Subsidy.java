package com.hr_java.Model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 补助项目
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("subsidy")
@ApiModel(value = "Subsidy对象", description = "补助项目")
public class Subsidy implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "subsidyId", type = IdType.AUTO)
    private Integer subsidyId;

    @TableField("salaryId")
    private Long salaryId;

    @TableField("subsidyName")
    private String subsidyName;

    @TableField("money")
    private BigDecimal money;


}
