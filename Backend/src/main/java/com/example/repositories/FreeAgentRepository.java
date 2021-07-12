package com.example.repositories;

import com.example.entities.player.FreeAgent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeAgentRepository extends CrudRepository<FreeAgent, Integer> {
}
