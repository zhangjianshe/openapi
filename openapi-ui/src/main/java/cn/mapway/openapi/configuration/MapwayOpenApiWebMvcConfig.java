package cn.mapway.openapi.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MapwayOpenApiWebMvcConfig
 * <p>
 * WEB MVC 配置 提供 UI输出
 *
 * @author zhangjianshe@gmail.com
 */
public class MapwayOpenApiWebMvcConfig implements WebMvcConfigurer {
    private static Logger log= LoggerFactory.getLogger(MapwayOpenApiWebMvcConfig.class);

    private final String baseUrl;

    public MapwayOpenApiWebMvcConfig(String baseUrl) {
        this.baseUrl = baseUrl;
        log.info("OPENAPI 文档UARL配置初始化@{}",this.baseUrl);
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
        registry.addResourceHandler(new String[]{baseUrl + "/**"}).addResourceLocations(new String[]{"classpath:/META-INF/jsviewer/"}).resourceChain(false);
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(this.baseUrl).setViewName("redirect:" + this.baseUrl + "/index.html?l="+this.baseUrl);
    }
}