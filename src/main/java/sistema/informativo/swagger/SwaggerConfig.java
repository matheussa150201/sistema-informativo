//package sistema.informativo.swagger;
//
//import org.springframework.context.annotation.Bean;
//
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public Docket forumApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("sistema.informativo"))
//                .paths(PathSelectors.ant("/**"))
//                .build()
//                .apiInfo(this.info());
//    }
//
//    public ApiInfo info() {
//
//        return new ApiInfoBuilder()
//                .title("API-Publicacao")
//                .description("Projeto feito para teste avaliativo " +
//                        "na empresa da Solides")
//                .version("1.0")
//                .build();
//    }
//}