package com.blogapplication.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blogapplication.security.JwtAuthenticationEntryPoint;
import com.blogapplication.security.JwtAuthenticationFilter;
import com.blogapplication.services.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled=true)
public class SecurityConfig {

	
	public static final String[] PUBLIC_URLS= {
			"/api/auth/**",
			"/api/categories/**",
			"/api/users/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	@Autowired
	private CustomUserDetailsService customUserDetailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private JwtAuthenticationEntryPoint point;
   
    @Autowired
    private JwtAuthenticationFilter filter;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		  http.csrf(csrf -> csrf.disable())
		  .cors(cors->cors.disable())
		  .authorizeHttpRequests(authorize -> authorize.
				  requestMatchers(PUBLIC_URLS).permitAll().
				  requestMatchers("/api/users/**").hasAnyRole("NORMAL","ADMIN")
				//  requestMatchers(HttpMethod.GET).permitAll()
				  .anyRequest()
                 .authenticated()).exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
return http.build();
    }
	@Bean
	DaoAuthenticationProvider   authenticationProvider() {
			DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
			authenticationProvider.setUserDetailsService(customUserDetailService);
			authenticationProvider.setPasswordEncoder(passwordEncoder);
			return authenticationProvider;
		}
	

}
