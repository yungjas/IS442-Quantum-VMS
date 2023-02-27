// package com.quantum.backend.auth.jwt;

// import java.nio.charset.Charset;
// import java.security.Key;
// import java.util.Date;
// import java.util.Random;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Component;

// import com.quantum.backend.auth.service.UserDetailsImpl;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;


// @Component
// // for generating JWT, getting username from JWT and validating 
// public class JwtUtils {
//     private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

// 	@Value("${jwt.secret}")
// 	private String jwtSecret;

// 	@Value("${jwt.expirationms}")
// 	private int jwtExpirationMs;

// 	private Key getSigningKey() {
// 		// byte[] secret = new byte[576];
// 		// new Random().nextBytes(secret);
// 		// String jwtSecret = new String(secret, Charset.forName("UTF-8"));
// 		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
// 		return Keys.hmacShaKeyFor(keyBytes);
// 	}
	  
// 	public String generateJwtToken(Authentication authentication) {
// 		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
// 		return Jwts.builder()
// 				.setSubject((userPrincipal.getUsername()))
// 				.setIssuedAt(new Date())
// 				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
// 				.signWith(getSigningKey(), SignatureAlgorithm.HS512)
// 				//.signWith(SignatureAlgorithm.HS512, jwtSecret)
// 				.compact();
// 	}

// 	public String getUserNameFromJwtToken(String token) {
// 		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
// 	}

// 	public boolean validateJwtToken(String authToken) {
// 		try {
// 			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
// 			return true;
// 		} catch (SecurityException e) {
// 			logger.error("Invalid JWT signature: {}", e.getMessage());
// 		} catch (MalformedJwtException e) {
// 			logger.error("Invalid JWT token: {}", e.getMessage());
// 		} catch (ExpiredJwtException e) {
// 			logger.error("JWT token is expired: {}", e.getMessage());
// 		} catch (UnsupportedJwtException e) {
// 			logger.error("JWT token is unsupported: {}", e.getMessage());
// 		} catch (IllegalArgumentException e) {
// 			logger.error("JWT claims string is empty: {}", e.getMessage());
// 		}

// 		return false;
// 	}
// }
