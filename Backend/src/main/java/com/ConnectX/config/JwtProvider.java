package com.ConnectX.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
	private static SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public static String generateToken(Authentication auth) {
		String jwt=Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+86400000))
				.claim("email", auth.getName())
				.signWith(key)
			     .compact();
		return jwt;
		
	}
	public static String getEmailFromJwtToken(String jwt) {
	    jwt = jwt.substring(7);
	    
	    Claims claims = Jwts.parser()
	            .verifyWith(key)               // Replaces setSigningKey(key)
	            .build()
	            .parseSignedClaims(jwt)        // Replaces parseClaimsJws(jwt)
	            .getPayload();                 // Replaces getBody()
	            
	    return claims.get("email", String.class); // Strongly-typed extraction
	}
}
