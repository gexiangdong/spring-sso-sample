package cn.devmgr.example.sso.jwt.mvc.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private JwtAccessTokenConverter jatc;
 
	   
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/", "/login**", "/index")
            .permitAll()
            .anyRequest()
            .authenticated();
        
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }


    
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        //defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
    
    
    @Bean
    public JwtTokenStore tokenStore() {
    	DefaultAccessTokenConverter datc  = new DefaultAccessTokenConverter();
        datc.setUserTokenConverter(new JWTUserAuthenticationConverter());
    	jatc.setAccessTokenConverter(datc);
        return new JwtTokenStore(jatc);
    }

}