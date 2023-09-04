package com.Romayssae.Security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {
    private static final String SECRET_KEY= "JOVqqJGrESUR/4UcePTpzjwaYde3qQNa38XChjTbFRe52dFAA8S6ahENFYUDr/aX";
    public String extractUserName(String token) {
        return null;
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                //set the signing key used to verify the signature of the JWT
                .setSigningKey(getSigningKey())
                .build() //parser instance ready for use
                .parseClaimsJws(token) //verifies the "token" signature
                //return a Claims object that contains the decoded JSON claims.
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
