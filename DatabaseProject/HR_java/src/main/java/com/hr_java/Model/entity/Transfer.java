package com.hr_java.Model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 调动临时表
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("transfer")
@ApiModel(value = "Transfer对象", description = "调动临时表")
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tid", type = IdType.AUTO)
    private Integer tid;

    @TableField("uid")
    private Long uid;

    @TableField("rid")
    private Integer rid;

    @TableField("pid")
    private Integer pid;


}
