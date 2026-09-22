package com.playyourlist.api.client;

import com.playyourlist.api.dto.ReproducaoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "reproducoes-service", url = "${reproducoes.service.url}")
public interface ReproducaoClient {

    @PostMapping("/reproducao")
    Object registrar(@RequestBody ReproducaoRequest request);
}
