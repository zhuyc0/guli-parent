package com.atguigu.guli.infrastructure.apigateway.filter;

import com.atguigu.guli.common.base.util.JwtUtils;
import com.google.gson.JsonObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月11日
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    public static final String AUTH_URL = "/api/**/auth/**";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        AntPathMatcher matcher = new AntPathMatcher();
        if (matcher.match(AUTH_URL, path)) {
            List<String> token = request.getHeaders().get("token");

            if (CollectionUtils.isEmpty(token)){
                return out(exchange.getResponse());
            }
            // token校验失败
            boolean isCheck = JwtUtils.checkToken(token.get(0));
            if(!isCheck) {
                return out(exchange.getResponse());
            }
        }
        // 放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {
        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", 28004);
        message.addProperty("data", "");
        message.addProperty("message", "鉴权失败");
        byte[] bytes = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        // 指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        // 输出http响应
        return response.writeWith(Mono.just(buffer));
    }
}
