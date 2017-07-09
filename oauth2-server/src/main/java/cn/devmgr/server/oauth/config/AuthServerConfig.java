package cn.devmgr.server.oauth.config;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    private static final Log log = LogFactory.getLog(AuthServerConfig.class);
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private DataSource dataSource;

    private transient BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public JdbcTokenStore tokenStore() {
        if(log.isTraceEnabled()){
            log.trace("..tokenStore()..");
        }
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
         return new JdbcAuthorizationCodeServices(dataSource);
    }



    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.passwordEncoder(passwordEncoder).allowFormAuthenticationForClients()
            .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("rs1")
            .secret(passwordEncoder.encode("secret"))
            .authorizedGrantTypes("authorization_code")
            .scopes("user_info")
            .autoApprove(true);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // use default token.
        endpoints.authenticationManager(authenticationManager);

        // use custom token.
        // endpoints.authorizationCodeServices(authorizationCodeServices())
        //        .authenticationManager(authenticationManager).tokenStore(tokenStore())
        //        .tokenEnhancer(tokenEnhancer()).accessTokenConverter(accessTokenConverter())
        //        .approvalStoreDisabled();
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        if(log.isTraceEnabled()){
            log.trace("..tokenEnhancer()..");
        }
        return new AuthoritiesTokenEnhancer();
    }
    
    @Bean
    public DefaultAccessTokenConverter accessTokenConverter() {
        if(log.isTraceEnabled()){
            log.trace("..accessTokenConverter()..");
        }
        return new DefaultAccessTokenConverter();
    }
}
