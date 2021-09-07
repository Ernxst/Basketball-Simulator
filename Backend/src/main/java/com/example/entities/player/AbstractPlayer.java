package com.example.entities.player;

import com.example.app.util.MathsUtil;
import com.example.app.util.Util;
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
    @Column(name = "PLAYER_ID", insertable = false, updatable = false)
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

    @Column(name = "ARCHETYPE_NAME", nullable = false)
    protected String archetype;

    @Column(name = "POSITION", nullable = false)
    protected String playerPosition;

    @Column(name = "SECONDARY_POSITION")
    protected String playerSecondaryPosition;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getFirstInitialAndSurname() {
        return getFirstName().charAt(0) + ". " + getLastName();
    }

    public int getAge() {
        return Util.yearsBetweenDateAndToday(getBirthDate());
    }

    public Archetype getArchetype() {
        return Archetype.getArchetypeByName(archetype);
    }

    public void setArchetype(Archetype archetype) {
        this.archetype = archetype.getLabel();
    }

    public Position getPosition() {
        return Position.getPositionByName(playerPosition);
    }

    public void setPosition(Position position) {
        this.playerPosition = position.getShortName();
    }

    public Position getSecondaryPosition() {
        return Position.getPositionByName(playerSecondaryPosition);
    }

    public void setSecondaryPosition(Position position) {
        if (position != null)
            this.playerSecondaryPosition = position.getShortName();
    }

    @Override
    public String toString() {
        int[] heightFeetInches = MathsUtil.cmToFeetAndInches(height);
        int[] wingspanFeetInches = MathsUtil.cmToFeetAndInches(wingspan);
        return "Player {" +
                "\n        Player ID:                  " + playerID +
                "\n        First Name:                 '" + firstName + '\'' +
                "\n        Last Name:                  '" + lastName + '\'' +
                "\n        College:                    '" + college + '\'' +
                "\n        Date of Birth (Y-m-d):      " + birthDate +
                "\n        Age:                        " + getAge() +
                "\n        Height (cm):                " + height +
                "\n        Height (ft, in):            " + heightFeetInches[0] + "\"" + heightFeetInches[1] +
                "\n        Wingspan (cm):              " + wingspan +
                "\n        Wingspan (ft, in):          " + wingspanFeetInches[0] + "\"" + wingspanFeetInches[1] +
                "\n        Weight (lbs):               " + weight +
                "\n        Standing Vertical (inches): " + standingVertical +
                "\n        Max Vertical (inches):      " + maxVertical +
                "\n        Archetype:                  " + getArchetype().getLabel() +
                "\n        Primary Position:           " + getPosition().getFullName() +
                "\n        Secondary Position:         " + getPlayerSecondaryPosition() +
                "\n        Years in League:            " + yearsPro +
                "\n        Overall:                    " + overall +
                "\n        Potential Overall:          " + potentialOverall +
//                "\n        Attributes:                 " + playerAttributes +
//                "\n        Potential Attributes:       " + potentialAttributes +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerID == player.getPlayerID();
    }
}
