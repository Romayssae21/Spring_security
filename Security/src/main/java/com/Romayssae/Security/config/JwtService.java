package com.Romayssae.Security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.el.lang.FunctionMapperImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY= "JOVqqJGrESUR/4UcePTpzjwaYde3qQNa38XChjTbFRe52dFAA8S6ahENFYUDr/aX";
    public String extractUsername(String token) {
        return extractedClaim(token,Claims::getSubject);
    }
    //Generic method:
    public <T> T extractedClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    //Generate token with only UserDetails:
    public String generateToken( UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    //Generate token with UserDetails and extraClaims:
    public String generateToken(
            Map<String ,Object> extraClaims,
            UserDetails userDetails
    ){

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24)) //Valid for 24h + 1000ms
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /* Make sure that the username that we have within the token is the same as the username that we have as an input
    and check the expiration Date */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        return extractedClaim(token,Claims::getExpiration);
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
