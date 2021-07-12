package com.example.services.freeAgent;

import com.example.entities.player.FreeAgent;

import java.util.List;

public interface FreeAgentServiceInterface {
    FreeAgent findFreeAgentByPlayerID(int playerID) throws FreeAgentNotFoundException;

    List<FreeAgent> findFreeAgentsByPlayerIDs(List<Integer> playerIDs);

    List<FreeAgent> findAllFreeAgentsInTeam(int teamID);

    void deleteFreeAgent(FreeAgent freeAgent);

    int insertFreeAgent(FreeAgent freeAgent);
}
