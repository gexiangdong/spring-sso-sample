package cn.devmgr.example.sso.jwt.mvc.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import cn.devmgr.example.sso.jwt.mvc.domain.SecurityUser;

public class JWTUserAuthenticationConverter implements
		UserAuthenticationConverter {
	private final static Log log = LogFactory.getLog(JWTUserAuthenticationConverter.class);
	
	@Override
	public Map<String, ?> convertUserAuthentication(Authentication auth) {
		throw new RuntimeException("This method is not implemented yet.");
	}

	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		if(log.isTraceEnabled()){
			log.trace("################JWTUserAuthenticationConverter.extractAuthentication()#################");
			log.trace(map);
			/**
			 * something like below.
			 * {phone=null, user_name=admin, scope=[user_info], headImage=null, 
			 * name=System Administrator, exp=1501020965, userId=1, 
			 * authorities=[{authority=all}, {authority=everything}], 
			 * jti=40358cbd-0d55-4bb2-9cc8-22aa42a5335c, 
			 * email=someone@somewhere.com, client_id=rs1}
			 */
			log.trace("################END#################");
		}
		
		SecurityUser su = new SecurityUser();
		
		su.setUsername((String) map.get(USERNAME));
		su.setName((String) map.get("name"));
		if(map.containsKey("phone")){
			su.setPhone((String) map.get("phone")); 
		}
		if(map.containsKey("userId")){
			su.setId(((Integer) map.get("userId")).intValue()); 
		}
		if(map.containsKey("headImage")){
			su.setHeadImage((String) map.get("headImage")); 
		}
		if(map.containsKey("email")){
			su.setEmail((String) map.get("email")); 
		}
		Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
		for(GrantedAuthority ga : authorities){
			su.getAuthorities().add(ga);
		}
		UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(su, "N/A", authorities);
		upa.setDetails(map);
		return upa;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		if (!map.containsKey(AUTHORITIES)) {
			return new ArrayList<GrantedAuthority>();
		}
		Object authorities = map.get(AUTHORITIES);
		if (authorities instanceof String) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
		}
		if (authorities instanceof Collection) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
					.collectionToCommaDelimitedString((Collection<?>) authorities));
		}
		throw new IllegalArgumentException("Authorities must be either a String or a Collection");
	}
}
