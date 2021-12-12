package com.hr_java.entity;

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
 * 复核用户
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("recheckUser")
@ApiModel(value = "RecheckUser对象", description = "复核用户")
public class RecheckUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rUserId", type = IdType.AUTO)
    private Integer rUserId;

    @TableField("statusID")
    private Integer statusID;

    @TableField("uid")
    private Long uid;

    @TableField("recheckTime")
    private LocalDateTime recheckTime;


}
