package com.how2java.tmall.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 散列加密之32位哈希值的MD5算法，调用JDK里的API ps：准确来说散列加密不是加密算法，因为它是不可逆的（只能加密，不能解密）
 */
public class SaltUtil {

	private static char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static void main(String[] args) throws Exception {
		// 注册
		String input = "123456";
		System.out.println("MD5加密" + "\n" + "明文：" + input + "\n");
		String hashToDb = MD5WithSalt(input);
		System.out.println("带盐密文(存到数据库)：" + hashToDb);

		// 登录
		String input2 = "1234567";
		if (varify(input2, hashToDb)) {
			System.out.println("密码正确");
		} else {
			System.out.println("密码错误");
		}

	}

	/**
	 * @params: [inputStr] 输入明文
	 * @Descrption: 不加盐MD5
	 */
	public static String MD5WithoutSalt(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");// 申明使用MD5算法,更改参数为"SHA"就是SHA算法了
			return byte2HexStr(md.digest(inputStr.getBytes()));// 哈希计算,转换输出
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}

	}

	/**
	 * @params: [inputStr, type]
	 *          inputStr是输入的明文;type是处理类型，0表示注册存hash值到库时，1表示登录验证时
	 * @Descrption: MD5加盐，盐的获取分两种情况;输入明文加盐；输出密文带盐(将salt存储到hash值中)
	 */
	public static String MD5WithSalt(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");// 申明使用MD5算法,更改参数为"SHA"就是SHA算法了

			String salt = null;
			salt = salt();

			String inputWithSalt = inputStr + salt;// 加盐，输入加盐
			String hashResult = byte2HexStr(md.digest(inputWithSalt.getBytes()));// 哈希计算,转换输出
			System.out.println("加盐密文：" + hashResult);
			System.out.println("盐值：" + salt);

			/* 将salt存储到hash值中，用于登陆验证密码时使用相同的盐 */
			char[] cs = new char[48];
			for (int i = 0; i < 48; i += 3) {
				cs[i] = hashResult.charAt(i / 3 * 2);
				cs[i + 1] = salt.charAt(i / 3);// 输出带盐，存储盐到hash值中;每两个hash字符中间插入一个盐字符
				cs[i + 2] = hashResult.charAt(i / 3 * 2 + 1);
			}
			hashResult = new String(cs);
			return hashResult;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	/**
	 * @params: [inputStr2, queriedHash]
	 *          inputStr2是用户登录时候输入的明文;queriedHash是用户注册时候存储到数据库的hash值
	 * @Descrption: 用该方法验证用户输入的密码是否正确
	 */
	public static boolean varify(String inputStr2, String queriedHash) {
		boolean result = false;
		try {
			MessageDigest md;

			md = MessageDigest.getInstance("MD5");
			// 申明使用MD5算法,更改参数为"SHA"就是SHA算法了
			String salt = getSaltFromHash(queriedHash);
			String inputWithSalt2 = inputStr2 + salt;
			String hashResult2 = byte2HexStr(md.digest(inputWithSalt2
					.getBytes()));// 哈希计算,转换输出

			char[] cs = new char[48];
			for (int i = 0; i < 48; i += 3) {
				cs[i] = hashResult2.charAt(i / 3 * 2);
				cs[i + 1] = salt.charAt(i / 3);// 输出带盐，存储盐到hash值中;每两个hash字符中间插入一个盐字符
				cs[i + 2] = hashResult2.charAt(i / 3 * 2 + 1);
			}
			hashResult2 = new String(cs);

			if (hashResult2.equals(queriedHash)) {
				return true;
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @return: salt
	 * @params:
	 * @Descrption: 自定义简单生成盐，是一个随机生成的长度为16的字符串，每一个字符是随机的十六进制字符
	 */
	public static String salt() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(16);
		for (int i = 0; i < sb.capacity(); i++) {
			sb.append(hex[random.nextInt(16)]);
		}
		return sb.toString();
	}

	/**
	 * @return: 十六进制字符串
	 * @params: [bytes]
	 * @Descrption: 将字节数组转换成十六进制字符串
	 */
	private static String byte2HexStr(byte[] bytes) {
		/**
		 * @Author: DavidHuang
		 * @Time: 19:41 2018/5/10
		 * @return: java.lang.String
		 * @params: * @param bytes
		 * @Descrption:
		 */
		int len = bytes.length;
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < len; i++) {
			byte byte0 = bytes[i];
			result.append(hex[byte0 >>> 4 & 0xf]);
			result.append(hex[byte0 & 0xf]);
		}
		return result.toString();
	}

	/**
	 * @return: 提取的salt
	 * @params: [hash] 3i byte带盐的hash值,带盐方法与MD5WithSalt中相同
	 * @Descrption: 从库中查找到的hash值提取出的salt
	 */
	public static String getSaltFromHash(String hash) {
		StringBuilder sb = new StringBuilder();
		char[] h = hash.toCharArray();
		for (int i = 0; i < hash.length(); i += 3) {
			sb.append(h[i + 1]);
		}
		return sb.toString();
	}

}