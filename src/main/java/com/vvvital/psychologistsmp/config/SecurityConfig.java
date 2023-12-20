package com.vvvital.psychologistsmp.config;

import com.vvvital.psychologistsmp.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityUserService securityUserService;

    @Autowired
    public SecurityConfig(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(basic -> basic.init(http))
                .authorizeHttpRequests(request->request
                        .requestMatchers(HttpMethod.POST,"/api/users/save").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/users/all/psychologist","/api/users/get/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui/index.html#").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/**",
                                "/v3/api-docs/**", "/v3/api-docs.yaml",
                                "/swagger-ui/**","/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()

                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(securityUserService);
        return daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

