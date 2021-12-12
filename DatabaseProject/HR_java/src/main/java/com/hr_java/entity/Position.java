package com.hr_java.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 职位
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("position")
@ApiModel(value = "Position对象", description = "职位")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("pid")
    private Integer pid;

    @ApiModelProperty("绑定机构")
    @TableField("fid")
    private Integer fid;

    @ApiModelProperty("职位类别")
    @TableField("pcId")
    private Integer pcId;

    @ApiModelProperty("职称id")
    @TableField("jtId")
    private Integer jtId;

    @ApiModelProperty("薪酬标准")
    @TableField("salaryId")
    private Long salaryId;

    @ApiModelProperty("职称名称")
    @TableField("name")
    private String name;


}
