package com.guat.lzp.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 安全配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 DelegatingPasswordEncoder，自动识别 {noop}、{bcrypt} 等前缀
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(customUserDetailsService)
            .authorizeHttpRequests(authz -> authz
                // 公开页面 & 静态资源
                .antMatchers("/login", "/register", "/css/**", "/js/**", "/images/**", "/img/**").permitAll()
                .antMatchers("/api/user/register").permitAll()
                
                // 缓存测试接口 - 公开访问
                .antMatchers("/test/cache/**").permitAll()
                
                // 管理后台页面 —— 仅管理员
                .antMatchers("/index", "/admin/**").hasRole("admin")
                
                // API 权限：查询接口所有人可用，增删改仅管理员
                .antMatchers(org.springframework.http.HttpMethod.GET, "/api/product/**", "/api/category/**").authenticated()
                .antMatchers("/api/product/**", "/api/category/**").hasRole("admin")
                
                // 其余需认证
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(customLoginSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .rememberMe(remember -> remember
                .key("smart-product-manager-remember-me")
                .tokenValiditySeconds(7 * 24 * 60 * 60)  // 7天有效期
                .userDetailsService(customUserDetailsService)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions().sameOrigin());
        return http.build();
    }
}
