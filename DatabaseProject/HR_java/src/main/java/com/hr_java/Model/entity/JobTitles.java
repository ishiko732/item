package com.hr_java.Model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 职称
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("jobTitles")
@ApiModel(value = "JobTitles对象", description = "职称")
public class JobTitles implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "jtId", type = IdType.AUTO)
    private Integer jtId;

    @TableField("name")
    private String name;


}
