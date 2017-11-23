package com.dongfg.project.api.config.property;

import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

/**
 * @author dongfg
 * @date 17-11-22
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "fastjson")
public class FastJsonProperty {
    private Charset charset;
    private String dateFormat;
    private SerializerFeature[] serializerFeatures;
}
