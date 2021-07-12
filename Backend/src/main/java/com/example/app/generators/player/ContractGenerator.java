package com.example.app.generators.player;

import com.example.app.util.MathsUtil;
import com.example.entities.player.util.PlayerConstants;
import com.example.entities.player.util.contract.Contract;
import com.example.entities.player.util.contract.ContractOption;

/**
 * Class to generate a contract for a given player.
 */
public class ContractGenerator {
    private static final double contractLengthMultiplier = 5.0 / 9801.0;
    private static final int averageAgeForLongestContract = 23;
    private static final double contractLengthAgeStdDeviation = 3;
    private static final double averageContractLength = 3;
    private static final double contractLengthStdDeviation = 1.5;

    private static final double salaryMultiplier = 45.914;
    private static final int averageAgeForHighestSalary = 28;
    private static final double salaryAgeStdDeviation = 4;
    private static final double averageSalary = 8320000;
    private static final double salaryStdDeviation = 4;

    private static final double probabilityOfNoTradeClause = 0.02;

    /**
     * Generate a contract for the given player.
     *
     * @param age              the age of the player.
     * @param overall          the overall of the player.
     * @param potentialOverall the potential overall of the player.
     * @return a new contract option.
     */
    public static Contract generateContract(int age, int yearsPro, int overall, int potentialOverall, int yearsSinceStart) {
        int contractLength = generateContractLength(age, overall, potentialOverall);
        int yearsRemaining = generateYearsRemaining(yearsSinceStart, contractLength);
        ContractOption contractOption = ContractOption.randomContractOption(overall, age, yearsPro);
        double salary = generateSalary(age, overall, potentialOverall, contractLength, yearsPro);
        boolean noTradeClause = generateNoTradeClause(yearsPro, overall);
        Contract contract = new Contract();
        contract.setContractLength(contractLength);
        contract.setYearsRemaining(yearsRemaining);
        contract.setContractOption(contractOption);
        contract.setSalary(salary);
        contract.setNoTradeClause(noTradeClause);
        return contract;
    }

    /**
     * Generate the length of the player's contract based on the following parameters.
     *
     * @param age              the age of the player, younger players are more likely to be given longer contracts;
     *                         older players (over 32) are less likely to be given longer contracts.
     * @param overall          the overall of the player, better players are more likely to be given longer contracts.
     * @param potentialOverall the potential overall of the player, young stars are more likely
     *                         to be given longer contracts.
     * @return an integer representing the length of the contract.
     */
    private static int generateContractLength(int age, int overall, int potentialOverall) {
        int baseLength = (int) (contractLengthMultiplier * overall * potentialOverall);
        int ageBasedLength = MathsUtil.gaussianInt(PlayerConstants.MAX_CONTRACT_LENGTH, age,
                averageAgeForLongestContract, contractLengthAgeStdDeviation);
        ageBasedLength = Math.max(PlayerConstants.MIN_CONTRACT_LENGTH, ageBasedLength);
        int normallyDistributedLength = MathsUtil.randomNormalDistributionInRange(PlayerConstants.MIN_CONTRACT_LENGTH,
                PlayerConstants.MAX_CONTRACT_LENGTH, averageContractLength, contractLengthStdDeviation);
        return (baseLength + ageBasedLength + normallyDistributedLength) / 3;
    }

    /**
     * @param age              the age of the player, young players are less likely to have high salaries,
     *                         the same is true for old players.
     * @param overall          the overall of the player, better players are more likely to have higher salaries.
     * @param potentialOverall the potential overall of the player, a player's (perceived) potential
     *                         is likely to positively influence their salary.
     * @param contractLength   the length of the contract, players with longer contracts
     *                         are more likely to have higher salaries.
     * @param yearsPro         the number of years the player has been in the league - more experienced players
     *                         are likely to receive higher salaries.
     * @return a double, representing the player's salary.
     */
    private static double generateSalary(int age, int overall, int potentialOverall, int contractLength, int yearsPro) {
        double baseSalary = (contractLength * (yearsPro + 1) * overall * potentialOverall * salaryMultiplier);
        double ageBasedSalary = MathsUtil.gaussianInt(PlayerConstants.MAX_SALARY, age,
                averageAgeForHighestSalary, salaryAgeStdDeviation);
        double normallyDistributedSalary = MathsUtil.randomNormalDistributionInRange(PlayerConstants.MIN_SALARY,
                PlayerConstants.MAX_SALARY, averageSalary, salaryStdDeviation);
        return (baseSalary + ageBasedSalary + normallyDistributedSalary) / 3;
    }

    /**
     * Randomly generate the No-Trade-Clause option.
     *
     * @param yearsPro the number of years a player has been in the league - only players with at least
     *                 eight years of experience are eligible (as per NBA rule).
     * @param overall  the overall of the player, only the best players (> 92 rated) are eligible for an NTC.
     * @return a boolean representing whether the player has a No-Trade-Clause.
     */
    private static boolean generateNoTradeClause(int yearsPro, int overall) {
        if (yearsPro > PlayerConstants.MIN_YEARS_PRO_FOR_NO_TRADE_CLAUSE && overall > PlayerConstants.MIN_OVERALL_FOR_NO_TRADE_CLAUSE) {
            return Math.random() <= probabilityOfNoTradeClause;
        }
        return false;
    }

    /**
     * Calculate the years remaining on a player's contract
     *
     * @param yearsSinceStart the number of years since the league has started - this may be longer
     *                        than the contract length.
     * @param contractLength  the length of the contract.
     * @return the number of years remaining on the contract (between 1 and the length of the contract).
     */
    private static int generateYearsRemaining(int yearsSinceStart, int contractLength) {
        if (yearsSinceStart >= contractLength)
            return contractLength;
        double random = Math.random();
        if (random < 0.33)
            return contractLength;
        else if (random < 0.67)
            return contractLength - yearsSinceStart;
        return yearsSinceStart;
    }
}
