package com.abroad.baekjunghyunDev.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.abroad.baekjunghyunDev.config.auth.PrincipalDetailService;
import com.abroad.baekjunghyunDev.config.schema.SiteExtractorFilter;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	public BCryptPasswordEncoder encoderPWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
		AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
		
		auth.userDetailsService(principalDetailService).passwordEncoder(encoderPWD());
		return auth.build();
	}
	
	@Autowired
	private SiteExtractorFilter siteExtractorFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.
			csrf(csrf->csrf.disable()).
			cors(cors->cors.configurationSource(corsConfigurationSource())).
			authorizeHttpRequests(authz->authz.
					dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll().
					requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/v1/*/signupProc", "/v1/*/loginProc", "/v1/*/qna", "/v1/*/video", "/v1/*/me").permitAll().
					anyRequest().authenticated()).
			formLogin(formLogin-> formLogin.
					loginPage("/auth/loginForm").permitAll().
					usernameParameter("email").
					loginProcessingUrl("/v1/*/loginProc")
					.successHandler((request, response, authentication) -> {
				        response.setStatus(HttpServletResponse.SC_OK);
				        response.setContentType("application/json");
				        response.getWriter().write("{\"message\":\"로그인 성공\"}");
				    })
				    .failureHandler((request, response, exception) -> {
				        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				        response.setContentType("application/json");
				        response.getWriter().write("{\"message\":\"로그인 실패\"}");
				    })) 
			.logout(logout -> logout
				    .logoutUrl("/v1/*/logout")
				    .invalidateHttpSession(true)
				    .clearAuthentication(true)
				    .logoutSuccessHandler((request, response, authentication) -> {
				        response.setStatus(HttpServletResponse.SC_OK); // 상태 코드 200 설정
				        response.setContentType("application/json"); // 응답 Content-Type을 JSON으로 설정
				        response.getWriter().write("{\"message\":\"로그아웃 성공\"}");
				    }))
			.exceptionHandling(exceptions -> exceptions
		            .authenticationEntryPoint((request, response, authException) -> {
		                // 인증되지 않은 사용자에 대해 리다이렉트 대신 401 Unauthorized를 반환
		                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		            }));
		
            // SiteExtractorFilter를 Security 필터 체인의 UsernamePasswordAuthenticationFilter 이전에 추가
            http.addFilterBefore(siteExtractorFilter, UsernamePasswordAuthenticationFilter.class);
            http.addFilterBefore(siteExtractorFilter, LogoutFilter.class);         
            
            
		return http.build();
	}
	
	 // CORS 설정 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(
        		"http://localhost:8000",
        		"http://localhost:4000",
        		"http://localhost:4001",
        		"http://localhost:4002",
        		"http://localhost:4003",
        		"http://localhost:4004",
        		"http://localhost:4005",
        		"https://*.abroad0213.com"
        		)); 
        //configuration.setAllowedOriginPatterns(Collections.singletonList("https://*.abroad0213.com"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH")); // 허용할 HTTP 메서드 설정
        configuration.setAllowCredentials(true); // 자격 증명 허용
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // 허용할 헤더 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        System.out.println("CORS 설정이 적용되었습니다."); // 확인용 로그
        return source;
    }
}

