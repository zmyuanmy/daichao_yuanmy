package com.jbb.mgt.core.domain.jsonformat.nwjr.request;


import com.jbb.mgt.core.domain.jsonformat.nwjr.CallRecordBackFlowBody;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 通话记录信息回流 实体
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class CallRecordBackFlow {
    private String function_code;
    private String current_time;
    private CallRecordBackFlowBody body;

}
