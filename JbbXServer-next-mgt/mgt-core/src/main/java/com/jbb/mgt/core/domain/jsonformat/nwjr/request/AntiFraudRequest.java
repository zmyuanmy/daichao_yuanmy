package com.jbb.mgt.core.domain.jsonformat.nwjr.request;

import com.jbb.mgt.core.domain.jsonformat.nwjr.AntiFraudRequestMsgBody;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 反欺诈请求
 * 小金条风控评估报告（100633）
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class AntiFraudRequest {
    private String app_id;
    private String version;
    private String function_code;
    private String session_id;
    private String current_time;
    private String reserve;
    private AntiFraudRequestMsgBody msg_body;
}
