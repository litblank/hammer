
package com.exch.platform.modular.system.dao;

import com.exch.platform.modular.system.model.Dict;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.exch.platform.modular.system.model.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 根据编码获取词典列表
     */
    List<Dict> selectByCode(@Param("code") String code);

    /**
     * 查询字典列表
     */
    List<Map<String, Object>> list(@Param("condition") String conditiion);

    /**
     * 根据父类编码获取词典列表
     */
    List<Dict> selectByParentCode(@Param("code") String code);

    /**
     * 获取数据字典
     */
    List<Map<String,Object>> listDict(HashMap<String,Object> parm);
}