
package com.exch.platform.modular.system.dao;

import com.exch.platform.modular.system.model.Notice;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.exch.platform.modular.system.model.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通知表 Mapper 接口
 * </p>
 *
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 获取通知列表
     */
    List<Map<String, Object>> list(@Param("condition") String condition);

}