package com.example.services.freeAgent;

import com.example.entities.player.FreeAgent;
import com.example.repositories.FreeAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FreeAgentService implements FreeAgentServiceInterface {
    @Autowired
    private FreeAgentRepository freeAgentRepository;

    @Override
    public int insertFreeAgent(FreeAgent freeAgent) {
        freeAgent = freeAgentRepository.save(freeAgent);
        return freeAgent.getPlayerID();
    }

    @Override
    public FreeAgent findFreeAgentByPlayerID(int freeAgentID) throws FreeAgentNotFoundException {
        Optional<FreeAgent> optionalFreeAgent = freeAgentRepository.findById(freeAgentID);
        return optionalFreeAgent.orElseThrow(() -> new FreeAgentNotFoundException("Could not find freeAgent"));
    }

    @Override
    public List<FreeAgent> findFreeAgentsByPlayerIDs(List<Integer> freeAgentIDs) {
        return (List<FreeAgent>) freeAgentRepository.findAllById(freeAgentIDs);
    }

    @Override
    public List<FreeAgent> findAllFreeAgentsInTeam(int teamID) {
        return null;
    }

    @Override
    public void deleteFreeAgent(FreeAgent freeAgent) {
        freeAgentRepository.delete(freeAgent);
    }
}
