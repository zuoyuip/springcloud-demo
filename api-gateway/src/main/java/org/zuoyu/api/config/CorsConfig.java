package org.zuoyu.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置.
 *
 * @author zuoyu
 * @program api-gateway
 * @create 2020-03-04 23:39
 **/
@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowCredentials(true);
    configuration.addAllowedHeader(CorsConfiguration.ALL);
    configuration.addAllowedMethod(CorsConfiguration.ALL);
    configuration.addAllowedOrigin(CorsConfiguration.ALL);
    configuration.setMaxAge(3600L);

    source.registerCorsConfiguration("/**", configuration);
    return new CorsFilter(source);
  }
}
