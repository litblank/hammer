package com.exch.platform.modular.system.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 拦截数据方法
 * </p>
 *
 * @author chenyd
 * @since 2019-01-09
 */
@TableName("sys_data_method_filter")
public class DataMethodFilter extends Model<DataMethodFilter> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 查询Mapper
     */
    private String method;
    /**
     * 查询名称
     */
    @TableField("method_name")
    private String methodName;
    /**
     * 创建人ID
     */
    @TableField("creater_id")
    private Integer createrId;
    /**
     * 创建时间
     */
    @TableField("creater_date")
    private Date createrDate;
    /**
     * 修改人ID
     */
    @TableField("modify_id")
    private Integer modifyId;
    /**
     * 修改时间
     */
    @TableField("modify_date")
    private Date modifyDate;
    /**
     * 是否删除（0否，1是）
     */
    @TableField("is_delete")
    private Integer isDelete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId = modifyId;
    }

    public Date getCreaterDate() {
        return createrDate;
    }

    public void setCreaterDate(Date createrDate) {
        this.createrDate = createrDate;
    }


    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DataMethodFilter{" +
        ", id=" + id +
        ", method=" + method +
        ", methodName=" + methodName +
        ", createrId=" + createrId +
        ", createrDate=" + createrDate +
        ", modifyId=" + modifyId +
        ", modifyDate=" + modifyDate +
        ", isDelete=" + isDelete +
        "}";
    }
}
