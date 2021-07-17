package com.example.repositories;

import com.example.entities.league.LeagueItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface LeagueItemRepository<S extends LeagueItem, ID extends Serializable> extends CrudRepository<S, ID> {
}
