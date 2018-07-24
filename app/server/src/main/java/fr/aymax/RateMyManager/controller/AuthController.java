package fr.aymax.RateMyManager.controller;

import java .util.List;
import java .util.HashMap;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.*;

import fr.aymax.RateMyManager.RateMyManagerApplication;
import fr.aymax.RateMyManager.entity.MyUser;
import fr.aymax.RateMyManager.entity.Profile;
import fr.aymax.RateMyManager.service.MyUserService;
import fr.aymax.RateMyManager.security.jwt.TokenProvider;

/* Remarque JWT :
 * Un utilisateur avec un JWT valide peut acceder à n'importe quel endpoint securisé car le JWTFilter ne fait que verif que l'user du token existe dans la bd
 * 2 cas vérification doivent etre faites sur certains controllers :
 * 		- Endpoints resevés à certains roles : vérifier le role envoyé avec celui permis
 * 		- Endpoints reservés à certains utilisateurs : verifier l'username envoyé de l'utilisateur avec celui du token
 * Pas fait sur update password car cela impose que l'user + son mdp soit connu donc trivial
 * Mais n'empeche que dérober le JWT + le nom d'utilisateur correpondant est une faille
 */


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
	public String loginUser(@Valid @RequestBody MyUser loginUser, HttpServletResponse response) 
	{
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword());

		try {
			this.authenticationManager.authenticate(authenticationToken);
			
			try {
				//If the user can be authentified but his role in the db doesn't match the chosen role, we throw an exception
				String loginUserRole = loginUser.getProfiles().get(0).getRole();
				List<Profile> dbUserProfiles = this.userService.lookup(loginUser.getUsername()).getProfiles();
				
				boolean roleOk = false;
				for (Profile dbUserProfile : dbUserProfiles)
					if ( dbUserProfile.getRole().equals(loginUserRole) || dbUserProfile.getRole().equals("ROLE_ADMIN") ) {
						roleOk = true;
						break;
					}
				if (!roleOk)
					throw new AuthenticationServiceException("User role not matching");
			} catch (NullPointerException e) {
				RateMyManagerApplication.logger.info("Security exception : User profile not specified");
				return null;
			}
			//When the authentication is successful the method creates a JWT and returns it to the client
			return this.tokenProvider.createToken(loginUser.getUsername());
		}
		catch (AuthenticationException e) {
			RateMyManagerApplication.logger.info("Security exception {}", e.getMessage());
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
	
	@PutMapping("/updatePassword")
	public String signupUser(@RequestBody String jsonUpdateInfo) 
	{
		try {
			HashMap<String, Object> updateInfo = new ObjectMapper().readValue(jsonUpdateInfo, HashMap.class);
			
			MyUser updateUser = this.userService.lookup(updateInfo.get("username").toString());
			if (updateUser != null) 
			{
				//Comparaison des anciens mdp
				if ( this.passwordEncoder.matches(updateInfo.get("password").toString(), updateUser.getPassword()) ) 
				{
					updateUser.setPassword(updateInfo.get("newPassword").toString());
					updateUser.encodePassword(this.passwordEncoder);
					this.userService.save(updateUser);
					return "NEW_PASSWORD";
				}
				return "BAD_PASSWORD";
			}
			return "BAD_USER";
		} catch (IOException e) {
			e.printStackTrace();
			return "BAD_UPDATE_DATA";
		}
	}

}
