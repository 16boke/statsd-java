package com;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

import net.sf.json.JSONObject;

public class PmStatsD {
	private static String PM25_API_URL = "http://feed.aqicn.org/feed/%s/en/feed.v1.json";

	public static int get_city_data(String city,StatsDClient client) {
		try {
			long begin = System.currentTimeMillis();
			String res = HttpUtil.getHttp(String.format(PM25_API_URL, city));
			long time = System.currentTimeMillis() - begin;
			client.time("airquality."+city+".response.time", time);
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
	public static void gaugePm25(StatsDClient client){
		client.gauge("airquality.beijing.pm25", get_city_data("beijing",client));
		client.gauge("airquality.shanghai.pm25", get_city_data("shanghai",client));
		client.gauge("airquality.guangzhou.pm25", get_city_data("guangzhou",client));
		client.gauge("airquality.wuhan.pm25", get_city_data("wuhan",client));
		client.gauge("airquality.xiantao.pm25", get_city_data("xiantao",client));
	}

	public static void main(String[] args) {
		StatsDClient client = new NonBlockingStatsDClient("","192.168.48.47", 8125);
		gaugePm25(client);
	}
}
