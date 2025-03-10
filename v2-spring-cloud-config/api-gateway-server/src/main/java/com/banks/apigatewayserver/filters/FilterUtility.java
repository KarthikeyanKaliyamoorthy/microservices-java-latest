package com.banks.apigatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {

public static final String CORRELATION_ID = "eazy-bank-correlation-id";

public String getCorrelationId(HttpHeaders headers) {

    if(headers.get(CORRELATION_ID) != null) {
        List<String> correlationIdList = headers.get(CORRELATION_ID);
        return correlationIdList.stream().findFirst().get();
    }else {
        return null;
    }
}

public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
    return exchange.mutate().request(exchange.getRequest().mutate().header(name,value).build()).build();
}


public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
    return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
}

}
