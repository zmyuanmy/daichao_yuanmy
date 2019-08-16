package com.jbb.mgt.core.domain.jsonformat.nwjr.request;

import com.jbb.mgt.core.domain.jsonformat.nwjr.MailListBackFlowBody;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class MailListBackFlow {
    private String function_code;
    private String current_time;
    private MailListBackFlowBody body;

}
