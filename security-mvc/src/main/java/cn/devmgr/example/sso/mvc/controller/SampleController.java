package cn.devmgr.example.sso.mvc.controller;

import java.security.Principal;
import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class SampleController {
	private static final Log log = LogFactory.getLog(SampleController.class);
	
	
    @RequestMapping("/userinfo")
    public String userInfo(@RequestParam(value="name", required=false, defaultValue="World") String name, 
    		Model model, Principal principal) {
        model.addAttribute("name", name);
        if (principal != null) {
            //获得定制的信息
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            Authentication authentication = oAuth2Authentication.getUserAuthentication();
            @SuppressWarnings("unchecked")
            LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) authentication.getDetails();
            if(log.isTraceEnabled()){
            	log.trace("\r\ndetails = " + details);
            }
            //用户信息
            @SuppressWarnings("unchecked")
            LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) details.get("principal");
            model.addAttribute("email", user.get("email"));
        }
        return "userinfo";
    }

    @RequestMapping("/")
    public String index(){
    	return "index";
    }
}
