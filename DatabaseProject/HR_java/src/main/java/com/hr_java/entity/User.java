package com.hr_java.entity;

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
 * 用户表
 * </p>
 *
 * @author liuyuanfeng
 * @since 2021-12-12
 */
@Getter
@Setter
@TableName("user")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("uid")
    private Long uid;

    @TableField("rid")
    private Integer rid;

    @TableField("fid")
    private Integer fid;

    @TableField("pid")
    private Integer pid;

    @TableField("name")
    private String name;

    @TableField("password")
    private String password;

    @ApiModelProperty("头像路径")
    @TableField("image")
    private String image;

    @TableField("gender")
    private String gender;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("QQ")
    private String qq;

    @TableField("address")
    private String address;

    @ApiModelProperty("邮编号码")
    @TableField("post")
    private String post;

    @ApiModelProperty("出生地")
    @TableField("native")
    private String _native;

    @TableField("birth")
    private LocalDateTime birth;

    @ApiModelProperty("民族")
    @TableField("nation")
    private String nation;

    @ApiModelProperty("宗教信仰")
    @TableField("religion")
    private String religion;

    @ApiModelProperty("政治面貌")
    @TableField("politicalFace")
    private String politicalFace;

    @ApiModelProperty("身份证")
    @TableField("cardID")
    private String cardID;

    @ApiModelProperty("学历")
    @TableField("degree")
    private String degree;

    @ApiModelProperty("专业")
    @TableField("speciality")
    private String speciality;

    @ApiModelProperty("简历")
    @TableField("resume")
    private String resume;

    @ApiModelProperty("开户行")
    @TableField("payName")
    private String payName;

    @ApiModelProperty("银行卡号码")
    @TableField("payID")
    private String payID;

    @ApiModelProperty("登记人")
    @TableField("booker")
    private Long booker;

    @TableField("registrantTime")
    private LocalDateTime registrantTime;


}
