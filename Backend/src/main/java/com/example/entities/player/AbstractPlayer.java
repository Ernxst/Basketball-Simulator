package com.example.entities.player;

import com.example.entities.player.util.Archetype;
import com.example.entities.player.util.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractPlayer {
    //    protected PlayerAttributes playerAttributes;
    //    protected PlayerAttributes potentialAttributes;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYER_ID", nullable = false)
    protected int playerID;

    @Column(name = "FIRST_NAME", nullable = false)
    protected String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    protected String lastName;

    @Column(name = "HEIGHT", nullable = false)
    protected double height;

    @Column(name = "WEIGHT", nullable = false)
    protected double weight;

    @Column(name = "WINGSPAN", nullable = false)
    protected double wingspan;

    @Column(name = "STANDING_VERTICAL", nullable = false)
    protected double standingVertical;

    @Column(name = "MAX_VERTICAL", nullable = false)
    protected double maxVertical;

    @Column(name = "COLLEGE", nullable = false)
    protected String college;

    @Column(name = "BIRTH_DATE", nullable = false)
    protected LocalDate birthDate;

    @Column(name = "YEARS_PRO", nullable = false)
    protected int yearsPro;

    @Column(name = "OVERALL", nullable = false)
    protected int overall;

    @Column(name = "POTENTIAL", nullable = false)
    protected int potentialOverall;

    @Column(name = "ARCHETYPE_ID", nullable = false)
    protected String archetype;

    @Column(name = "POSITION", nullable = false)
    protected String position;

    @Column(name = "SECONDARY_POSITION", nullable = false)
    protected String secondaryPosition;

    public Archetype getArchetype() {
        return Archetype.getArchetypeByName(archetype);
    }

    public void setArchetype(Archetype archetype) {
        this.archetype = archetype.getLabel();
    }

    public Position getPosition() {
        return Position.getPositionByName(position);
    }

    public Position getSecondaryPosition() {
        return Position.getPositionByName(secondaryPosition);
    }

    public void setPosition(Position position) {
        this.position = position.getShortName();
    }

    public void setSecondaryPosition(Position position) {
        if (position != null)
            this.secondaryPosition = position.getShortName();
    }
}
