package org.delivery.storeadmin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;


@Configuration
@EnableWebSecurity // 시큐리티 활성화
public class SecurityConfig {
    // 정적 자원 제외


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers(SWAGGER.toArray(new String[0])).permitAll();
                    authorizeRequests.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
                    authorizeRequests.requestMatchers("/open-api/**").permitAll(); // open-api 하위 path 인증 x
                    authorizeRequests.anyRequest().authenticated();
                })
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                );
        return http.build();
    }

    // Swagger
    private final List<String> SWAGGER = List.of(
            "/swagger-ui.html" ,
            "/swagger-ui/**" ,
            "/v3/api-docs/**"
    );

    @Bean
    public PasswordEncoder passwordEncoder() {
        // hash 암호화
        return new BCryptPasswordEncoder();

    }
}
