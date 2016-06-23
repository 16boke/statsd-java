package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class TestHalf {
	public static int getIndex(int target, int[] array) {
		if (array == null || array.length == 0)
			return -1;
		if (target < array[0] || target > array[array.length - 1])
			return -1;
		int left = 0;
		int right = array.length - 1;
		int mid = (left + right) / 2;
		while (left <= right) {
			if (target < array[mid])
				right = mid - 1;
			if (target > array[mid])
				left = mid + 1;
			if (target == array[mid])
				return mid;
			mid = (left + right) / 2;
		}
		return -1;
	}

	/**
	 * 根据ip地址判断属于哪个ip地址段，返回这个段的ip地址对象
	 * 
	 * @param ip
	 * @param ipBeans
	 * @return
	 */
	public static IPBean getIPIndex(String ip) {
		IPBean[] ipBeans = getIpBeans();
		if (ipBeans == null || ipBeans.length == 0)
			return null;
		long iplong = IPUtil.ipToLong(ip);
		if (iplong < ipBeans[0].getBegin() || iplong > ipBeans[ipBeans.length - 1].getEnd())
			return null;
		int left = 0;
		int right = ipBeans.length - 1;
		int mid = (left + right) / 2;
		while (left <= right) {
			if (iplong < ipBeans[mid].getBegin())
				right = mid - 1;
			if (iplong > ipBeans[mid].getBegin())
				left = mid + 1;
			if (iplong >= ipBeans[mid].getBegin() && iplong <= ipBeans[mid].getEnd())
				return ipBeans[mid];
			mid = (left + right) / 2;
		}
		return null;
	}

	public static IPBean[] getIpBeans() {
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		try {
			File file = new File(TestHalf.class.getResource("ipcode.txt").getPath());
			inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String line;
			LinkedList<IPBean> ipBeanList = new LinkedList<IPBean>();
			while ((line = reader.readLine()) != null) {
				String[] tmp = line.split(",");
				ipBeanList.add(new IPBean(IPUtil.ipToLong(tmp[0]), IPUtil.ipToLong(tmp[1]), tmp[2]));
			}
			IPBean[] ipBeans = ipBeanList.toArray(new IPBean[] {});
			return ipBeans;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				reader.close();
				inputStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// int num[] = { 2, 3, 4, 6, 10, 20, 31, 35, 42, 53, 60, 90 };
		// System.out.println(getIndex(7, num));
		IPBean ipBean = getIPIndex("20.1.1.255");
		System.out.println(ipBean);
		if (ipBean != null)
			System.out.println("begin = " + IPUtil.longToIP(ipBean.getBegin()) + " , end = " + IPUtil.longToIP(ipBean.getEnd())
					+ " , code = " + ipBean.getCode());
	}
}

class IPBean {
	private long begin;
	private long end;
	private String code;

	public long getBegin() {
		return begin;
	}

	public void setBegin(long begin) {
		this.begin = begin;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public IPBean(long begin, long end, String code) {
		this.begin = begin;
		this.end = end;
		this.code = code;
	}

	@Override
	public String toString() {
		return "IPBean [begin=" + begin + ", end=" + end + ", code=" + code + "]";
	}

	public IPBean() {
	}
}
