package com.playyourlist.playlists.controller;

import com.playyourlist.playlists.entity.Playlist;
import com.playyourlist.playlists.entity.PlaylistMusica;
import com.playyourlist.playlists.exception.ResourceNotFoundException;
import com.playyourlist.playlists.repository.PlaylistMusicaRepository;
import com.playyourlist.playlists.repository.PlaylistRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicaRepository playlistMusicaRepository;

    public PlaylistController(PlaylistRepository playlistRepository,
                               PlaylistMusicaRepository playlistMusicaRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistMusicaRepository = playlistMusicaRepository;
    }

    @PostMapping
    public ResponseEntity<Playlist> criar(@Valid @RequestBody Playlist playlist) {
        Playlist salva = playlistRepository.save(playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public List<Playlist> listar() {
        return playlistRepository.findAll();
    }

    @GetMapping("/{playlistid}")
    public ResponseEntity<Playlist> buscarPorId(@PathVariable Long playlistid) {
        Playlist playlist = buscarOu404(playlistid);
        return ResponseEntity.ok(playlist);
    }

    @PutMapping("/{playlistid}")
    public ResponseEntity<Playlist> atualizar(@PathVariable Long playlistid,
                                               @Valid @RequestBody Playlist dadosAtualizados) {
        Playlist playlist = buscarOu404(playlistid);
        playlist.setNome(dadosAtualizados.getNome());
        playlist.setDescricao(dadosAtualizados.getDescricao());
        return ResponseEntity.ok(playlistRepository.save(playlist));
    }

    @DeleteMapping("/{playlistid}")
    public ResponseEntity<Void> excluir(@PathVariable Long playlistid) {
        Playlist playlist = buscarOu404(playlistid);
        playlistMusicaRepository.deleteByPlaylistId(playlist.getId());
        playlistRepository.delete(playlist);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{playlistid}/musicas/{musicaId}")
    public ResponseEntity<Void> adicionarMusica(@PathVariable Long playlistid, @PathVariable Long musicaId) {
        buscarOu404(playlistid);

        boolean jaExiste = playlistMusicaRepository.findByPlaylistIdAndMusicaId(playlistid, musicaId).isPresent();
        if (!jaExiste) {
            playlistMusicaRepository.save(new PlaylistMusica(playlistid, musicaId));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{playlistid}/musicas/{musicaId}")
    public ResponseEntity<Void> removerMusica(@PathVariable Long playlistid, @PathVariable Long musicaId) {
        buscarOu404(playlistid);

        PlaylistMusica vinculo = playlistMusicaRepository.findByPlaylistIdAndMusicaId(playlistid, musicaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Música " + musicaId + " não está na playlist " + playlistid));
        playlistMusicaRepository.delete(vinculo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{playlistid}/musicas")
    public ResponseEntity<List<Long>> listarMusicas(@PathVariable Long playlistid) {
        buscarOu404(playlistid);

        List<Long> ids = playlistMusicaRepository.findByPlaylistId(playlistid).stream()
                .map(PlaylistMusica::getMusicaId)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ids);
    }

    private Playlist buscarOu404(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist com id " + id + " não encontrada"));
    }
}
