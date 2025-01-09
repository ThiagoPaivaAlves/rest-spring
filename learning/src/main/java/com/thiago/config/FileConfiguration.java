package com.thiago.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*This is not mandatory, it is just another way to retrieve a custom property on application.yml*/
@Configuration
@ConfigurationProperties(prefix="file")
@Data
public class FileConfiguration {
    
    @Value("${spring.file.upload-dir: uploads}")
    private String uploadDir;

}
