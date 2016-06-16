package com.funshion;


import net.sf.json.JSONObject;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;


public class TestStatsD {
	public static long getOPS(String url){
		try {
			String res = HttpUtil.getHttp(url);
			if(res!=null && res.trim().length()>0){
				JSONObject jsonObject = JSONObject.fromObject(res);
				return jsonObject.getLong("ops");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static void pm25(StatsDClient client){
		client.gauge("airquality.beijing.pm25", PmStatsD.get_city_data("beijing"));
		client.gauge("airquality.shanghai.pm25", PmStatsD.get_city_data("shanghai"));
		client.gauge("airquality.guangzhou.pm25", PmStatsD.get_city_data("guangzhou"));
		client.gauge("airquality.wuhan.pm25", PmStatsD.get_city_data("wuhan"));
		client.gauge("airquality.xiantao.pm25", PmStatsD.get_city_data("xiantao"));
	}
	public static void main(String[] args) {
		try {
			final StatsDClient client = new NonBlockingStatsDClient("","192.168.48.47", 8125);
			final String mvs_url = "http://192.168.112.42:18087/api/overview";
			final String mvir_url = "http://192.168.112.40:18087/api/overview";
			final String tvs_url = "http://192.168.112.47:18087/api/overview";
			new Thread(new Runnable() {
				public void run() {
					while(true){
						client.count("mvs.ops", getOPS(mvs_url));
						client.count("mvir.ops", getOPS(mvir_url));
						client.count("tvs.ops", getOPS(tvs_url));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
			new Thread(new Runnable() {
				public void run() {
					while(true){
						pm25(client);
						try {
							Thread.sleep(60000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
