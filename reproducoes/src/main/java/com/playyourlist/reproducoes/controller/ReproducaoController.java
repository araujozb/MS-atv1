package com.playyourlist.reproducoes.controller;

import com.playyourlist.reproducoes.dto.ReproducaoRequest;
import com.playyourlist.reproducoes.dto.TotalReproducoesResponse;
import com.playyourlist.reproducoes.entity.Reproducao;
import com.playyourlist.reproducoes.repository.ReproducaoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reproducao")
public class ReproducaoController {

    private final ReproducaoRepository reproducaoRepository;

    public ReproducaoController(ReproducaoRepository reproducaoRepository) {
        this.reproducaoRepository = reproducaoRepository;
    }

    @PostMapping
    public ResponseEntity<Reproducao> registrar(@Valid @RequestBody ReproducaoRequest request) {
        Reproducao reproducao = new Reproducao(request.getPlaylistId(), LocalDateTime.now());
        Reproducao salva = reproducaoRepository.save(reproducao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{playlistid}")
    public List<Reproducao> listarPorPlaylist(@PathVariable Long playlistid) {
        return reproducaoRepository.findByPlaylistId(playlistid);
    }

    @GetMapping("/total/{playlistid}")
    public ResponseEntity<TotalReproducoesResponse> total(@PathVariable Long playlistid) {
        long total = reproducaoRepository.countByPlaylistId(playlistid);
        return ResponseEntity.ok(new TotalReproducoesResponse(playlistid, total));
    }
}
