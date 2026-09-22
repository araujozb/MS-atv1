package com.playyourlist.musicas.repository;

import com.playyourlist.musicas.entity.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
}
