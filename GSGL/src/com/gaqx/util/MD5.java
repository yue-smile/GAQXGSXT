/**   
 * 特别声明：本技术材料受《中华人民共和国着作权法》、《计算机软件保护条例》
 * 等法律、法规、行政规章以及有关国际条约的保护，武汉中地数码科技有限公
 * 司享有知识产权、保留一切权利并视其为技术秘密。未经本公司书面许可，任何人
 * 不得擅自（包括但不限于：以非法的方式复制、传播、展示、镜像、上载、下载）使
 * 用，不得向第三方泄露、透露、披露。否则，本公司将依法追究侵权者的法律责任。
 * 特此声明！
 * 
   Copyright (c) 2013,武汉中地数码科技有限公司
 */

package com.gaqx.util;

import java.security.MessageDigest;

/**
 * 模块名称：MD5										<br>
 * 功能描述：MD5加密类									<br>
 * 文档作者：雷志强									<br>
 * 创建时间：2013-9-22 上午11:20:15					<br>
 * 初始版本：V1.0	最初版本号							<br>
 * 修改记录：											<br>
 * *************************************************<br>
 * 修改人：雷志强										<br>
 * 修改时间：2013-9-22 上午11:20:15					<br>
 * 修改内容：											<br>
 * *************************************************<br>
 */
public class MD5 {
	
	private final static String[] hexDigits = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
	
	private static String byteArrayToHexString(byte[] bt){
		StringBuffer sb = new StringBuffer();
		int length = bt.length;
		for(int i=0;i<length;i++){
			sb.append(byteToHexString(bt[i]));
		}
		return sb.toString();
	}
	
	private static String byteToHexString(byte b){
		int n = b;
		if(n < 0){
			n = 256 + n;
		}
		int d1 = n/16;
		int d2 = n%16;
		return hexDigits[d1]+hexDigits[d2];
	}
	
	/**
	 * 功能描述：对字符串进行加密<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：2013-9-22 上午11:37:14<br>
	 * @param origin 需要加密的字符串
	 * @return
	 * @return String
	 */
	public static String MD5Encode(String origin){
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	
	public static void main(String[] args) {
		String string = MD5.MD5Encode("goldwind");
		System.out.println(string);
		string = MD5.MD5Encode("123456");
		System.out.println(string);
		//string = MD5.MD5Encode(string);
		//System.out.println(string);
	}
}
