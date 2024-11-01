package likelion12th.session.config;

import likelion12th.session.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService; // UserDetailsService DI

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors((SecurityConfig::corsAllow))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/join", "/login").permitAll()
                        .requestMatchers("/api/**").authenticated())
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static void corsAllow(CorsConfigurer<HttpSecurity> corsCustomizer) {
        corsCustomizer.configurationSource(request -> {

            CorsConfiguration configuration = new CorsConfiguration();

            //허용할 HTTP METHOD
            configuration.setAllowedMethods(Collections.singletonList("*"));
            //허용할 Origin
            configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
            //허용할 HTTP Request Header
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            //허용할 자격 증명 허용 여부
            configuration.setAllowCredentials(true);
            //CORS 응답 캐싱 시간
            configuration.setMaxAge(3600L);

            return configuration;
        });
    }
}
