package fr.aymax.RateMyManager.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import fr.aymax.RateMyManager.RateMyManagerApplication;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is found.
 * This class will be injected into the Spring Security filter chain and every http request, that needs to be authenticated, will flow through this filter.
 */
public class JWTFilter extends GenericFilterBean 
{
	public final static String AUTHORIZATION_HEADER = "Authorization";

	private final TokenProvider tokenProvider;

	public JWTFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	
	/**
	 * The client sends the token in the Authorization header, and the first thing the code does is extracting the JWT from this header (resolveToken). 
	 * Then it calls the getAuthentication method from the TokenProvider. 
	 * This method returns either an Authentication object or null when the token is valid but the user does not exists in the database 
	 * or it throws one of several exceptions when the validation of the JWT failed. In that case the filter returns a http status code of 401.
	 * When getAuthentication returns an Authentication object the filter puts it into a thread local variable SecurityContextHolder.getContext().setAuthentication(authentication) 
	 * so other parts of the application have access to the authentication object.
	*/
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException 
	{
		try {
			//Extraction token
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			String jwt = resolveToken(httpServletRequest);
			//Si token valide
			if (jwt != null) 
			{
				//Retourne objet d'authentification ou null si l'utilisateur n'existe pas
				Authentication authentication = this.tokenProvider.getAuthentication(jwt);
				//Authentifie
				if (authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		filterChain.doFilter(servletRequest, servletResponse);
		}
		//Erreur 401 si une des verification du token echoue
		catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | UsernameNotFoundException e) 
		{
			RateMyManagerApplication.logger.info("Security exception {}", e.getMessage());
			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
	
	//Extraction du token de l'entete HTTP de forme Authorization:Bearer eyJhbGciOiJIUzUxMiJ9......
	private static String resolveToken(HttpServletRequest request) 
	{
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
