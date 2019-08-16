package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClubQueryResponse {
    
    public static int SUCCES_CODE = 0;
    
    private int code;
    private String message;
    @JsonProperty("task_id")
    private String taskId;
    private ClubQueryData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public ClubQueryData getData() {
        return data;
    }

    public void setData(ClubQueryData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClubQueryResponse [code=" + code + ", message=" + message + ", taskId=" + taskId + ", data=" + data
            + "]";
    }

}
