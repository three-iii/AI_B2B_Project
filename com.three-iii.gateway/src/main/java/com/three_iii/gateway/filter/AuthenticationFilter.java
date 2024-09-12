package com.three_iii.gateway.filter;

import static com.three_iii.gateway.exception.AuthErrorCode.INVALID_SIGNATURE;

import com.three_iii.gateway.exception.AuthErrorCode;
import com.three_iii.gateway.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (path.equals("/api/auth/sign-up") || path.equals("/api/auth/sign-in")
            || path.contains("/v3/api-docs")) {
            return chain.filter(exchange);
        }

        String token = extractToken(exchange);
        Claims claims = validateToken(token);

        if (token == null || claims.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 응답을 완료하여 요청을 더 이상 처리하지 않도록
            return exchange.getResponse().setComplete();
        }
        String id = (String) claims.get("id");
        String username = (String) claims.get("username");
        String role = (String) claims.get("role");

        exchange.getRequest().mutate()
            .header("X-User-Id", id)
            .header("X-User-Name", username)
            .header("X-User-Role", role)
            .build();

        // 토큰이 유효한 경우, 요청을 다음 필터나 라우트 핸들러로 전달
        return chain.filter(exchange);
    }

    // JWT 토큰 추출
    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    // 토큰 유효성 검사
    private Claims validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        try {
            return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (WeakKeyException | NullPointerException weakKeyException) {
            throw new AuthException(INVALID_SIGNATURE);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new AuthException(AuthErrorCode.TOKEN_IS_EXPIRED);
        } catch (MalformedJwtException malformedJwtException) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        } catch (Exception unexpectedException) {
            throw new RuntimeException(unexpectedException);
        }
    }

}
