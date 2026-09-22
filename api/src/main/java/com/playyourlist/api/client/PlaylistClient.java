package com.playyourlist.api.client;

import com.playyourlist.api.dto.PlaylistDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "playlists-service", url = "${playlists.service.url}")
public interface PlaylistClient {

    @GetMapping("/playlists/{playlistid}")
    PlaylistDTO buscarPorId(@PathVariable("playlistid") Long playlistId);

    @PostMapping("/playlists/{playlistid}/musicas/{musicaId}")
    void adicionarMusica(@PathVariable("playlistid") Long playlistId, @PathVariable("musicaId") Long musicaId);

    @DeleteMapping("/playlists/{playlistid}/musicas/{musicaId}")
    void removerMusica(@PathVariable("playlistid") Long playlistId, @PathVariable("musicaId") Long musicaId);
}
