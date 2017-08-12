package cn.devmgr.example.sso.jwt.mvc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.devmgr.example.sso.jwt.mvc.domain.SecurityUser;



@Controller
public class SampleController {
	private static final Log log = LogFactory.getLog(SampleController.class);
	


    @RequestMapping("/userinfo")
    public String userInfo(@RequestParam(value="name", required=false, defaultValue="World") String name, 
    		Model model, OAuth2Authentication oAuth2Authentication) {
    	if(log.isTraceEnabled()){
    		log.trace("userInfo...." + oAuth2Authentication);
    	}
        model.addAttribute("name", name);
        if (oAuth2Authentication != null) {
            Authentication authentication = oAuth2Authentication.getUserAuthentication();
            
            if(log.isTraceEnabled()){
            	/**
            	 * oAuth2Authentication: 
            	 * -org.springframework.security.oauth2.provider.OAuth2Authentication@fb976836: 
            	 * Principal: cn.devmgr.example.sso.mvc.domain.SecurityUser@536457a3; 
            	 * Credentials: [PROTECTED]; Authenticated: true; 
            	 * Details: remoteAddress=0:0:0:0:0:0:0:1, sessionId=<SESSION>, 
            	 * tokenType=bearertokenValue=<TOKEN>; 
            	 * Granted Authorities: {authority=all}, {authority=everything}
            	 * 
            	 * authentication: (JWTUserAuthenticationConverter.extractAuthentication的输出)
            	 * org.springframework.security.authentication.UsernamePasswordAuthenticationToken@230bb906: 
            	 * Principal: cn.devmgr.example.sso.mvc.domain.SecurityUser@536457a3; 
            	 * Credentials: [PROTECTED]; 
            	 * Authenticated: true; 
            	 * Details: {phone=null, user_name=admin, scope=[user_info], headImage=null, 
            	 * 			name=System Administrator, exp=1501076280, userId=1, 
            	 * 			authorities=[{authority=all}, {authority=everything}], 
            	 * 			jti=1a2cb56d-f2c1-4f2f-8400-1d4ffaafb96d, 
            	 * 			email=someone@somewhere.com, client_id=rs1}; 
            	 * Granted Authorities: {authority=all}, {authority=everything}
            	 */
	            log.trace("\r\n\r\n========authentication======\r\n" + authentication + "\r\n=============");
	            log.trace("--oAuth2Authentication---" + oAuth2Authentication + "\r\n");
            }
            
            String email = null;
            
            if(oAuth2Authentication.getPrincipal() instanceof SecurityUser){
            	email = ((SecurityUser) oAuth2Authentication.getPrincipal()).getEmail();
            }
            
            model.addAttribute("email", email);
        }
        return "userinfo";
    }

    @RequestMapping("/")
    public String index(){
    	return "index";
    }
}
