package com.hr_java.Model.entity;

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
 * 职位类别
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("PositionClassification")
@ApiModel(value = "PositionClassification对象", description = "职位类别")
public class PositionClassification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("pcId")
    private Integer pcId;

    @TableField("name")
    private String name;


}
