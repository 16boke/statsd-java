package com;

import java.io.File;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

/**
 * ganymed的版本选择build210 
 * <dependency> 
 * <groupId>ch.ethz.ganymed</groupId> 
 * <artifactId>ganymed-ssh2</artifactId>
 * <version>build210</version> 
 * </dependency>
 */
public class TestScp {
	private static String IP = "192.168.8.100";
	private static int PORT = 5044;
	private static String USER = "zengyc";
	private static String PASSWORD = null;
	private static Connection connection = new Connection(IP, PORT);
	private static String PRIVATEKEY = "C:\\ZENGYUCHAO";// 私钥文件
	private static boolean usePassword = false;// 使用用户名和密码来进行登录验证

	/**
	 * ssh用户登录验证，使用用户名和密码来认证
	 * 
	 * @param user
	 * @param password
	 * @return
	 */
	public static boolean isAuthedWithPassword(String user, String password) {
		try {
			return connection.authenticateWithPassword(user, password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ssh用户登录验证，使用用户名、私钥、密码来认证 其中密码如果没有可以为null，生成私钥的时候如果没有输入密码，则密码参数为null
	 * 
	 * @param user
	 * @param privateKey
	 * @param password
	 * @return
	 */
	public static boolean isAuthedWithPublicKey(String user, File privateKey, String password) {
		try {
			return connection.authenticateWithPublicKey(user, privateKey, password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isAuth() {
		if (usePassword) {
			return isAuthedWithPassword(USER, PASSWORD);
		} else {
			return isAuthedWithPublicKey(USER, new File(PRIVATEKEY), PASSWORD);
		}
	}

	public static void getFile(String remoteFile, String path) {
		try {
			connection.connect();
			boolean isAuthed = isAuth();
			if (isAuthed) {
				System.out.println("认证成功!");
				SCPClient scpClient = connection.createSCPClient();
				scpClient.get(remoteFile, path);
			} else {
				System.out.println("认证失败!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	public static void putFile(String localFile, String remoteTargetDirectory) {
		try {
			connection.connect();
			boolean isAuthed = isAuth();
			if (isAuthed) {
				SCPClient scpClient = connection.createSCPClient();
				scpClient.put(localFile, remoteTargetDirectory);
			} else {
				System.out.println("认证失败!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			connection.close();
		}
	}

	public static void main(String[] args) {
		try {
			// getFile("/home/users/zengyc/error.txt", "c://");
			putFile("c://aaa.txt", "/home/users/zengyc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
