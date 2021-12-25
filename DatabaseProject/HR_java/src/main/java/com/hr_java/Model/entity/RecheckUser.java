package com.hr_java.Model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * <p>
 * 复核用户
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @TableField(value = "tid",updateStrategy = FieldStrategy.IGNORED)//设置：允许为null
    private Integer tid;

    @TableField("recheckTime")
    private LocalDateTime recheckTime;

    @TableField("checkUserName")
    private String checkUserName;

    @TableField(exist = false)
    private User user;
}
