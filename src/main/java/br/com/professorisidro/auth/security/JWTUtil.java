package br.com.professorisidro.auth.security;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {
	
	private static final long EXPIRATION=5*60*1000;
	private static final String SECRET="12345678901234561234567890123456";
	
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String AUTH_HEADER = "Authorization";
	
	
	
	public static String createToken(String username) {
		
		System.out.println("USername recebido = "+username);
		
		 Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

//		String secretString = Encoders.BASE64.encode(key.getEncoded());
	 
		
		
		String jws = Jwts.builder()
					.setSubject(username)
					.setIssuer("ProfessorIsidro")
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
					.signWith(key, SignatureAlgorithm.HS256)
					.compact();					
				
		System.out.println("TOKEN GERADO = "+jws);
				
        return TOKEN_PREFIX+jws;
	}
	
	public static boolean isIssuerValid(String issuer) {
		return issuer.equals("ProfessorIsidro");
	}
	
	public static boolean isExpirationValid(Date expiration) {
		return expiration.after(new Date(System.currentTimeMillis()));
	}
	
	
	public static boolean isSubjectValid(String subject) {
		return subject != null && subject.length() > 0;
	}
	
	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(AUTH_HEADER);
		System.out.println("TOKEN RECEBIDO = "+token);
		
		token = token.replace(TOKEN_PREFIX, "");
		
		byte[] secretBytes = SECRET.getBytes();
		
		Jws<Claims> jwsClaims = Jwts.parserBuilder()
								    .setSigningKey(secretBytes)
								    .build()
								    .parseClaimsJws(token);
		
		String username = jwsClaims.getBody().getSubject();
		String issuer   = jwsClaims.getBody().getIssuer();
		Date   exp      = jwsClaims.getBody().getExpiration();
								    
		
		
		System.out.println(username + " - " + issuer + " - " + exp);
		
		if (isIssuerValid(issuer) && isExpirationValid(exp)  && isSubjectValid(username)) {
			
			System.out.println("Valid token!!");
			return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
		}
		return null;
	}
}
