package com.sys.bill.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @Title: Swagger2Config
 * @Description: swagger配置
 * @author: furg@senthink.com
 * @date: 2019/8/27 15:28
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    private static final Set<String> DEFAULT_PROTOCOLS = Sets.newHashSet("http", "https");

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = Sets.newHashSet("application/json");

    /**
     * 物联网无线云平台管理接口文档
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .host("localhost:8080")
                .protocols(DEFAULT_PROTOCOLS)
                .groupName("API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sys.bill.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("bill管理平台")
                .description("更多文档请参考: https://www.baidu.com/")
                .version("1.0-beta")
                .termsOfServiceUrl("https://www.baidu.com/")
                .contact(new Contact("付荣刚", "https://www.senthink.com", "furg@senthink.com"))
                .license("Apache-2.0")
                .licenseUrl("https://www.baidu.com")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("Authorization", "Authorization", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.any())
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }

}
