package com.jbb.mgt.core.domain.jsonformat.nwjr;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 反欺诈计算结果
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class Result {
    private int decision; //0:accept 1:refuse 2:review
    private Detail detail;
}
