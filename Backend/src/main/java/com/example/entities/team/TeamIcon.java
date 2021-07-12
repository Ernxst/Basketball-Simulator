package com.example.entities.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

//@Entity
//@Table(name = "TEAM_ICON")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamIcon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ICON_ID", nullable = false)
    private int iconID;

    @Column(name = "ICON", nullable = false)
    private String icon;

    @OneToMany(mappedBy = "icon", fetch = FetchType.LAZY)
    private Map<Integer, Team> teams;
}
