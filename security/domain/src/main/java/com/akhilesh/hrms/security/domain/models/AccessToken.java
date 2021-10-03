package com.akhilesh.hrms.security.domain.models;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;


public class AccessToken {

    private static final Algorithm algorithm = Algorithm.HMAC256("secret");
    private static final JWTVerifier jwtVerifier = JWT.require(algorithm)
            .withIssuer("auth0")
            .build();
    private final String jwt;


    private AccessToken(String value) {
        this.jwt = value;
    }

    public static AccessToken fromValue(String value) {
        return new AccessToken(value);
    }

    public static AccessToken createFor(User user) {
        String token = JWT.create().withSubject(user.getUsername().value())
                .withIssuedAt(new Date())
                .withIssuer("auth0")
                .sign(algorithm);
        return fromValue(token);
    }

    public boolean isValidFor(User user) {
        if (user == null) return false;
        Username username = extractUsername();
        return username.equals(user.getUsername()) && isNotExpired();
    }

    public String value() {
        return jwt;
    }

    public Username extractUsername() {
        DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
        String subject = decodedJWT.getSubject();
        return new Username(subject);

    }

    private Date extractExpiration() {
        DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
        return decodedJWT.getExpiresAt();
    }


    private boolean isExpired() {
        return extractExpiration().before(new Date());
    }

    private boolean isNotExpired() {
        return !isExpired();
    }

}
