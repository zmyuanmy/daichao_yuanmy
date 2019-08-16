package com.jbb.mgt.core.domain.jsonformat.nwjr.request;

import com.jbb.mgt.core.domain.jsonformat.nwjr.InstallationListBackFlowBody;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * APP安装列表信息回流
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class InstallationListBackFlow {
    private String function_code;
    private String current_time;
    private InstallationListBackFlowBody body;
}
