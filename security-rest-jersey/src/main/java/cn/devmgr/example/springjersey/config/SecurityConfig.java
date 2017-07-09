package cn.devmgr.example.springjersey.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests()
			.antMatchers("/", "/login**", "/test/hi").permitAll()
			.anyRequest().authenticated();
		http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
			
			@Override
 			public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
					throws IOException, ServletException {
				if (authException != null) {
//	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	                response.getWriter().print("Unauthorizated....");
//	                throw new WebApplicationException(HttpServletResponse.SC_UNAUTHORIZED);
	            }
				
			}
			
	    });
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}
}