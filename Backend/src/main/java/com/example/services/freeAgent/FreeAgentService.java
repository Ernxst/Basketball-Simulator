package com.example.services.freeAgent;

import com.example.entities.player.FreeAgent;
import com.example.repositories.FreeAgentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FreeAgentService implements FreeAgentServiceInterface {
    private final FreeAgentRepository freeAgentRepository;

    @Override
    public int insertFreeAgent(FreeAgent freeAgent) {
        freeAgent = freeAgentRepository.save(freeAgent);
        return freeAgent.getPlayerID();
    }

    @Override
    public Iterable<FreeAgent> insertFreeAgents(Collection<FreeAgent> freeAgents) {
        return freeAgentRepository.saveAll(freeAgents);
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
    public void deleteFreeAgent(FreeAgent freeAgent) {
        freeAgentRepository.delete(freeAgent);
    }
}
