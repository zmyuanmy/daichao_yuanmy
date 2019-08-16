package com.jbb.server.rs.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service(FaceAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FaceAction extends BasicAction {
    
    private static Logger logger = LoggerFactory.getLogger(FaceAction.class);
    
    public static final String ACTION_NAME = "FaceAction";
    
    public void getReturnResult(HttpServletRequest request, HttpServletResponse response){
        logger.debug("> getReturnResult(), request ={}", request);
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String)iter.next();
            String[] values = (String[])requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            System.out.println("return "+"name "+name+"valueStr "+valueStr);
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        /*try {
            String biz_extra_data = new String(request.getParameter("biz_extra_data").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("biz_extra_data "+biz_extra_data);
        } catch (UnsupportedEncodingException e) {
            logger.error("Face++同步调用异常，原因{}", e.getMessage());
             e.printStackTrace();
        }*/
        
        logger.debug("< getReturnResult()");
    }
    
     public void notyfyFace(HttpServletRequest request, HttpServletResponse response){
        logger.debug("> notyfyFace(), request ={}", request);
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String)iter.next();
            String[] values = (String[])requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            System.out.println("notify "+"name "+name+"valueStr "+valueStr);
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        /*try {
            String biz_extra_data = new String(request.getParameter("biz_extra_data").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("biz_extra_data "+biz_extra_data);
        } catch (UnsupportedEncodingException e) {
            logger.error("Face++同步调用异常，原因{}", e.getMessage());
             e.printStackTrace();
        }*/
        
        logger.debug("< notyfyFace()");
    } 
    
    


}
