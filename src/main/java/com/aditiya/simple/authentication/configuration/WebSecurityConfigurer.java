package com.aditiya.simple.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aditiya.simple.applicationuser.ApplicationUserService;
import com.aditiya.simple.authentication.jwt.JwtAuthenticationEntryPoint;
import com.aditiya.simple.authentication.jwt.JwtAuthenticationTokenFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfigurer {
    private final ApplicationUserService userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    JwtAuthenticationTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    AuthenticationManager authenticationManagerBean(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
            .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(this.userDetailsService)
            .passwordEncoder(this.bCryptPasswordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http.cors().and().csrf().disable()
        //     .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        //     .authorizeHttpRequests().requestMatchers("/tokens").permitAll().and()
        //     .authorizeHttpRequests().anyRequest().authenticated();

        http.cors(CorsConfigurer::disable)
        .csrf(CsrfConfigurer::disable)
        .exceptionHandling(exception -> exception.authenticationEntryPoint(this.unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(request -> request.requestMatchers("/tokens", "/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // @Bean
    // WebSecurityCustomizer webSecurityCustomizer() {
    //     return web -> web.ignoring().requestMatchers("/swagger-ui/**", "v3/api-docs/**");
    // }
}
