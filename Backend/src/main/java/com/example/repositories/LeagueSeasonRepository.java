package com.example.repositories;

import com.example.entities.league.LeagueSeason;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueSeasonRepository extends CrudRepository<LeagueSeason, Integer> {
}
