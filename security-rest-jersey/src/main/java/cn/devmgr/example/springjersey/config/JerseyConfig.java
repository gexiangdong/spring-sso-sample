package cn.devmgr.example.springjersey.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
    	register(ThrowableMapper.class);
    	packages("cn.devmgr.example.springjersey.endpoint");
        // register(DemoEndpoint.class);
    }

}