package com.wysiwyg.gateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author wwcc
 */
@Slf4j
public class WebExchangeUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 从ServerWebExchange的请求体解析为JsonNode
     *
     * @param exchange ServerWebExchange对象
     * @return 包含JsonNode的Mono
     */
    public static Mono<JsonNode> parseRequestBodyToJson(ServerWebExchange exchange) {
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    String bodyStr = new String(bytes, StandardCharsets.UTF_8);

                    try {
                        return Mono.just(OBJECT_MAPPER.readTree(bodyStr));
                    } catch (JsonProcessingException e) {
                        log.error("Failed to parse JSON request body", e);
                        return Mono.empty();
                    }
                });
    }

    /**
     * 从ServerWebExchange的请求体解析为指定类型
     *
     * @param exchange ServerWebExchange对象
     * @param clazz    指定解析的类型
     * @param <T>      类型参数
     * @return 包含指定类型对象的Mono
     */
    public static <T> Mono<T> parseRequestBody(ServerWebExchange exchange, Class<T> clazz) {
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    String bodyStr = new String(bytes, StandardCharsets.UTF_8);

                    try {
                        return Mono.just(OBJECT_MAPPER.readValue(bodyStr, clazz));
                    } catch (JsonProcessingException e) {
                        log.error("Failed to parse request body to {}", clazz.getSimpleName(), e);
                        return Mono.empty();
                    }
                });
    }

    /**
     * 向ServerWebExchange响应JSON数据
     *
     * @param exchange     ServerWebExchange对象
     * @param status       HTTP状态码
     * @param responseBody 响应体对象
     * @return 表示响应完成的Mono<Void>
     */
    public static Mono<Void> writeJsonResponse(ServerWebExchange exchange, HttpStatus status, Object responseBody) {
        return Mono.just(exchange.getResponse())
                .flatMap(r -> {
                    r.setStatusCode(status);
                    r.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    try {
                        DataBuffer buffer = r.bufferFactory().wrap(OBJECT_MAPPER.writeValueAsBytes(responseBody));
                        return r.writeWith(Mono.just(buffer));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                });
    }

    /**
     * 向ServerWebExchange响应错误信息
     *
     * @param exchange     ServerWebExchange对象
     * @param status       HTTP状态码
     * @param errorMessage 错误信息
     * @return 表示响应完成的Mono<Void>
     */
    public static Mono<Void> writeErrorResponse(ServerWebExchange exchange, HttpStatus status, String errorMessage) {
        return Mono.just(exchange.getResponse())
                .flatMap(r -> {
                    r.setStatusCode(status);
                    r.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                    String errorJson = String.format("{\"error\": \"%s\"}", errorMessage);
                    DataBuffer buffer = r.bufferFactory().wrap(errorJson.getBytes(StandardCharsets.UTF_8));
                    return r.writeWith(Mono.just(buffer));
                });


    }
}
