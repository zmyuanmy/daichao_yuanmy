package com.jbb.mgt.core.domain.jsonformat.nwjr.request;

import com.jbb.mgt.core.domain.jsonformat.nwjr.ShortMessageBackFlowBody;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 短信回流 实体
 */

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class ShortMessageBackFlow {
    private String function_code;
    private String current_time;
    private ShortMessageBackFlowBody body;
}
