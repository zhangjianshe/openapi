package cn.mapway.openapi;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OpenapiViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenapiViewApplication.class, args);
    }

}