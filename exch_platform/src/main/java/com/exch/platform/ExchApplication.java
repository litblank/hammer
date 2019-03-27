
package com.exch.platform;

import cn.stylefeng.roses.core.config.WebAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * SpringBoot方式启动类
 *
 */
@SpringBootApplication(exclude = WebAutoConfiguration.class)
public class ExchApplication {

    private final static Logger logger = LoggerFactory.getLogger(ExchApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ExchApplication.class, args);
        logger.info("ExchApplication is success!");
    }
}
