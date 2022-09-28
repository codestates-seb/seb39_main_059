package com.twentyfour_seven.catvillage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
Swagger Rest API 문서 자동 생성을 위한 설정 클래스 입니다.
 */

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket swagger() {

        Parameter parameterBuilder = new ParameterBuilder()
                .name(HttpHeaders.AUTHORIZATION)
                .description("Access Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("냥빌리지 API 명세 페이지")
                .description("냥빌리지 Swagger API 명세 페이지입니다.")
                .version("1.0")
                .build();
    }
}
