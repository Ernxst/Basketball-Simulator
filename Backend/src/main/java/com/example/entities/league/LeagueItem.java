package com.example.entities.league;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class LeagueItem<T> implements Serializable {
    /**
     * @return
     */
    public abstract T getId();

    /**
     * @param id
     */
    public abstract void setId(T id);
}
