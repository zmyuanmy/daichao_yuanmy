package com.jbb.mgt.core.domain;

/**
 * @author wyq
 * @date 2018/9/10 17:58
 */
public class LoanTotal {
    private Long amountTotal;
    private Long repaymentTotal;
    private Long toRepaymentTotal;
    private Long serviceFeeTotal;

    public Long getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Long amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Long getRepaymentTotal() {
        return repaymentTotal;
    }

    public void setRepaymentTotal(Long repaymentTotal) {
        this.repaymentTotal = repaymentTotal;
    }

    public Long getToRepaymentTotal() {
        return toRepaymentTotal;
    }

    public void setToRepaymentTotal(Long toRepaymentTotal) {
        this.toRepaymentTotal = toRepaymentTotal;
    }

    public Long getServiceFeeTotal() {
        return serviceFeeTotal;
    }

    public void setServiceFeeTotal(Long serviceFeeTotal) {
        this.serviceFeeTotal = serviceFeeTotal;
    }

    public LoanTotal() {
        super();
    }

    public LoanTotal(Long amountTotal, Long repaymentTotal, Long toRepaymentTotal, Long serviceFeeTotal) {
        this.amountTotal = amountTotal;
        this.repaymentTotal = repaymentTotal;
        this.toRepaymentTotal = toRepaymentTotal;
        this.serviceFeeTotal = serviceFeeTotal;
    }

    @Override
    public String toString() {
        return "LoanTotal{" + "amountTotal=" + amountTotal + ", repaymentTotal=" + repaymentTotal
            + ", toRepaymentTotal=" + toRepaymentTotal + ", serviceFeeTotal=" + serviceFeeTotal + '}';
    }
}
