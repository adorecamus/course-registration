package com.teamsparta.courseregistration.infra.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
// Application에서 전역적으로 사용하는 설정은 @Configuration 이용
// Spring이 Bean을 등록할 때 가장 먼저 읽힘
class SwaggerConfig {

    @Bean   // import하며 등록된 openAPI Bean을 재정의
    fun openAPI(): OpenAPI = OpenAPI()
        .components(Components())
        .info(
            Info()
                .title("Course API")
                .description("Course API schema")
                .version("1.0.0")
        )
}