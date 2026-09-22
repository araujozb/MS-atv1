package com.playyourlist.api.controller;

import com.playyourlist.api.client.MusicaClient;
import com.playyourlist.api.client.PlaylistClient;
import com.playyourlist.api.client.ReproducaoClient;
import com.playyourlist.api.dto.MusicaDTO;
import com.playyourlist.api.dto.PlaylistDTO;
import com.playyourlist.api.dto.ReproducaoRequest;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final MusicaClient musicaClient;
    private final PlaylistClient playlistClient;
    private final ReproducaoClient reproducaoClient;

    public ApiController(MusicaClient musicaClient, PlaylistClient playlistClient, ReproducaoClient reproducaoClient) {
        this.musicaClient = musicaClient;
        this.playlistClient = playlistClient;
        this.reproducaoClient = reproducaoClient;
    }

    @PostMapping("/adicionar/{playlistId}/musicas/{musicaId}")
    public ResponseEntity<String> adicionarMusicaNaPlaylist(@PathVariable Long playlistId, @PathVariable Long musicaId) {
        // Valida os dois recursos via Open Feign antes de vincular
        MusicaDTO musica;
        try {
            musica = musicaClient.buscarPorId(musicaId);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(404).body("Música com id " + musicaId + " não encontrada");
        }

        PlaylistDTO playlist;
        try {
            playlist = playlistClient.buscarPorId(playlistId);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(404).body("Playlist com id " + playlistId + " não encontrada");
        }

        playlistClient.adicionarMusica(playlistId, musicaId);

        String mensagem = String.format("Música %s adicionada com sucesso à playlist %s",
                musica.getTitulo(), playlist.getNome());
        return ResponseEntity.ok(mensagem);
    }

    @PutMapping("/executar/{playlistId}")
    public ResponseEntity<String> executarPlaylist(@PathVariable Long playlistId) {
        PlaylistDTO playlist;
        try {
            playlist = playlistClient.buscarPorId(playlistId);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(404).body("Playlist com id " + playlistId + " não encontrada");
        }

        reproducaoClient.registrar(new ReproducaoRequest(playlistId));

        String mensagem = String.format("Playlist %s executada com sucesso", playlist.getNome());
        return ResponseEntity.ok(mensagem);
    }
}
