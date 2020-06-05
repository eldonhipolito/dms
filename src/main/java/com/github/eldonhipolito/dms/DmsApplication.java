package com.github.eldonhipolito.dms;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = "com.github.com.eldonhipolito.dms")
@EnableJpaRepositories(basePackages = "com.github.com.eldonhipolito.dms.repository")
@EntityScan(basePackages = "com.github.com.eldonhipolito.dms.core")
public class DmsApplication {

  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  public static void main(String[] args) {
    SpringApplication.run(DmsApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bcryptHashingStrategy() {
    return new BCryptPasswordEncoder();
  }

  //  @Bean
  //  public WebMvcConfigurer registerCors() {
  //    System.err.println("FUCK BAT AYAW GUMANA");
  //    return new WebMvcConfigurer() {
  //      @Override
  //      public void addCorsMappings(CorsRegistry registry) {
  //        System.out.println("PUTANGINA BAT AYAW GUMANA");
  //        registry
  //            .addMapping("/**")
  //            .allowedOrigins("http://localhost:3000/*")
  //            .allowedMethods("*")
  //            .allowedHeaders("*")
  //            .allowCredentials(false)
  //            .maxAge(3600);
  //      }
  //    };
  //  }
  //
  //  @Bean
  //  public WebSecurityConfigurerAdapter fuckThis() {
  //    return new WebSecurityConfigurerAdapter() {
  //
  //      @Override
  //      protected void configure(HttpSecurity http) throws Exception {
  //        System.err.println("ANDITO AKO");
  //        http.authorizeRequests().antMatchers("/url*").permitAll();
  //        http.cors();
  //      }
  //    };
  //  }
  //
  //  @Bean
  //  CorsConfigurationSource corsConfigurationSource() {
  //    CorsConfiguration configuration = new CorsConfiguration();
  //    configuration.setAllowedOrigins(Arrays.asList("*"));
  //    configuration.setAllowedMethods(
  //        Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
  //    configuration.setAllowedHeaders(
  //        Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
  //    configuration.setAllowCredentials(false);
  //    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  //    source.registerCorsConfiguration("/**", configuration);
  //    return source;
  //  }
}
