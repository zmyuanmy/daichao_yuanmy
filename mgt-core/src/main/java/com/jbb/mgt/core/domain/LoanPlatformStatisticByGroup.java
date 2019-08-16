package com.jbb.mgt.core.domain;

import java.util.List;

public class LoanPlatformStatisticByGroup {
    private String groupName;
    private List<LoanPlatformStatistic> loanPlatformStatistic;

    private int totalBalance;
    
    

    public LoanPlatformStatisticByGroup() {
        super();
    }

    public LoanPlatformStatisticByGroup(String groupName, List<LoanPlatformStatistic> loanPlatformStatistic) {
        super();
        this.groupName = groupName;
        this.loanPlatformStatistic = loanPlatformStatistic;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<LoanPlatformStatistic> getLoanPlatformStatistic() {
        return loanPlatformStatistic;
    }

    public void setLoanPlatformStatistic(List<LoanPlatformStatistic> loanPlatformStatistic) {
        this.loanPlatformStatistic = loanPlatformStatistic;
    }

    public int getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }

    public void calculateTotalBalance() {
        int totalBalance = 0;
        if (this.loanPlatformStatistic == null) {
           return;
        }
        for (LoanPlatformStatistic lps : this.loanPlatformStatistic) {
            totalBalance += lps.getBalance();
        }
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return "LoanPlatformStatisticByGroup [groupName=" + groupName + ", loanPlatformStatistic="
            + loanPlatformStatistic + ", totalBalance=" + totalBalance + "]";
    }

}
