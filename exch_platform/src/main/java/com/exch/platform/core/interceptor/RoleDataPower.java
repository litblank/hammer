package com.exch.platform.core.interceptor;

import com.baomidou.mybatisplus.plugins.SqlParserHandler;
import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.exch.platform.core.shiro.ShiroKit;
import com.exch.platform.core.shiro.ShiroUser;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * mybatis拦截器实现角色数据拦截
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class RoleDataPower extends SqlParserHandler implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler)PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        } else {
            //com.exch.platform.modular.system.dao.MenuMapper.getMenusByRoleIds
            String sqlid=mappedStatement.getId();
            if(sqlid.contains("com.exch.platform.modular")){
                ShiroUser user=ShiroKit.getUser();
                if(user!=null && user.getDataPower()!=null){
                    Map<String, String> dataPower=user.getDataPower();

                    String[] strl=sqlid.split("\\.");
                    if(strl.length>2){
                        String sqlidMethod=strl[strl.length-2]+"."+strl[strl.length-1];

                        if(dataPower.get(sqlidMethod)!=null){
                            BoundSql boundSql = (BoundSql)metaObject.getValue("delegate.boundSql");
                            String originalSql = boundSql.getSql();
                            String powersql=dataPower.get(sqlidMethod);
                            //当前用户                            //当前机构
                            powersql=powersql.replaceAll("@userid",user.getId()+"")
                                    .replaceAll("@orgid",user.getDeptId()+"");

                            StringBuilder msql=new StringBuilder();
                            msql.append("select * from (");
                            msql.append(originalSql);
                            msql.append(" ) rdp_  ");

                            if(StringUtils.isNotEmpty(powersql)){
                                msql.append(" where ");
                                msql.append(powersql);
                            }

                            metaObject.setValue("delegate.boundSql.sql", msql.toString());
                        }
                    }
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
