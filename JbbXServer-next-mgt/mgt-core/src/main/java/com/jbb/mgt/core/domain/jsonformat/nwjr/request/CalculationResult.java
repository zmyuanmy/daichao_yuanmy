package com.jbb.mgt.core.domain.jsonformat.nwjr.request;

import com.jbb.mgt.core.domain.jsonformat.nwjr.CalculationResultMsgBody;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class CalculationResult {
    private String app_id;
    private String version;
    private String function_code;
    private String session_id;
    private String current_time;
    private String reserve;
    private CalculationResultMsgBody msg_body;

}
