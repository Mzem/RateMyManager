package fr.aymax.authService.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.aymax.authService.AuthServiceApplication;
import fr.aymax.authService.entity.MyUser;
import fr.aymax.authService.entity.MyUserService;
import fr.aymax.authService.security.jwt.TokenProvider;

@RestController
@CrossOrigin
public class AuthController 
{
	private final PasswordEncoder passwordEncoder;
	private final MyUserService userService;
	private final TokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;

	public AuthController(PasswordEncoder passwordEncoder, MyUserService userService, TokenProvider tokenProvider, AuthenticationManager authenticationManager) 
	{
		this.userService = userService;
		this.tokenProvider = tokenProvider;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	@GetMapping("/authenticate")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void authenticate() {
		// we don't have to do anything here
		// this is just a secure endpoint and the JWTFilter
		// validates the token
		// this service is called at startup of the app to check
		// if the jwt token is still valid
	}

	@PostMapping("/login")
	public String loginUser(@Valid @RequestBody MyUser activeUser, HttpServletResponse response) 
	{
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(activeUser.getUsername(), activeUser.getPassword());

		try {
			this.authenticationManager.authenticate(authenticationToken);
			//When the authentication is successful the method creates a JWT and returns it to the client
			return this.tokenProvider.createToken(activeUser.getUsername());
		}
		catch (AuthenticationException e) {
			AuthServiceApplication.logger.info("Security exception {}", e.getMessage());
			//When username and/or password are not valid this call throws an exception and the method returns the status code 401
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
	}

	@PostMapping("/signup")
	public String signupUser(@RequestBody MyUser newUser) 
	{
		if (this.userService.usernameExists(newUser.getUsername())) {
			return "EXISTS";
		}
		newUser.encodePassword(this.passwordEncoder);
		this.userService.save(newUser);
		return this.tokenProvider.createToken(newUser.getUsername());
	}

}
