package com.playyourlist.musicas.controller;

import com.playyourlist.musicas.entity.Musica;
import com.playyourlist.musicas.exception.ResourceNotFoundException;
import com.playyourlist.musicas.repository.MusicaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    private final MusicaRepository musicaRepository;

    public MusicaController(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
    }

    @PostMapping
    public ResponseEntity<Musica> cadastrar(@Valid @RequestBody Musica musica) {
        Musica salva = musicaRepository.save(musica);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public List<Musica> listar() {
        return musicaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Musica> buscarPorId(@PathVariable Long id) {
        Musica musica = musicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Música com id " + id + " não encontrada"));
        return ResponseEntity.ok(musica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Musica> atualizar(@PathVariable Long id, @Valid @RequestBody Musica dadosAtualizados) {
        Musica musica = musicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Música com id " + id + " não encontrada"));

        musica.setTitulo(dadosAtualizados.getTitulo());
        musica.setArtista(dadosAtualizados.getArtista());
        musica.setAlbum(dadosAtualizados.getAlbum());
        musica.setDuracao(dadosAtualizados.getDuracao());
        musica.setGenero(dadosAtualizados.getGenero());

        return ResponseEntity.ok(musicaRepository.save(musica));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Musica musica = musicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Música com id " + id + " não encontrada"));
        musicaRepository.delete(musica);
        return ResponseEntity.noContent().build();
    }
}
