package com.twentyfour_seven.catvillage.config;

import com.fasterxml.classmate.TypeResolver;
import com.twentyfour_seven.catvillage.board.dto.BoardGetResponseDto;
import com.twentyfour_seven.catvillage.board.dto.BoardMultiGetResponse;
import com.twentyfour_seven.catvillage.board.dto.BoardPostResponseDto;
import com.twentyfour_seven.catvillage.board.dto.BoardUserCommentResponseDto;
import com.twentyfour_seven.catvillage.cat.dto.CatResponseDto;
import com.twentyfour_seven.catvillage.cat.dto.CatTagResponseDto;
import com.twentyfour_seven.catvillage.dto.MultiBoardResponseDto;
import com.twentyfour_seven.catvillage.dto.MultiResponseDto;
import com.twentyfour_seven.catvillage.feed.dto.FeedGetResponseDto;
import com.twentyfour_seven.catvillage.feed.dto.FeedMultiGetResponseDto;
import com.twentyfour_seven.catvillage.feed.dto.FeedMultiResponseDto;
import com.twentyfour_seven.catvillage.feed.dto.FeedResponseDto;
import com.twentyfour_seven.catvillage.security.dto.TokenDto;
import com.twentyfour_seven.catvillage.user.dto.UserGetResponseDto;
import com.twentyfour_seven.catvillage.user.dto.UserMyInfoDto;
import com.twentyfour_seven.catvillage.user.dto.UserPatchResponseDto;
import com.twentyfour_seven.catvillage.user.dto.UserPostResponseDto;
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
// Swagger v3에서는 아래의 어노테이션을 사용하지 않는 것을 권장
//@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket swagger() {

        TypeResolver typeResolver = new TypeResolver();

        Parameter parameterBuilder = new ParameterBuilder()
                .name(HttpHeaders.AUTHORIZATION)
                .description("Access Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(
                        typeResolver.resolve(UserPostResponseDto.class),
                        typeResolver.resolve(UserPatchResponseDto.class),
                        typeResolver.resolve(UserGetResponseDto.class),
                        typeResolver.resolve(TokenDto.class),
                        typeResolver.resolve(FeedResponseDto.class),
                        typeResolver.resolve(FeedGetResponseDto.class),
                        typeResolver.resolve(FeedMultiGetResponseDto.class),
                        typeResolver.resolve(FeedMultiResponseDto.class),
                        typeResolver.resolve(MultiResponseDto.class),
                        typeResolver.resolve(CatResponseDto.class),
                        typeResolver.resolve(CatTagResponseDto.class),
                        typeResolver.resolve(BoardGetResponseDto.class),
                        typeResolver.resolve(BoardMultiGetResponse.class),
                        typeResolver.resolve(BoardPostResponseDto.class),
                        typeResolver.resolve(BoardUserCommentResponseDto.class),
                        typeResolver.resolve(MultiBoardResponseDto.class),
                        typeResolver.resolve(UserMyInfoDto.class)
                )
//                .useDefaultResponseMessages(true)
                .useDefaultResponseMessages(false)
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
