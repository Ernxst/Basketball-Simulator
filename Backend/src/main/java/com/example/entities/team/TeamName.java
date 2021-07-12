package com.example.entities.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

//@Entity
//@Table(name = "TEAM_NAME")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamName {
    @Id
    @Column(name = "TEAM_NAME", nullable = false)
    private String teamName;

    @OneToMany(mappedBy = "teamName", fetch = FetchType.LAZY)
    private Map<Integer, Team> teams;
}
