package com.itartisan.gateway.filter;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.itartisan.common.core.constant.CacheConstants;
//import com.itartisan.common.core.domain.R;
//import com.itartisan.common.core.utils.StringUtils;
//import com.itartisan.gateway.config.properties.IgnoreWhiteProperties;
//import org.apache.logging.log4j.util.Strings;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.annotation.Resource;
//
///**
// * 网关鉴权
// */
//@Component
//public class AuthFilter implements GlobalFilter, Ordered {
//    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
//
//    // 排除过滤的uri地址
//    @Autowired
//    private IgnoreWhiteProperties ignoreWhite;
//
//    @Resource(name = "stringRedisTemplate")
//    private ValueOperations<String, String> sops;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String url = exchange.getRequest().getURI().getPath();
//        // 跳过不需要验证的路径
//        if (StringUtils.matches(url, ignoreWhite.getWhites())) {
//            return chain.filter(exchange);
//        }
//
//        String token = getToken(exchange.getRequest());
//        if (Strings.isBlank(token)) {
//            return setUnauthorizedResponse(exchange, "令牌不能为空");
//        }
//
//        String userStr = sops.get(CacheConstants.LOGIN_TOKEN_KEY + token);
////        if (Strings.isNull(userStr))
//        if (Strings.isBlank(userStr)) {
//            return setUnauthorizedResponse(exchange, "令牌验证失败");
//        }
//
//        JSONObject obj = JSONObject.parseObject(userStr);
//        String userid = obj.getString("userid");
//        String username = obj.getString("username");
//        if (Strings.isBlank(userid) || Strings.isBlank(username)) {
//            return setUnauthorizedResponse(exchange, "令牌验证失败");
//        }
//
//        // 设置用户信息到请求
//        ServerHttpRequest mutableReq = exchange.getRequest().mutate().header(CacheConstants.DETAILS_USER_ID, userid)
//                .header(CacheConstants.DETAILS_USERNAME, username).build();
//        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
//
//        return chain.filter(mutableExchange);
//    }
//
//    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//        response.setStatusCode(HttpStatus.OK);
//
//        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
//
//        return response.writeWith(Mono.fromSupplier(() -> {
//            DataBufferFactory bufferFactory = response.bufferFactory();
//            return bufferFactory.wrap(JSON.toJSONBytes(R.fail(msg)));
//        }));
//    }
//
//    /**
//     * 获取请求token
//     */
//    private String getToken(ServerHttpRequest request) {
//        String token = request.getHeaders().getFirst(CacheConstants.HEADER);
//        if (StringUtils.isNotEmpty(token) && token.startsWith(CacheConstants.TOKEN_PREFIX)) {
//            token = token.replace(CacheConstants.TOKEN_PREFIX, "");
//        }
//        return token;
//    }
//
//    @Override
//    public int getOrder() {
//        return -200;
//    }
//}