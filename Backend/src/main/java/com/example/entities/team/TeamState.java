package com.example.entities.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Map;

//@Entity
//@Table(name = "TEAM_STATE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamState {
    @Id
    @Column(name = "TEAM_STATE", nullable = false)
    private String teamState;

    @OneToMany(mappedBy = "teamState", fetch = FetchType.LAZY)
    private Map<Integer, Team> teams;
}
