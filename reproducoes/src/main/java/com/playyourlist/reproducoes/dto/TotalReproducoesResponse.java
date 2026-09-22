package com.playyourlist.reproducoes.dto;

public class TotalReproducoesResponse {

    private Long playlistId;
    private long total;

    public TotalReproducoesResponse() {
    }

    public TotalReproducoesResponse(Long playlistId, long total) {
        this.playlistId = playlistId;
        this.total = total;
    }

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
