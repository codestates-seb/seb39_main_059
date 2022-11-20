package com.twentyfour_seven.catvillage.security.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenizer {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60;    // 액세스 토큰 만료 시간 : 1시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 리프레쉬 토큰 만료 시간 : 7일
    private static final String ISSUER = "catVillage";

    private final Key key;

    public JwtTokenizer(@Value("${jwt.secret}") String secretKey) {
        this.key = getKeyFromBase64EncodedKey(secretKey);
    }

    // password를 byte를 변환하고 base64로 인코딩
    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // JWT signature에 사용할 seceret Key 생성
    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

    // accessToken 생성
    public String generateAccessToken(Authentication authentication) {
        // claim 생성
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Map<String, String> claims = new HashMap<>();
        claims.put("auth", authorities);

        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .setIssuer(ISSUER)  // "iss": "catVillage"
                .setHeaderParam("typ", "ATK")   // Header "typ": "ATK"
                .setClaims(claims)  // JWT에 담는 body
                .setSubject(authentication.getName())    // JWT 제목 payload "sub": "email"
                .setIssuedAt(Calendar.getInstance().getTime())  // JWT 발행일자 payload "iat": "발행일자"
                .setExpiration(accessTokenExpiresIn)  // 만료일자 payload "exp": "발행시간 + 1시간"
                .signWith(key)  // signature header "alg": "HS512"
                .compact(); // 생성
    }

    // refreshToken 생성
    public String generateRefreshToken(Authentication authentication) {

        long now = (new Date()).getTime();

        return Jwts.builder()
                .setIssuer(ISSUER)  // "iss": "catVillage"
                .setHeaderParam("typ", "RTK") // Header "typ": "RTK"
                .setSubject(authentication.getName())    // JWT 제목 payload "sub": "email"
                .setIssuedAt(Calendar.getInstance().getTime())  // JWT 발행일자 payload "iat": "발행일자"
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))  // 만료일자 payload "exp": "발행시간 + 7일"
                .signWith(key)  // signature header "alg": "HS512"
                .compact(); // 생성
    }


    // JWT 검증
    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    // 엑세스 토큰에서 인증정보 가져오기
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 리프레쉬 토큰에서 유저 정보(email) 가져오기
    public String getUserEmail(String refreshToken) {
        return parseClaims(refreshToken).getSubject();
    }

    // 토큰 정보를 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // Refresh Token 인지 검증
    public boolean validateRefreshToken(String token) {
        String tokenType = Jwts.parserBuilder().setSigningKey(key).build().parse(token).getHeader().getType();
        if (!tokenType.equals("RTK")) {
            return false;
        }
        return true;
    }

    // 만료된 토큰이어도 정보를 꺼내는 로직
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
