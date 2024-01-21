//package sistema.informativo.security;
//
//import jakarta.servlet.Filter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:4200"); // Permite solicitações deste domínio
//        configuration.addAllowedMethod("*"); // Permite todos os métodos HTTP (GET, POST, PUT, etc.)
//        configuration.addAllowedHeader("*"); // Permite todos os cabeçalhos
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }
//
//    @Bean
//    public Filter corsFilter() {
//        return new CorsFilter(corsConfigurationSource());
//    }
//}
