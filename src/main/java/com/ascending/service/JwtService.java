package com.ascending.service;

import com.ascending.model.Employee;
import com.ascending.model.Role;
import com.ascending.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final String SECRET_KEY = System.getProperty("secret.key");
    private static final String ISSUER = "com.ascending";
    private final long EXPIRATION_TIME = 86400 * 1000;

//    public String generateToken(User user){
//
//        //JWT signature algorithm using to sign the token
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
////        //Sign JWT with SECRET_KEY
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
////
//        Claims claims = Jwts.claims();
//        claims.setId(String.valueOf(user.getId()));
//        claims.setIssuedAt(new Date(System.currentTimeMillis()));
//        claims.setIssuer(ISSUER);
//        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
//        //Set the JWT Claims
//        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);
//        //Builds the JWT and serializes it to a compact, URL-safe string
//        return builder.compact();
//    }

//    public Claims decryptJwtToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
//                .parseClaimsJws(token).getBody();
//        logger.debug("Claims: " + claims.toString());
//        return claims;
//    }

    public Map<String, Object> generateToken(User user) {
        Map<String, Object> token = new HashMap<>();
        //JWT signature algorithm using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //Sign JWT with SECRET_KEY
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        Claims claims = Jwts.claims();
        claims.setId(String.valueOf(user.getId()));
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
        List<Role> roles = user.getRole();
        String allowedReadResources = "";
        String allowedCreateResources = "";
        String allowedUpdateResources = "";
        String allowedDeleteResources = "";
        //String allowedResource = roles.stream().map(role -> role.getAllowedResource()).collect(Collectors.joining(","));
        //claims.put("allowedResource", allowedResource);
        for (Role role : roles) {
            if (role.isAllowedRead()) allowedReadResources = String.join(role.getAllowedResource(), allowedReadResources, ",");
            if (role.isAllowedCreate()) allowedCreateResources = String.join(role.getAllowedResource(), allowedCreateResources, ",");
            if (role.isAllowedUpdate()) allowedUpdateResources = String.join(role.getAllowedResource(), allowedUpdateResources, ",");
            if (role.isAllowedDelete()) allowedDeleteResources = String.join(role.getAllowedResource(), allowedDeleteResources, ",");
        }
        claims.put("allowedReadResources", allowedReadResources.replaceAll(".$", ""));
        claims.put("allowedCreateResources", allowedCreateResources.replaceAll(".$", ""));
        claims.put("allowedUpdateResources", allowedUpdateResources.replaceAll(".$", ""));
        claims.put("allowedDeleteResources", allowedDeleteResources.replaceAll(".$", ""));
//
//        //Set the JWT Claims
        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);
        //Builds the JWT and serializes it to a compact, URL-safe string
        token.put("token",builder.compact());
        return token;
    }

    public Claims decryptJwtToken(Map<String,Object> token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token.get("token").toString()).getBody();
        logger.debug("Claims: " + claims.toString());
        return claims;
    }

}
