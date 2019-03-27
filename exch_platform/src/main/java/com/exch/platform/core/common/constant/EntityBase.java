package com.exch.platform.core.common.constant;

import com.baomidou.mybatisplus.activerecord.Model;

import java.util.Date;

public abstract class EntityBase<T extends Model>   extends Model<T> {
    //创建人
    private Integer createrId;
    //创建时间
    private Date createrDate;

    //创建人
    private Integer modifyId;
    //创建时间
    private Date modifyDate;

    //是否删除
    private Integer isDelete;

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public Date getCreaterDate() {
        return createrDate;
    }

    public void setCreaterDate(Date createrDate) {
        this.createrDate = createrDate;
    }

    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId = modifyId;
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
}
