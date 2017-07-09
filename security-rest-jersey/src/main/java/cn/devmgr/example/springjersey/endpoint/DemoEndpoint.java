package cn.devmgr.example.springjersey.endpoint;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;


@Path("/test/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Controller
public class DemoEndpoint {
    private static final Log log = LogFactory.getLog(DemoEndpoint.class);

    @Path("/hi")
    @GET
    public Map<String, Object> sayHi() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("message", "hi world.");
        return map;
    }


    @Path("/howareyou")
    @GET
    public Map<String, Object> sayHowAreYou() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("message", "How are you?");
        return map;
    }
    
    @Path("/hello")
    @GET
    public Map<String, Object> sayHello(@Context HttpServletRequest req, @Context SecurityContext sc) {
    	/**
    	 * req.getUserPrincipal() 可以获取到当前用户
    	 * @Context SecurityContext sc 无法获取当前用户
    	 * SecurityContextHolder.getContext().getAuthentication() 可以获取到当前用户
    	 */
        HashMap<String, Object> map = new HashMap<String, Object>();
        if(req != null){
			log.trace("reqest " + (req.getUserPrincipal() == null ? " user from req is null " : req.getUserPrincipal().getName()));
			map.put("request.getUserPrincipal()", req.getUserPrincipal());
		}
		if(sc != null){
			log.trace("SC " + sc.getAuthenticationScheme() + ", " + (sc.getUserPrincipal() == null ? "user is null" : sc.getUserPrincipal().getName() ));
			map.put("SecurityContext.getAuthenticationScheme()", sc.getAuthenticationScheme());
		}
		map.put("SecurityContextHolder.getContext().getAuthentication()", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        map.put("message", "hello, world.");
        return map;
    }

    // @RolesAllowed({"ROLE_ADMIN"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @Secured("ROLE_ADMIN")
    @Path("/nihao")
    @GET
    public Map<String, Object> sayNihao(@Context SecurityContext sc) {
    	/**
    	 * @RolesAllowed, @PreAuthorize, @Secured支持哪个，要看@EnableGlobalMethodSecurity
    	 * (securedEnabled = true, prePostEnabled = true, jsr250Enabled=true) 
		 * 中的设置，可以全部都支持
    	 * 通不过方法安全验证，会抛出异常
    	 * org.springframework.security.access.AccessDeniedException: 不允许访问
    	 * 此异常默认spring-boot不在日志输出
    	 */
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("message", "ni hao.");
        return map;
    }
}
