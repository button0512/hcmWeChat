package com.deku.framework;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "spring.datasource")
@Configuration
@Slf4j
public class DataSourceConfig implements InitializingBean {

    private Map<String, HikariConfig> dataSourceMap;

    public void afterPropertiesSet() throws Exception {
        log.info("DataSource loaded is ==> " + dataSourceMap);
    }
}
