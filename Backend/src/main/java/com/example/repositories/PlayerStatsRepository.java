package com.example.repositories;

import com.example.entities.league.PlayerStats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatsRepository extends CrudRepository<PlayerStats, Integer> {
}
