package com.jbb.server.core.domain;

public class FaceVerifyResult {

    private String requestId;
    private int timeUsed;
    private String bizNo;
    private String procedureNalidation;// "PASSED"：流程验证通过。 "VIDEO_SR_ERROR"：语音识别结果与要求不符。（仅当视频验证流程时）
                                       // "VIDEO_NOT_SYNCHRONIZED"：视频中唇语识别错误。（仅当视频验证流程时）"VIDEO_FACE_INCONSISTENT":
                                       // 视频过程中的人脸不一致

    private String faceGenuineness;// 假脸攻击判定的结果，它取如下值： “PASSED”：不是假脸。 “MASK”：面具攻击。 “SCREEN_REPLAY”：屏幕翻拍。
                                   // “FACEGEN”：软件合成脸。 一般仅在procedure_validation、face_genuineness 都为“PASSED”时，才认为活体认定通过。
    private String error_message;
    private int sim;
    private boolean flagVerify;
    private String verifyData;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getProcedureNalidation() {
        return procedureNalidation;
    }

    public void setProcedureNalidation(String procedureNalidation) {
        this.procedureNalidation = procedureNalidation;
    }

    public String getFaceGenuineness() {
        return faceGenuineness;
    }

    public void setFaceGenuineness(String faceGenuineness) {
        this.faceGenuineness = faceGenuineness;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public int getSim() {
        return sim;
    }

    public void setSim(int sim) {
        this.sim = sim;
    }

    public boolean isFlagVerify() {
        return flagVerify;
    }

    public void setFlagVerify(boolean flagVerify) {
        this.flagVerify = flagVerify;
    }

    public String getVerifyData() {
        return verifyData;
    }

    public void setVerifyData(String verifyData) {
        this.verifyData = verifyData;
    }

    @Override
    public String toString() {
        return "FaceVerifyResult [requestId=" + requestId + ", timeUsed=" + timeUsed + ", bizNo=" + bizNo
            + ", procedureNalidation=" + procedureNalidation + ", faceGenuineness=" + faceGenuineness
            + ", error_message=" + error_message + ", sim=" + sim + "]";
    }

}
