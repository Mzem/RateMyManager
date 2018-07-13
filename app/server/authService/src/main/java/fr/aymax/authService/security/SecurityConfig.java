package fr.aymax.authService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.aymax.authService.security.jwt.JWTConfigurer;
import fr.aymax.authService.security.jwt.TokenProvider;

/**
 * Configure what endpoints are secured and which ones are public.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
	private final TokenProvider tokenProvider;

	public SecurityConfig(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {	
		return super.authenticationManagerBean();
	}
	
	/**
	 * The configuration disables CSRF protection and enables CORS handling. 
	 * Then it sets the session creation policy to STATELESS that prevents Spring Security from creating HttpSession objects. 
	 * The http endpoints /signup, /login and /public are accessible without an authentication. 
	 * All the other endpoints will be secured and require a valid JWT token. 
	 * At the end the JWTConfigurer helper class injects the JWTFilter into the Spring Security filter chain.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
		http
		  .csrf()
		    .disable()
		  .cors()
		    .and()
		  .sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
		//.httpBasic() // optional, if you want to access 
		//  .and()     // the services from a browser
		  .authorizeRequests()
		    .antMatchers("/signup").permitAll()
		    .antMatchers("/login").permitAll()
		    .antMatchers("/public").permitAll()
		    .anyRequest().authenticated()
		    .and()
		  .apply(new JWTConfigurer(this.tokenProvider));
		// @formatter:on
	}
	


}
