package com.playyourlist.api.client;

import com.playyourlist.api.dto.MusicaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "musicas-service", url = "${musicas.service.url}")
public interface MusicaClient {

    @GetMapping("/musicas/{id}")
    MusicaDTO buscarPorId(@PathVariable("id") Long id);
}
