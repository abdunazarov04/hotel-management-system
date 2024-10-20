package javachi.biz.hotelmanagementsystem.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${spring.security.secret.key}")
    private String secretKey;

    public String generateAccessToken(String user) {
        return Jwts.builder()
                .setSubject(user)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public String generateRefreshToken(String user) {
        return Jwts.builder()
                .setSubject(user)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public JwtParser parser() {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();
    }

    public boolean isTokenValid(String token) {
        if (!parser().isSigned(token)) return false;
        try {
            Claims body = parser()
                    .parseClaimsJws(token)
                    .getBody();

            return !StringUtils.isBlank(body.getSubject());
        } catch (Exception e) {
            return false;
        }
    }

    public <T> T getClaim(String name, String token, Class<T> type) {
        try {
            return parser()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(name, type);
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername(String subject) {
        return subject.split(",")[0].split(":")[1].trim();
    }
}
