package fr.aymax.RateMyManager.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import fr.aymax.RateMyManager.config.AppConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * This class provides a method to create a JWT (createToken) and a method to parse and validate a JWT and return an Authentication object (getAuthentication).
 */
@Component
public class TokenProvider 
{
	private final String secretKey;
	private final long tokenValidityInMilliseconds;

	private final UserDetailsService userService;

	public TokenProvider(AppConfig config, UserDetailsService userService) {
		this.secretKey = Base64.getEncoder().encodeToString(config.getSecret().getBytes());
		this.tokenValidityInMilliseconds = 1000 * config.getTokenValidityInSeconds();
		this.userService = userService;
	}
	
	/**
	 * The JWT is created with the Jwts builder from the jjwt library. For the signature the builder needs to know the algorithm (HMAC with SHA-512) and it needs a secret key. 
	 * The application reads this key from the application.yml file (the key has to be a base64 encoded string). The method setExpiration sets the date until when the token is valid. 
	 * The current date and time is set with the setIssuedAt method and the username is put into the subject with setSubject. The final call to compact() builds and returns the JWT as a String.
	 */
	public String createToken(String username) 
	{
		Date now = new Date();
		Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

		return Jwts.builder().setId(UUID.randomUUID().toString())
			.setSubject(username)
			.setIssuedAt(now)
			.signWith(SignatureAlgorithm.HS512, this.secretKey)
			.setExpiration(validity)
			.compact();
	}
	
	/**
	 * This method receives the JWT and parses it. 
	 * The code needs to set the same secret key as in the token creation method to check the validity of the signature. 
	 * After the parse succeeds the username is extracted and the code loads the UserDetails from the "database". 
	 * It's debatable if the application should do a database query here, because we could store all the authorities (roles) of a user in the JWT and then extract it here. 
	 * Depending on the use case and how long the JWT is valid checking the user in the database could make sense. The advantage is that we can block users and change their roles immediately.
	 */
	public Authentication getAuthentication(String token) 
	{
		//Extrait un username du token
		String username = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody().getSubject();
		//Recupere l'user de la bd (s'il n'existe pas, null)
		UserDetails userDetails = this.userService.loadUserByUsername(username);
		//Verification que l'utilisateur trouvé dans le token correspond bien à l'utilisateur qui envoie le token
		
		//Retourne un objet d'authentification ou null
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

}
