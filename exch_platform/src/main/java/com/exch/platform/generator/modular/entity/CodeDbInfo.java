package com.exch.platform.generator.modular.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 数据库链接信息
 * </p>
 *
 */
@TableName("code_dbinfo")
@Data
public class CodeDbInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("id")
    private Long id;
    /**
     * 别名
     */
    private String name;
    /**
     * 数据库驱动
     */
    @TableField("db_driver")
    private String dbDriver;
    /**
     * 数据库地址
     */
    @TableField("db_url")
    private String dbUrl;
    /**
     * 数据库账户
     */
    @TableField("db_user_name")
    private String dbUserName;
    /**
     * 连接密码
     */
    @TableField("db_password")
    private String dbPassword;
    /**
     * 数据库类型
     */
    @TableField("db_type")
    private String dbType;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

}
