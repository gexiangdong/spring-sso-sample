package cn.devmgr.example.sso.jwt.mvc.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import cn.devmgr.example.sso.jwt.mvc.domain.SecurityUser;
import cn.devmgr.example.sso.jwt.mvc.domain.User;

@Component
public class JWTUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User u = new User();
		u.setUsername(username);
		SecurityUser su = new SecurityUser(u, null);
		return su;
	}

}
