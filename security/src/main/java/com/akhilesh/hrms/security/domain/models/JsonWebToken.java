package com.akhilesh.hrms.security.domain.models;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

public class JsonWebToken {

    private static final String SECRET = "secret";
    private final String value;

    private JsonWebToken(String value) {
        this.value = value;
    }

    public static JsonWebToken fromValue(String value) {
        return new JsonWebToken(value);
    }

    public static JsonWebToken createFor(User user) {
        String token = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getUsername().value())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return fromValue(token);
    }

    public boolean isValidFor(User user) {
        if (user == null) return false;
        String username = extractUsername();
        return username.equals(user.getUsername().value()) && isNotExpired();
    }

    public Claims extractAllClaims() {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(value)
                .getBody();
    }

    public String value() {
        return value;
    }

    private <T> T extractClaim(Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims();
        return claimResolver.apply(claims);
    }

    public String extractUsername() {
        return extractClaim(Claims::getSubject);
    }

    private Date extractExpiration() {
        return extractClaim(Claims::getExpiration);
    }


    private boolean isExpired() {
        return extractExpiration().before(new Date());
    }

    private boolean isNotExpired() {
        return !isExpired();
    }

}
