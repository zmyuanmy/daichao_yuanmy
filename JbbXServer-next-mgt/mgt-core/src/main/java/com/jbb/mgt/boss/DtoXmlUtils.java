/**
 * Copyright (c) behosoft Co.,Ltd.
 * All Rights Reserved.
 * <p>
 * This software is the confidential and proprietary information of behosoft.
 * (Social Security Department). You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with behosoft.
 * <p>
 * Distributable under GNU LGPL license by gnu.org
 */
package com.jbb.mgt.boss;

import com.jbb.mgt.server.core.util.CheckApplyUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title: 百弘电商物流标准版_[]_[模块名]
 * </p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 *
 * @author jiangyonghua
 * @author (lastest modification by)
 * @version 1.0 2013-10-17
 * @since 1.0
 */
@Slf4j
public class DtoXmlUtils {

    public static Map<String, Marshaller> marshallerMap = new HashMap<String, Marshaller>();

    public static Map<String, Unmarshaller> UnmarshallerMap = new HashMap<String, Unmarshaller>();

    public static synchronized String dtoToXml(Object dtoObject)
            throws Exception {
        String xml = "";
        StringWriter stringWriter = new StringWriter();
        try {
            String className = dtoObject.getClass().getName();
            Marshaller marshaller_res = null;
            if (marshallerMap.containsKey(className)) {
                marshaller_res = marshallerMap.get(className);
            } else {
                marshaller_res = JAXBContext.newInstance(dtoObject.getClass()).createMarshaller();
                marshallerMap.put(className, marshaller_res);
            }
            marshaller_res.marshal(dtoObject, stringWriter);
            xml = stringWriter.toString();
            stringWriter.flush();
        } catch (Exception ex) {
            log.error("to_xml:{}", ex.toString());
            throw ex;
        } finally {
            try {
                if (stringWriter != null) {
                    stringWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return xml;
    }

    public static synchronized Object xmlToDto(String xml, Class classType)
            throws Exception {
        Object obj = null;
        try {
            Unmarshaller unmarshaller_req = null;
            String className = classType.getName();
            if (UnmarshallerMap.containsKey(className)) {
                unmarshaller_req = UnmarshallerMap.get(className);
            } else {
                unmarshaller_req = JAXBContext.newInstance(classType).createUnmarshaller();
                UnmarshallerMap.put(className, unmarshaller_req);
            }
            obj = unmarshaller_req.unmarshal(new StringReader(xml));
        } catch (Exception ex) {
            log.error("to_dto:{}", ex.toString());
            throw ex;
        }
        return obj;
    }

}
