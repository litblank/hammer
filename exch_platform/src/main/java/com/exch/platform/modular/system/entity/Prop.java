package com.exch.platform.modular.system.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 公共参数表
 * </p>
 *
 * @author wanbhb
 * @since 2019-01-11
 */
@TableName("sys_prop")
public class Prop extends Model<Prop> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 版本
     */
    private Integer version;
    /**
     * APP_ID
     */
    @TableField("app_id")
    private String appId;
    /**
     * 参数名称
     */
    @TableField("prop_name")
    private String propName;
    /**
     * 参数说明
     */
    @TableField("prop_desc")
    private String propDesc;
    /**
     * 参数值
     */
    @TableField("prop_value")
    private String propValue;
    /**
     * 备注
     */
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropDesc() {
        return propDesc;
    }

    public void setPropDesc(String propDesc) {
        this.propDesc = propDesc;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Prop{" +
        ", id=" + id +
        ", version=" + version +
        ", appId=" + appId +
        ", propName=" + propName +
        ", propDesc=" + propDesc +
        ", propValue=" + propValue +
        ", remark=" + remark +
        "}";
    }
}
