package com.playyourlist.playlists.repository;

import com.playyourlist.playlists.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
