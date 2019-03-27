package com.exch.platform.generator.executor;


import com.exch.platform.generator.executor.config.ExchGeneratorConfig;

/**
 * 代码生成器,可以生成实体,dao,service,controller,html,js
 *
 */
public class ExchCodeGenerator {

    public static void main(String[] args) {

        /**
         * Mybatis-Plus的代码生成器:
         *      mp的代码生成器可以生成实体,mapper,mapper对应的xml,service
         */
        ExchGeneratorConfig ExchGeneratorConfig = new ExchGeneratorConfig();
        ExchGeneratorConfig.doMpGeneration();

        /**
         * Exch的生成器:
         *      Exch的代码生成器可以生成controller,html页面,页面对应的js
         */
        ExchGeneratorConfig.doExchGeneration();
    }

}