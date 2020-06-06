package com.zsc.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropertiesUtils {

    public static Properties getProperties(String location) {
        Properties properties = null;
        try {
            log.info("loading properties[{}]", location);
            properties = PropertiesLoaderUtils
                    .loadProperties(
                        new EncodedResource
                            (
                            new ClassPathResource(location),
                            "UTF-8"
                            )
                    );
        } catch (IOException e) {
            log.error("load error {}, error msg{}", location, e.getMessage());
        }
        return properties;
    }
}
