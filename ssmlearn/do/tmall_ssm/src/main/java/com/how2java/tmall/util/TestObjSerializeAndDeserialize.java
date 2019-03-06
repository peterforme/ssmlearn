package com.how2java.tmall.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.how2java.tmall.pojo.BackUser;

/**
 * <p>
 * ClassName: TestObjSerializeAndDeserialize
 * <p>
 * <p>
 * Description: 测试对象的序列化和反序列
 * <p>
 * 
 * @author xudp
 * @version 1.0 V
 * @createTime 2014-6-9 下午03:17:25
 */
public class TestObjSerializeAndDeserialize {

	public static void main(String[] args) throws Exception {
		SerializePerson();// 序列化
	}

	/**
	 * MethodName: SerializePerson Description: 序列化Person对象
	 * 
	 * @author xudp
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void SerializePerson() throws FileNotFoundException,
			IOException {
		BackUser person = new BackUser();
		person.setName("gac");
		person.setPassword("gac");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(person);
		String cookieValue = baos.toString("ISO-8859-1");
		String encodedCookieValue = java.net.URLEncoder.encode(cookieValue,
				"UTF-8");
		System.out.println(encodedCookieValue);

		// 反序列化
		try {
			String decoderCookieValue = java.net.URLDecoder.decode(
					encodedCookieValue, "UTF-8");

			BackUser result = new BackUser();

			ByteArrayInputStream bais = new ByteArrayInputStream(
					cookieValue.getBytes("ISO-8859-1"));
			ObjectInputStream ios = new ObjectInputStream(bais);
			result = (BackUser) ios.readObject();
			System.out.println(result.getName() + "  " + result.getPassword());

		} catch (Exception e) {
		}

	}

}