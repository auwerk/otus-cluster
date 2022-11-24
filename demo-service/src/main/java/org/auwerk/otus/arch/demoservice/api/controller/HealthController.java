package org.auwerk.otus.arch.demoservice.api.controller;

import org.auwerk.otus.arch.demoservice.api.dto.ServiceResponse;
import org.auwerk.otus.arch.demoservice.api.dto.ServiceResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ServiceResponse health() {
        return ServiceResponse.builder()
                .status(ServiceResponseStatus.OK)
                .build();
    }
}
