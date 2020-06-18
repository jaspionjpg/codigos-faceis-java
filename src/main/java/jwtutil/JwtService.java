package jwtutil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;


@Service
public class JwtService {
    public static final String jwtSecret = "<JWT HASH>";

    public String createToken(String string) {
        String token = Jwts.builder().setSubject(string).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

        return token;
    }

    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
