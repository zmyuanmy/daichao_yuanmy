package com.jbb.mgt.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.qftx.other.QftxOpen4OutUtil;

/**
 * 企蜂开放接口实际业务：对外
 * 
 * @author 企蜂通信 GuoBL
 */
public class QftxOpenBusiTest {
	private static final String url			= "http://open.qftx.net:9800/open/out";
	private static final String authId		= "wJWpmuZGmh7MQx0O";
	private static final String authSecret	= "QkPrmrbKypwwY2OHPvLfC4UVzhMQay0B";
	
	public static void main(String[] args) {
		// 接口：新增客户
		String companyId = "szjfxy";
		String reqType = "CUST_ADD";
		Date ts = new Date();
		String reqData = ""
				+ "{"
					+ "\"type\":\"" + "1" + "\","
					+ "\"data\":["
						+ "{"
							+ "\"reqId\":\"" + "20170424154235a0b6e127e0cbe31ba3" + "\","
							+ "\"name\":\"" + "企蜂通信1" + "\","
							+ "\"mobilePhone\":\"" + "13012345678" + "\","
							+ "\"defineds\":{" + "" + "}"
						+ "}"
					+ "]"
				+ "}";
		
		
		// 主内容：
		String content = QftxOpen4OutUtil.content(companyId, reqType, ts, reqData);
		
		// 发送：
		try {
			System.out.println(timeLog() + "发送内容：" + content);
			String result = QftxOpen4OutUtil.aes(url, authId, authSecret, content, ts);
			System.out.println(timeLog() + "返回结果：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 时间日志： */
	private static String timeLog() {
		SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		return SDF.format(new Date()) + " ";
	}
}
