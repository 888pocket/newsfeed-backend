package com.joosangah.apigateway.filter;

import com.joosangah.apigateway.payload.TokenUser;
import com.joosangah.apigateway.util.JwtUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationGatewayFilterFactory extends
        AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

    private static final String ROLE_KEY = "role";

    private final JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public JwtAuthenticationGatewayFilterFactory(JwtUtils jwtUtils) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(ROLE_KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String path = request.getPath().toString();

            logger.error(path);

            // 비로그인 상태에서 접근 가능한 API인 경우 허용
            if (path.contains("/public/")) {
                return chain.filter(exchange);
            }

            // 내부 API 접근 거부
            if (path.contains("/internal/")) {
                return onError(response, "access denied", HttpStatus.BAD_REQUEST);
            }

            if (!containsAuthorization(request)) {
                return onError(response, "missing authorization header", HttpStatus.BAD_REQUEST);
            }

            String token = extractToken(request);
            if (!jwtUtils.validateJwtToken(token)) {
                return onError(response, "invalid authorization header", HttpStatus.BAD_REQUEST);
            }

            TokenUser tokenUser = jwtUtils.decode(token);
            if (!hasRole(tokenUser, config.role)) {
                return onError(response, "invalid role", HttpStatus.FORBIDDEN);
            }

            addAuthorizationHeaders(request, tokenUser);

            return chain.filter(exchange);
        };
    }

    private boolean containsAuthorization(ServerHttpRequest request) {
        return request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private String extractToken(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0).substring(7);
    }

    private boolean hasRole(TokenUser tokenUser, String role) {
        return tokenUser.getRole().equals(role);
    }

    private void addAuthorizationHeaders(ServerHttpRequest request, TokenUser tokenUser) {
        request.mutate()
                .header("X-Authorization-Id", tokenUser.getId())
                .header("X-Authorization-Role", tokenUser.getRole())
                .build();
    }

    private Mono<Void> onError(ServerHttpResponse response, String message, HttpStatus status) {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        response.setStatusCode(status);
        response.getHeaders().add("Content-Type", "application/json");
        String errorMessage = String.format("{\"message\":\"[ERROR] %s\","
                        + "\"status\":\"%s\","
                        + "\"error\":\"%d\","
                        + "\"timestamp\":\"%s\""
                        + "}", message, status.getReasonPhrase(), status.value(),
                timestamp.format(formatter));
        DataBuffer buffer = response.bufferFactory().wrap(errorMessage.getBytes());
        return response.writeWith(Mono.just(buffer));
    }

    @Setter
    public static class Config {

        private String role;
    }
}
