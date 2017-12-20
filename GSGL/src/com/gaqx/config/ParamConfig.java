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

package com.gaqx.config;

import java.util.Random;

import com.gaqx.util.DateUtils;
import com.gaqx.util.MD5;

/**
 * 模块名称：ParamConfig									<br>
 * 功能描述：该文件详细功能描述							<br>
 * 文档作者：雷志强									<br>
 * 创建时间：Aug 29, 2016 2:41:56 PM					<br>
 * 初始版本：V1.0	最初版本号							<br>
 * 修改记录：											<br>
 * *************************************************<br>
 * 修改人：雷志强										<br>
 * 修改时间：Aug 29, 2016 2:41:56 PM					<br>
 * 修改内容：											<br>
 * *************************************************<br>
 */
public class ParamConfig {

	public enum ColumnMethod{
		//生成轨迹编号
		getTraceNo,
		//获取
		getMd5pwd,
		//生成项目编号
		getProjectNo,
		//生成方案编号
		getPlanNo,
		//生成工作组编号
		getTeamNo,
		//生成临时工作组编号
		getTempNo,
		//生成文件名称
		getFileName,
		//生成故障信息编号
		getEquipmentError,
		//生成设备编号
		getEquipment,
		//用户绘制的区域文件名称
		customArea
	}

	public static String getColumnValue(String methodStr,String paramStr){
		String columnValue = "";
		ColumnMethod method = null;
		String uid = getuid();
		try {
			method = ColumnMethod.valueOf(methodStr);
			switch (method) {
			case getTraceNo:
				columnValue = "Trace-"+DateUtils.date2String("yyyyMMddHHmmssSSS")+"-"+uid.substring(0,4);
				break;
			case getMd5pwd:
				columnValue = MD5.MD5Encode(paramStr);
				break;
			case getProjectNo:
				columnValue = "Project-"+DateUtils.date2String("yyyyMMddHHmmssSSS")+"-"+uid.substring(0,4);
				break;
			case getTeamNo:
				columnValue = "Team-"+DateUtils.date2String("yyyyMMddHHmmssSSS")+"-"+uid.substring(0,4);
				break;
			case getPlanNo:
				columnValue = "Proplan-"+DateUtils.date2String("yyyyMMddHHmmssSSS")+"-"+uid.substring(0,4);
				break;
			case getTempNo:
				columnValue = "TeamTemp-"+DateUtils.date2String("yyyyMMddHHmmssSSS")+"-"+uid.substring(0,4);
				break;
			case getEquipmentError:
				columnValue = "EquipmentError-"+DateUtils.date2String("yyyyMMddHHmmssSSS")+"-"+uid.substring(0,4);
				break;
			case getEquipment:
				columnValue = "Equipment-"+DateUtils.date2String("yyyyMMddHHmmssSSS")+"-"+uid.substring(0,4);
				break;
			case getFileName:
				columnValue = "FileName-"+DateUtils.date2String("yyyyMMddHHmmssSSS")+"-"+uid.substring(0,4);
				break;
			case customArea:
				columnValue = "CustomArea-"+DateUtils.date2String("yyyyMMddHHmmssSSS")+"-"+getRandomInt(6);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnValue;
	}

	
	public static String getuid(){
		String uid = java.util.UUID.randomUUID().toString();
		return uid;
	}
	
	public static long getRandomInt(int count){
		double c = (Math.random()+1)*Math.pow(10, count-1);
		return Math.round(c);
	}
	
	public static String getuserid(){
		String uid="UE";
		for(int i=0;i<9;i++){
			Random random1=new Random();
			Integer m=random1.nextInt(10);
			uid+=m.toString();
		}
		boolean falg=uid.matches("^.*(\\d)\\1{3}.*$")||uid.contains("1234")||uid.contains("2345")||uid.contains("3456")||uid.contains("4567")||uid.contains("5678")||uid.contains("6789");
		while (falg){
			uid="UE";
			for(int i=0;i<9;i++){
				Random random1=new Random();
				Integer m=random1.nextInt(10);
				uid+=m.toString();
			}
			falg=uid.matches("^.*(\\d)\\1{3}$.*")||uid.matches("^.*1234$.*")||uid.matches("^.*2345$.*")||uid.matches("^.*3456$.*")||uid.matches("^.*4567$.*")||uid.matches("^.*5678$.*")||uid.matches("^.*6789$.*");
		}
		return uid;
	}
	/**
	 * 功能描述：请用一句话描述这个方法实现的功能<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Aug 29, 2016 2:41:56 PM<br>
	 * @param args
	 * @return void
	 */
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			System.out.println(i+"-"+ParamConfig.getColumnValue("customArea", ""));
		}
	}
}
