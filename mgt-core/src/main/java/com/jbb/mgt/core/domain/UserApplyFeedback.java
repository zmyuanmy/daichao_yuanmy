package com.jbb.mgt.core.domain;

public class UserApplyFeedback {
    private int applyId;
    private String reason;
    private String reasonDesc;
    private int point;
    private boolean zjFlag;

    public UserApplyFeedback() {}

    public UserApplyFeedback(int applyId, String reason, String reasonDesc, int point, boolean zjFlag) {
        this.applyId = applyId;
        this.reason = reason;
        this.reasonDesc = reasonDesc;
        this.point = point;
        this.zjFlag = zjFlag;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isZjFlag() {
        return zjFlag;
    }

    public void setZjFlag(boolean zjFlag) {
        this.zjFlag = zjFlag;
    }

    @Override
    public String toString() {
        return "UserApplyFeedback [applyId=" + applyId + ", reason=" + reason + ", reasonDesc=" + reasonDesc
            + ", point=" + point + ", zjFlag=" + zjFlag + "]";
    }

}
