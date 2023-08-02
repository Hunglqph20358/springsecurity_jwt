package com.poly.security.jwt;

import com.poly.security.userPrincal.UserPrinciple;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;
@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret = "aasdasdasdasdasdasdqweqeqwe";

    //set thoi gian ton` tai
    private int jwtExpiration = 86400;

    //tai ra chuoi~ token
    public String createToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary(jwtSecret))
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid JWT sinature -> Message: {}",e);
        }catch (ExpiredJwtException e){
            logger.error("ExpiredJwtException the Token -> Message: {}", e);
        }catch (UnsupportedJwtException e){
            logger.error("UnsupportedJwtException Jwt Token -> Message: {}", e);
        }catch (MalformedJwtException e){
            logger.error("The token Invalid format -> Message: {}", e);
        }catch (IllegalArgumentException e){
            logger.error("Jwt clauins strin is empty -> Message: {}", e);
        }
        return false;
    }

    public String getUserNameFromToken(String token){
        String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return username;
    }
}
