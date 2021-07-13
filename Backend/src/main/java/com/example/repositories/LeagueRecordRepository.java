package com.example.repositories;

import com.example.entities.league.LeagueRecord;
import org.springframework.data.repository.CrudRepository;

public interface LeagueRecordRepository extends CrudRepository<LeagueRecord, Integer> {
}
