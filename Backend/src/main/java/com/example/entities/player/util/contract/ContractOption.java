package com.example.entities.player.util.contract;

public enum ContractOption {
    TEAM("Team"),
    PLAYER("Player"),
    NONE("None");

    private static final double PROBABILITY_OF_TEAM_OPTION = 0.019;
    private static final double PROBABILITY_OF_PLAYER_OPTION = 0.027;
    private final String label;

    ContractOption(String label) {
        this.label = label;
    }

    /**
     * Generate a contract option for the given player.
     *
     * @param overall  the overall of the player.
     * @param age      the age of the player, young and old players are unlikely to get player or team options.
     * @param yearsPro the number of years a player has been in the league - rookies are extremely unlikely to get player options.
     * @return a random contract option.
     */
    public static ContractOption randomContractOption(int overall, int age, int yearsPro) {
        if (75 <= overall && overall <= 81 && yearsPro > 1 && age < 34) {
            if (Math.random() <= PROBABILITY_OF_TEAM_OPTION)
                return TEAM;
        }
        if (yearsPro > 3 && 24 <= age && age < 32 && overall > 80) {
            if (Math.random() <= PROBABILITY_OF_PLAYER_OPTION)
                return PLAYER;
        }
        return NONE;
    }

    public String getLabel() {
        return label;
    }

    public static ContractOption getByName(String name) {
        switch(name) {
            case "Team":
                return TEAM;
            case "Player":
                return PLAYER;
            case "None":
                return NONE;
        }
        return null;
    }
}
