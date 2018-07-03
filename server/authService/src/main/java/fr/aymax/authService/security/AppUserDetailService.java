package fr.aymax.authService.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import fr.aymax.authService.entity.MyUser;
import fr.aymax.authService.entity.MyUserService;

/**
 * Fetches the MyUser object with the MyUserService from the "database" and instantiates an UserDetails. The code utilizes the builder from the MyUser class to create the UserDetails instance.
 */
@Component
public class AppUserDetailService implements UserDetailsService 
{
	private final MyUserService myUserService;

	public AppUserDetailService(MyUserService myUserService) {
		this.myUserService = myUserService;
	}

	@Override
	public final UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		final MyUser user = this.myUserService.lookup(username);
		if (user == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}

		return org.springframework.security.core.userdetails.User.withUsername(username)
					.password(user.getPassword()).authorities(Collections.emptyList())
					.accountExpired(false).accountLocked(false).credentialsExpired(false)
					.disabled(false).build();
  }

}
