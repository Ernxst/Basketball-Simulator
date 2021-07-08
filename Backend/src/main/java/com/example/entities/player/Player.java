package com.example.entities.player;

import com.example.app.util.MathsUtil;
import com.example.app.util.Util;
import com.example.entities.player.util.Archetype;
import com.example.entities.player.util.Position;
import com.example.entities.player.util.attributes.PlayerAttributes;
import com.example.entities.player.util.contract.Contract;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class representing a player.
 */
//@Entity
//@Table(name = "PLAYER")
public class Player {
    protected final String firstName;
    protected final String lastName;
    protected final double height;
    protected final double wingspan;
    protected final Archetype archetype;
    protected final String college;
    protected final LocalDate birthDate;
    protected Position position;
    protected Position secondaryPosition;
    protected double weight;
    protected double standingVertical;
    protected double maxVertical;
    protected int yearsPro;
    protected int overall;
    protected int potentialOverall;
    protected Contract contract;
    protected PlayerAttributes playerAttributes;
    protected PlayerAttributes potentialAttributes;

    //    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int playerID;

    protected int teamID;

    public Player(String firstName, String lastName, Position position, Position secondaryPosition, double height,
                  double weight, double wingspan, double standingVertical, double maxVertical, Archetype archetype,
                  String college, LocalDate birthDate, int yearsPro, int overall, int potentialOverall, Contract contract,
                  PlayerAttributes playerAttributes, PlayerAttributes potentialAttributes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.secondaryPosition = secondaryPosition;
        this.height = height;
        this.weight = weight;
        this.wingspan = wingspan;
        this.standingVertical = standingVertical;
        this.maxVertical = maxVertical;
        this.archetype = archetype;
        this.college = college;
        this.birthDate = birthDate;
        this.yearsPro = yearsPro;
        this.overall = overall;
        this.potentialOverall = potentialOverall;
        this.contract = contract;
        this.playerAttributes = playerAttributes;
        this.potentialAttributes = potentialAttributes;
    }

    public Player(String firstName, String lastName, Position position, Position secondaryPosition, double height,
                  double weight, double wingspan, double standingVertical, double maxVertical, Archetype archetype,
                  String college, LocalDate birthDate, int yearsPro, int overall, int potentialOverall, Contract contract,
                  PlayerAttributes playerAttributes, PlayerAttributes potentialAttributes, int playerID, int teamID) {
        this(firstName, lastName, position, secondaryPosition, height, weight, wingspan, standingVertical,
                maxVertical, archetype, college, birthDate, yearsPro, overall, potentialOverall, contract,
                playerAttributes, potentialAttributes);
        this.playerID = playerID;
        this.teamID = teamID;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getFirstInitialAndSurname() {
        return getFirstName().charAt(0) + ". " + getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getSecondaryPosition() {
        return secondaryPosition;
    }

    public void setSecondaryPosition(Position secondaryPosition) {
        this.secondaryPosition = secondaryPosition;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWingspan() {
        return wingspan;
    }

    public double getStandingVertical() {
        return standingVertical;
    }

    public void setStandingVertical(double standingVertical) {
        this.standingVertical = standingVertical;
    }

    public double getMaxVertical() {
        return maxVertical;
    }

    public void setMaxVertical(double maxVertical) {
        this.maxVertical = maxVertical;
    }

    public Archetype getArchetype() {
        return archetype;
    }

    public String getCollege() {
        return college;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        return Util.yearsBetweenDateAndToday(getBirthDate());
    }

    public int getYearsPro() {
        return yearsPro;
    }

    public void setYearsPro(int yearsPro) {
        this.yearsPro = yearsPro;
    }

    public int getOverall() {
        return overall;
    }

    public void setOverall(int overall) {
        this.overall = overall;
    }

    public int getPotentialOverall() {
        return potentialOverall;
    }

    public void setPotentialOverall(int potentialOverall) {
        this.potentialOverall = potentialOverall;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public PlayerAttributes getPlayerAttributes() {
        return playerAttributes;
    }

    public void setPlayerAttributes(PlayerAttributes playerAttributes) {
        this.playerAttributes = playerAttributes;
    }

    public PlayerAttributes getPotentialAttributes() {
        return potentialAttributes;
    }

    public void setPotentialAttributes(PlayerAttributes potentialAttributes) {
        this.potentialAttributes = potentialAttributes;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    /**
     * Turn a player into a free agent.
     *
     * @return a free agent of this player, with no contract or team.
     */
    public FreeAgent toFreeAgent() {
        return new FreeAgent(firstName, lastName, position, secondaryPosition, height, weight, wingspan, standingVertical,
                maxVertical, archetype, college, birthDate, yearsPro, overall, potentialOverall, null,
                playerAttributes, potentialAttributes, playerID, -1);
    }

    public Player copy() {
        return new Player(firstName, lastName, position, secondaryPosition, height, weight, wingspan, standingVertical,
                maxVertical, archetype, college, birthDate, yearsPro, overall, potentialOverall, contract,
                playerAttributes, potentialAttributes, playerID, teamID);
    }

    @Override
    public String toString() {
        int[] heightFeetInches = MathsUtil.cmToFeetAndInches(height);
        int[] wingspanFeetInches = MathsUtil.cmToFeetAndInches(wingspan);
        return "Player {" +
                "\n        Player ID:                  " + playerID +
                "\n        Team ID:                    " + teamID +
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
                "\n        Archetype:                  " + archetype.getLabel() +
                "\n        Primary Position:           " + position.getFullName() +
                "\n        Secondary Position:         " + secondaryPosition +
                "\n        Years in League:            " + yearsPro +
                "\n        Overall:                    " + overall +
                "\n        Potential Overall:          " + potentialOverall +
                "\n        Contract:                   " + contract +
                "\n        Attributes:                 " + playerAttributes +
                "\n        Potential Attributes:       " + potentialAttributes +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerID == player.playerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID);
    }
}
