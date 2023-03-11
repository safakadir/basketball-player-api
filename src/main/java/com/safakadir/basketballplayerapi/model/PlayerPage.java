package com.safakadir.basketballplayerapi.model;

import java.util.List;

public class PlayerPage {
    private List<Player> players;
    private int totalPages;
    private int currentPage;
    private int pageSize;

    public PlayerPage() {
    }

    public PlayerPage(List<Player> players, int totalPages, int currentPage, int pageSize) {
        this.players = players;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
