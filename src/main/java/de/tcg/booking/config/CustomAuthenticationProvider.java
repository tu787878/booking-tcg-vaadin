package de.tcg.booking.config;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import de.tcg.booking.entity.User;
import de.tcg.booking.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService m_userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		Optional<User> optional = m_userService.findUserByUsername(name);

		if (optional.isPresent() && optional.get().getPassword().equals(password)) {

			// use the credentials
			// and authenticate against the third-party system
			return new UsernamePasswordAuthenticationToken(name, password,
					Arrays.asList(new SimpleGrantedAuthority(optional.get().getRole().toString())));
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}