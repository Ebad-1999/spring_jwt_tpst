package com.tpst.security;

import com.tpst.security.service.UserDetailsImpl;
import com.tpst.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private String jwtSecretKey = "sboot";

    private long jwtExpiration = 86400000;

    public String createToken(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().
                setSubject(userDetails.getUsername()).
                setIssuedAt(new Date()).  // created Time
                        setExpiration(new Date(new Date().getTime()+jwtExpiration)).  //setting life cycle
                        signWith(SignatureAlgorithm.HS512, jwtSecretKey). //
                        compact();

    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJwt(token);
            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.
                parser().
                setSigningKey(jwtSecretKey).
                parseClaimsJws(token).
                getBody().
                getSubject();
    }


}
