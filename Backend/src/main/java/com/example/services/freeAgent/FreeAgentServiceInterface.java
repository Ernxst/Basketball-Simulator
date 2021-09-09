package com.example.services.freeAgent;

import com.example.entities.player.FreeAgent;

import java.util.Collection;
import java.util.List;

public interface FreeAgentServiceInterface {
    /**
     * @param playerID
     * @return
     * @throws FreeAgentNotFoundException
     */
    FreeAgent findFreeAgentByPlayerID(int playerID) throws FreeAgentNotFoundException;

    /**
     * @param playerIDs
     * @return
     */
    List<FreeAgent> findFreeAgentsByPlayerIDs(List<Integer> playerIDs);

    /**
     * @param freeAgent
     */
    void deleteFreeAgent(FreeAgent freeAgent);

    int insertFreeAgent(FreeAgent freeAgent);

    /**
     * @param freeAgents
     * @return
     */
    Iterable<FreeAgent> insertFreeAgents(Collection<FreeAgent> freeAgents);
}
