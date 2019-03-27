package com.exch.platform.generator.db;

import com.exch.platform.generator.modular.entity.CodeDbInfo;
import cn.stylefeng.roses.core.db.DbInitializer;

/**
 * 数据库链接信息表初始化
 *
 */
public class DbInfoInitializer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `code_dbinfo` (\n" +
                "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(20) DEFAULT NULL COMMENT '别名',\n" +
                "  `db_driver` varchar(100) NOT NULL COMMENT '数据库驱动',\n" +
                "  `db_url` varchar(200) NOT NULL COMMENT '数据库地址',\n" +
                "  `db_user_name` varchar(100) NOT NULL COMMENT '数据库账户',\n" +
                "  `db_password` varchar(100) NOT NULL COMMENT '连接密码',\n" +
                "  `db_type` varchar(10) DEFAULT NULL COMMENT '数据库类型',\n" +
                "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                "  PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据库链接信息'";
    }

    @Override
    public String getTableName() {
        return "code_dbinfo";
    }

    @Override
    public Class<?> getEntityClass() {
        return CodeDbInfo.class;
    }
}
