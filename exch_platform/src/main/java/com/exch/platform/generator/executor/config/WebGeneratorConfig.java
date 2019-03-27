package com.exch.platform.generator.executor.config;

import cn.hutool.core.util.StrUtil;
import com.exch.platform.generator.executor.model.GenQo;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;

/**
 * 默认的代码生成的配置
 *
 */
public class WebGeneratorConfig extends AbstractGeneratorConfig {

    private GenQo genQo;

    public WebGeneratorConfig(GenQo genQo) {
        this.genQo = genQo;
    }

    @Override
    protected void config() {
        /**
         * 数据库配置
         */
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername(genQo.getUserName());
        dataSourceConfig.setPassword(genQo.getPassword());
        dataSourceConfig.setUrl(genQo.getUrl());

        /**
         * 全局配置
         */
        globalConfig.setOutputDir(genQo.getProjectPath() + File.separator + "src" + File.separator + "main" + File.separator + "java");
        globalConfig.setFileOverride(true);
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setOpen(false);
        globalConfig.setAuthor(genQo.getAuthor());
        contextConfig.setAuthor(genQo.getAuthor());
        contextConfig.setProPackage(genQo.getProjectPackage());
        contextConfig.setCoreBasePackage(genQo.getCorePackage());

        /**
         * 生成策略
         */
        if (genQo.getIgnoreTabelPrefix() != null) {
            strategyConfig.setTablePrefix(new String[]{genQo.getIgnoreTabelPrefix()});
        }
        strategyConfig.setInclude(new String[]{genQo.getTableName()});
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        packageConfig.setParent(null);
        packageConfig.setEntity(genQo.getProjectPackage() + ".modular."+genQo.getModuleName()+".entity");
        packageConfig.setMapper(genQo.getProjectPackage() + ".modular."+genQo.getModuleName()+".mapper");
        packageConfig.setXml(genQo.getProjectPackage() + ".modular."+genQo.getModuleName()+".mapper.mapping");

        /**
         * 业务代码配置
         */
        contextConfig.setBizChName(genQo.getBizName());
        contextConfig.setModuleName(genQo.getModuleName());
        contextConfig.setProjectPath(genQo.getProjectPath());//写自己项目的绝对路径
        if(ToolUtil.isEmpty(genQo.getIgnoreTabelPrefix())){
            String entityName = StrUtil.toCamelCase(genQo.getTableName());
            contextConfig.setEntityName(StrUtil.upperFirst(entityName));
            contextConfig.setBizEnName(StrUtil.lowerFirst(entityName));
        }else{
            String entiyName = StrUtil.toCamelCase(StrUtil.removePrefix(genQo.getTableName(), genQo.getIgnoreTabelPrefix()));
            contextConfig.setEntityName(StrUtil.upperFirst(entiyName));
            contextConfig.setBizEnName(StrUtil.lowerFirst(entiyName));
        }
        sqlConfig.setParentMenuName(genQo.getParentMenuName());//这里写已有菜单的名称,当做父节点

        /**
         * mybatis-plus 生成器开关
         */
        contextConfig.setEntitySwitch(genQo.getEntitySwitch());
        contextConfig.setDaoSwitch(genQo.getDaoSwitch());
        contextConfig.setServiceSwitch(genQo.getServiceSwitch());

        /**
         * exch 生成器开关
         */
        contextConfig.setControllerSwitch(genQo.getControllerSwitch());
        contextConfig.setIndexPageSwitch(genQo.getIndexPageSwitch());
        contextConfig.setAddPageSwitch(genQo.getAddPageSwitch());
        contextConfig.setEditPageSwitch(genQo.getEditPageSwitch());
        contextConfig.setJsSwitch(genQo.getJsSwitch());
        contextConfig.setInfoJsSwitch(genQo.getInfoJsSwitch());
        contextConfig.setSqlSwitch(genQo.getSqlSwitch());
    }
}
