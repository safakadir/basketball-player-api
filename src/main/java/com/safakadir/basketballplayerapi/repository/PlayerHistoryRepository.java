package com.safakadir.basketballplayerapi.repository;

import com.safakadir.basketballplayerapi.model.PlayerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerHistoryRepository extends JpaRepository<PlayerHistory, Long> {

}
