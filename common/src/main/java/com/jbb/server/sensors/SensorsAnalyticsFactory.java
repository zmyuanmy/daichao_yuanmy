package com.jbb.server.sensors;

import com.sensorsdata.analytics.javasdk.SensorsAnalytics;

public class SensorsAnalyticsFactory {
	// private static final Logger logger =
	// LoggerFactory.getLogger(SensorsAnalyticsFactory.class);

	private static SensorsAnalytics sa;

	private static String SERVER_URL = "https://jiebangbang.datasink.sensorsdata.cn/sa?token=e31110d7b90682da";
	private static int bulkSize = 10;

	public static SensorsAnalytics getSa() {
		if (sa == null) {

			sa = new SensorsAnalytics(new SensorsAnalytics.BatchConsumer(SERVER_URL, bulkSize));
		}
		return sa;
	}

	public static void close() {
		if (sa != null) {
			sa.flush();
			sa.shutdown();
		}
	}
	


}
