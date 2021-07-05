package com.example.entities.player.util.contract;

/**
 * A class representing a player's contract.
 */
public class Contract {
    private final int contractLength;
    private final int yearsRemaining;
    private final ContractOption contractOption;
    private final double salary;
    private final boolean noTradeClause;

    public Contract(int contractLength, int yearsRemaining, ContractOption contractOption, double salary, boolean noTradeClause) {
        this.contractLength = contractLength;
        this.yearsRemaining = yearsRemaining;
        this.contractOption = contractOption;
        this.salary = salary;
        this.noTradeClause = noTradeClause;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getYearsRemaining() {
        return yearsRemaining;
    }

    public ContractOption getContractOption() {
        return contractOption;
    }

    public double getSalary() {
        return salary;
    }

    public boolean hasNoTradeClause() {
        return noTradeClause;
    }

    public Contract copy() {
        return new Contract(contractLength, yearsRemaining, contractOption, salary, noTradeClause);
    }

    @Override
    public String toString() {
        return "Contract {" +
                "\n    Contract Length:  " + contractLength +
                "\n    Years Remaining:  " + yearsRemaining +
                "\n    Contract Option:  " + contractOption.getLabel() +
                "\n    Salary ($):       " + salary +
                "\n    No Trade Clause?: " + noTradeClause +
                "\n}";
    }
}
