package com.funshion;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

import net.sf.json.JSONObject;

public class PmStatsD {
	private static String PM25_API_URL = "http://feed.aqicn.org/feed/%s/en/feed.v1.json";

	public static int get_city_data(String city) {
		try {
			String res = HttpUtil.getHttp(String.format(PM25_API_URL, city));
			System.out.println(res);
			if (res != null && res.trim().length() > 0) {
				JSONObject jsonObject = JSONObject.fromObject(res);
				return strToInt(jsonObject.getJSONObject("iaqi").getJSONObject("pm25").get("val"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int strToInt(Object obj) {
		if (obj == null)
			return 0;
		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) {
		StatsDClient client = new NonBlockingStatsDClient("","192.168.48.47", 8125);
		client.gauge("airquality.beijing.pm25", get_city_data("beijing"));
		client.gauge("airquality.shanghai.pm25", get_city_data("shanghai"));
		client.gauge("airquality.guangzhou.pm25", get_city_data("guangzhou"));
		client.gauge("airquality.wuhan.pm25", get_city_data("wuhan"));
		client.gauge("airquality.xiantao.pm25", get_city_data("xiantao"));
	}
}
