package fr.aymax.RateMyManager.security;

import java.util.List;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import fr.aymax.RateMyManager.entity.MyUser;
import fr.aymax.RateMyManager.entity.Profile;
import fr.aymax.RateMyManager.service.MyUserService;

/**
 * Fetches the MyUser object with the MyUserService from the "database" and instantiates an UserDetails. The code utilizes the builder from the User class to create the UserDetails instance.
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
		final MyUser loginUser = this.myUserService.lookup(username);
		if (loginUser == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}
		//Load user profiles
		List<GrantedAuthority> userRoles = new ArrayList<GrantedAuthority>();
		List<Profile> userProfiles = loginUser.getProfiles();
		for (Profile profile : userProfiles)
			userRoles.add(new SimpleGrantedAuthority(profile.getRole()));
		
		return org.springframework.security.core.userdetails.User.withUsername(username)
					.password(loginUser.getPassword()).authorities(userRoles)
					.accountExpired(false).accountLocked(false).credentialsExpired(false)
					.disabled(false).build();
  }

}
