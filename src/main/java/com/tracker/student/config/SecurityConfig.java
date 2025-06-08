package com.tracker.student.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracker.student.security.filter.JWTAuthProcessingFilter;
import com.tracker.student.security.filter.UsernamePasswordAuthProcessingFilter;
import com.tracker.student.security.handler.UsernamePasswordAuthFailureHandler;
import com.tracker.student.security.handler.UsernamePasswordAuthSuccessHandler;
import com.tracker.student.security.provider.JWTAuthenticationProvider;
import com.tracker.student.security.provider.UsernamePasswordAuthProvider;
import com.tracker.student.security.util.JWTTokenFactory;
import com.tracker.student.security.util.SkipPathRequestMatcher;
import com.tracker.student.util.TokenExtractor;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

	private final static String AUTH_URL = "/v1/login";

	private final static String PROCESSING_URL_V1 = "/v1/**";
	private final static String PROCESSING_URL_V2 = "/v2/**";

	private final static List<String> PERMIT_ENDPOIND_LIST = Arrays.asList(AUTH_URL);
	private final static List<String> AUTHENTICATED_ENDPOINT_LIST = Arrays.asList(PROCESSING_URL_V1, PROCESSING_URL_V2);

	@Autowired
	private UsernamePasswordAuthProvider usernamePasswordAuthProvider;

	@Autowired
	private JWTAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(usernamePasswordAuthProvider).authenticationProvider(jwtAuthenticationProvider);
	}

	@Bean
	public AuthenticationSuccessHandler usernamePasswordAuthSuccessHandler(ObjectMapper objectMapper,
			JWTTokenFactory jwtTokenFactory) {
		return new UsernamePasswordAuthSuccessHandler(objectMapper, jwtTokenFactory);
	}

	@Bean
	public AuthenticationFailureHandler usernamePasswordAuthFailurHandler(ObjectMapper objectMapper) {
		return new UsernamePasswordAuthFailureHandler(objectMapper);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public UsernamePasswordAuthProcessingFilter usernamePasswordAuthProcessingFilter(ObjectMapper objectMapper,
			AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler,
			AuthenticationManager manager) {
		UsernamePasswordAuthProcessingFilter filter = new UsernamePasswordAuthProcessingFilter(AUTH_URL, objectMapper,
				successHandler, failureHandler);
		filter.setAuthenticationManager(manager);
		return filter;
	}

	@Bean
	public JWTAuthProcessingFilter jwtAuthProcessingFilter(TokenExtractor tokenExtractor,
			AuthenticationFailureHandler failureHandler, AuthenticationManager manager) {
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(PERMIT_ENDPOIND_LIST, AUTHENTICATED_ENDPOINT_LIST);
		JWTAuthProcessingFilter filter = new JWTAuthProcessingFilter(matcher, tokenExtractor, failureHandler);
		filter.setAuthenticationManager(manager);
		return filter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, UsernamePasswordAuthProcessingFilter filter,
			JWTAuthProcessingFilter jwtAuthProcessingFilter) throws Exception {
		http.authorizeHttpRequests(auth -> auth.requestMatchers(PROCESSING_URL_V1, PROCESSING_URL_V2).authenticated())
				.csrf(csrf -> csrf.disable()).sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthProcessingFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
