
package com.exch.platform.modular.system.service.impl;

import com.exch.platform.modular.system.dao.RelationMapper;
import com.exch.platform.modular.system.model.Relation;
import com.exch.platform.modular.system.service.IRelationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 */
@Service
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation> implements IRelationService {

}
