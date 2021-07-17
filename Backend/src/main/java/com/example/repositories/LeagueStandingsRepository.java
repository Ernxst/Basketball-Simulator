package com.example.repositories;

import com.example.entities.league.LeagueStandings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueStandingsRepository extends LeagueItemRepository<LeagueStandings, Integer> {
}
