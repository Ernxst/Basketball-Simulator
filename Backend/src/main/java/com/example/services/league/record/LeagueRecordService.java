package com.example.services.league.record;

import com.example.entities.league.LeagueRecord;
import com.example.repositories.LeagueRecordRepository;
import com.example.services.league.util.AbstractLeagueItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueRecordService extends AbstractLeagueItemService<LeagueRecord,
        String, LeagueRecordNotFoundException, LeagueRecord.LeagueRecordKey>
        implements LeagueRecordServiceInterface {

    public LeagueRecordService(@Autowired LeagueRecordRepository repository) {
        super(repository, "LeagueRecord");
    }
}
