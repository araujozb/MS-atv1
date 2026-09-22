package com.playyourlist.api.dto;

public class ReproducaoRequest {

    private Long playlistId;

    public ReproducaoRequest() {
    }

    public ReproducaoRequest(Long playlistId) {
        this.playlistId = playlistId;
    }

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }
}
