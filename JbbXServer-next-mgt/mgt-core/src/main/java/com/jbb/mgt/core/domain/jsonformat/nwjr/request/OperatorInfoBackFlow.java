package com.jbb.mgt.core.domain.jsonformat.nwjr.request;

import com.jbb.mgt.core.domain.jsonformat.nwjr.OperatorInfoBackFlowBody;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/***
 * 第三方授权数据-运营商信息回流
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class OperatorInfoBackFlow {
    private String function_code;
    private OperatorInfoBackFlowBody body;

}
