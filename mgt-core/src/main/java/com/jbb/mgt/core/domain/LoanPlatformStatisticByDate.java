package com.jbb.mgt.core.domain;

import java.sql.Date;
import java.util.List;

public class LoanPlatformStatisticByDate {
    private Date statisticDate;
    private List<LoanPlatformStatistic> loanPlatformStatistic;
    
    public LoanPlatformStatisticByDate() {
        super();
    }
    
    public LoanPlatformStatisticByDate(Date statisticDate, List<LoanPlatformStatistic> loanPlatformStatistic) {
        super();
        this.statisticDate = statisticDate;
        this.loanPlatformStatistic = loanPlatformStatistic;
    }

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public List<LoanPlatformStatistic> getLoanPlatformStatistic() {
        return loanPlatformStatistic;
    }

    public void setLoanPlatformStatistic(List<LoanPlatformStatistic> loanPlatformStatistic) {
        this.loanPlatformStatistic = loanPlatformStatistic;
    }

}
