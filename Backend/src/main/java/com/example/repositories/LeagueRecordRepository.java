package com.example.repositories;

import com.example.entities.league.LeagueRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRecordRepository extends LeagueItemRepository<LeagueRecord, String> {
}
