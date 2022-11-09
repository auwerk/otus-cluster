package org.auwerk.otus.arch.demoservice.api;

import org.auwerk.otus.arch.demoservice.dto.ServiceResponse;
import org.auwerk.otus.arch.demoservice.dto.ServiceResponseStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HealthHandler {
    
    public Mono<ServerResponse> health(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ServiceResponse.builder()
                        .status(ServiceResponseStatus.OK).build()));
    }
}
