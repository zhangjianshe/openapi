package cn.mapway.openapi.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * MapwayOpenApiAutoConfiguration
 * 自动化装配类
 *
 * @author zhangjianshe@gmail.com
 */
@Configuration
@ConditionalOnWebApplication(
        type = ConditionalOnWebApplication.Type.SERVLET
)
@ConditionalOnProperty(
        value = {"springfox.documentation.openapi-ui.enabled"},
        havingValue = "true",
        matchIfMissing = true
)
public class MapwayOpenApiAutoConfiguration {
    private static Logger log= LoggerFactory.getLogger(MapwayOpenApiWebMvcConfig.class);
    @Value("${springfox.documentation.openapi-ui.base-url}")
    private String openapiBaseUrl;

    public MapwayOpenApiAutoConfiguration() {
    }

    @Bean
    public MapwayOpenApiWebMvcConfig mapwayOpenApiWebMvcConfig() {

        return new MapwayOpenApiWebMvcConfig(this.fixup(this.openapiBaseUrl));
    }

    private String fixup(String swaggerBaseUrl) {
        return StringUtils.trimTrailingCharacter(swaggerBaseUrl == null ? "" : swaggerBaseUrl, '/');
    }
}