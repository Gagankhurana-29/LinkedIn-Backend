package com.codingshuttle.linkedin.ApiGateway.filters;

import com.codingshuttle.linkedin.ApiGateway.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationGatewayFilter extends AbstractGatewayFilterFactory<AuthenticationGatewayFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationGatewayFilter.class);
    private final JwtService jwtService;

    public AuthenticationGatewayFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService ;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Authentication logic can be added here

            log.info("Gateway filter request intercepted:{}",exchange.getRequest().getURI());

            final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(tokenHeader == null || !tokenHeader.startsWith("Bearer "))
            {
                log.info("Request not authorized");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String tokenId = tokenHeader.split("Bearer ")[1];

            String userId = jwtService.getUserIdFromToken(tokenId).toString();

            exchange.mutate().request(r->r.header(("X-User-Id"),userId)).build();

            return chain.filter(exchange);
        };
    }

    public static class Config{
    }
}
