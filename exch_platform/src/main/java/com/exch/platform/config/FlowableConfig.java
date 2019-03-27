package com.exch.platform.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * 工作流配置
 *
 */
@Configuration
public class FlowableConfig implements ProcessEngineConfigurationConfigurer {


    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {

        processEngineConfiguration.setAsyncExecutorActivate(false);
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
//        processEngineConfiguration.setProcessDiagramGenerator(new GunsDefaultProcessDiagramGenerator());
    }
}