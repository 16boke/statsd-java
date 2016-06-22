package com;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

public class TestStatsD {
	public static long getOPS(String url) {
		try {
			String res = HttpUtil.getHttp(url);
			if (res != null && res.trim().length() > 0) {
				JSONObject jsonObject = JSONObject.fromObject(res);
				return jsonObject.getLong("ops");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void pm25(StatsDClient client) {
		PmStatsD.gaugePm25(client);
	}

	public static void main(String[] args) {
		try {
			final StatsDClient client = new NonBlockingStatsDClient("", "192.168.48.47", 8125);
			final Map<String, String> map = new HashMap<String, String>();
			map.put("mvs.ops", "http://192.168.112.42:18087/api/overview");
			map.put("mvir.ops", "http://192.168.112.40:18087/api/overview");
			map.put("tvs.ops", "http://192.168.112.47:18087/api/overview");
			ExecutorService threadPool = Executors.newCachedThreadPool();
			Set<String> keySet = map.keySet();
			for (final String key : keySet) {
				threadPool.execute(new Runnable() {
					@Override
					public void run() {
						while (true) {
							client.count(key, getOPS(map.get(key)));
							try {
								Thread.sleep(60000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
			
			threadPool.execute(new Runnable() {
				public void run() {
					while (true) {
						pm25(client);
						try {
							Thread.sleep(60000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			
			threadPool.shutdown();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
