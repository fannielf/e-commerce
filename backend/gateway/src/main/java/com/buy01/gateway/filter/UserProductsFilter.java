//package com.buy01.gateway.filter;
//
//import com.buy01.gateway.dto.ProductResponseDTO;
//import com.buy01.gateway.dto.UserResponseDTO;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.reactivestreams.Publisher;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//public class UserProductsFilter implements GlobalFilter, Ordered {
//
//    private static final Logger log = LoggerFactory.getLogger(UserProductsFilter.class);
//
//    private final WebClient webClient;
//    private final ObjectMapper objectMapper;
//
//    public UserProductsFilter(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
//        this.webClient = webClientBuilder.build();
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        if (exchange.getAttribute("responseDecorated") != null) {
//            return chain.filter(exchange);
//        }
//        exchange.getAttributes().put("responseDecorated", true);
//
//        String path = exchange.getRequest().getURI().getPath();
//        log.debug("Incoming request path: {}", path);
//
//        if (!"/api/users/me".equals(path)) {
//            log.debug("Skipping UserProductsFilter for path: {}", path);
//            return chain.filter(exchange);
//        }
//
//        log.info("UserProductsFilter triggered for /api/users/me");
//
//        ServerHttpResponse originalResponse = exchange.getResponse();
//        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
//
//        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//
//                // Status guard - only injecting successful calls
//                if (getStatusCode() == null || !getStatusCode().is2xxSuccessful()) {
//                    return super.writeWith(body);
//                }
//
//                // Content-type guard
//                String contentType = getHeaders().getFirst("Content-Type");
//                if (contentType == null || !contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
//                    return super.writeWith(body);
//                }
//
//                return DataBufferUtils.join(body)
//                        .flatMap(dataBuffer -> Mono.fromCallable(() -> {
//                            byte[] content = new byte[dataBuffer.readableByteCount()];
//                            dataBuffer.read(content);
//                            DataBufferUtils.release(dataBuffer);
//                            return objectMapper.readValue(content, UserResponseDTO.class);
//                        }).flatMap(user -> {
//
//                            if (!UserResponseDTO.Role.SELLER.equals(user.getRole())) {
//                                return Mono.fromCallable(() ->
//                                        objectMapper.writeValueAsBytes(user)
//                                ).flatMap(bytes ->
//                                        super.writeWith(Mono.just(bufferFactory.wrap(bytes))));
//                            }
//
//                            String authHeader =
//                                    exchange.getRequest().getHeaders().getFirst("Authorization");
//
//                            return webClient.get()
//                                    .uri("lb://PRODUCT-SERVICE/api/products/my-products")
//                                    .header("Authorization", authHeader)
//                                    .retrieve()
//                                    .bodyToMono(new ParameterizedTypeReference<List<ProductResponseDTO>>() {})
//                                    .onErrorReturn(List.of())   // fallback if product-service fails
//                                    .flatMap(products -> Mono.fromCallable(() -> {
//                                        user.setProducts(products);
//                                        return objectMapper.writeValueAsBytes(user);
//                                    }))
//                                    .flatMap(json -> {
//                                        getHeaders().setContentType(MediaType.APPLICATION_JSON);
//                                        getHeaders().setContentLength(json.length);
//                                        return super.writeWith(
//                                                Mono.just(bufferFactory.wrap(json)));
//                                    });
//                        }));
//            }
//
//            @Override
//            public Mono<Void> writeAndFlushWith(
//                    Publisher<? extends Publisher<? extends DataBuffer>> body) {
//
//                return writeWith(Flux.from(body).flatMap(p -> p).cache());
//            }
//
//        };
//
//        return chain.filter(exchange.mutate().response(decoratedResponse).build());
//    }
//
//    @Override
//    public int getOrder() {
//        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
//    }
//}
