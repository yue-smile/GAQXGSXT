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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaqx.listener.ApplicationListener;
import com.gaqx.util.Dom4jUtils;

/**
 * 模块名称：QuerySqlConfig									<br>
 * 功能描述：该文件详细功能描述							<br>
 * 文档作者：雷志强									<br>
 * 创建时间：2013-9-26 下午04:48:02					<br>
 * 初始版本：V1.0	最初版本号							<br>
 * 修改记录：											<br>
 * *************************************************<br>
 * 修改人：雷志强										<br>
 * 修改时间：2013-9-26 下午04:48:02					<br>
 * 修改内容：											<br>
 * *************************************************<br>
 */
public class QuerySqlConfig {
	
	private static Logger log = Logger.getLogger(QuerySqlConfig.class.getSimpleName());
	
	@SuppressWarnings("unchecked")
	public JSONObject loadConfig(){
		JSONObject config = new JSONObject();
		String configXmlPath = ApplicationListener.sqlconfigFilePath;
		File file = new File(configXmlPath);
		if (file.exists()) {
			try {
				Document doc = Dom4jUtils.readXml(file);
				Element rootNode = doc.getRootElement();
				List<Element> configList = rootNode.elements();
				int nodeLength = configList.size();
				Element node = null;
				for (int i = 0; i < nodeLength; i++) {
					node = configList.get(i);
					config.put(node.getName(), node.getText());
				}
			} catch (Exception e) {
				log.error("loadSqlConfig[exception]", new Throwable(e));
			}
		} else {
			log.warn("loadSqlConfig[messgae] - 系统配置文件不存在[" + configXmlPath + "]");
		}
		return config;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject loadManagerConfig(){
		JSONObject config = new JSONObject();
		String configXmlPath = ApplicationListener.sqlconfigFilePath;
		File file = new File(configXmlPath);
		if (file.exists()) {
			try {
				Document doc = Dom4jUtils.readXml(file);
				Element rootNode = doc.getRootElement();
				List<Element> configList = rootNode.elements();
				int nodeLength = configList.size();
				Element node = null;
				for (int i = 0; i < nodeLength; i++) {
					node = configList.get(i);
					config.put(node.getName(), node.getText());
				}
			} catch (Exception e) {
				log.error("loadManagerConfig[exception]", new Throwable(e));
			}
		} else {
			log.warn("loadManagerConfig[messgae] - 系统配置文件不存在[" + configXmlPath + "]");
		}
		return config;
	}
	
	/**
	 * 功能描述：读取配置文件返回Json数组<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Nov 3, 2016 2:01:07 PM<br>
	 * @return JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray loadConfigList(){
		JSONArray json = new JSONArray();
		JSONObject config = new JSONObject();
		String configXmlPath = ApplicationListener.sqlconfigFilePath;
		File file = new File(configXmlPath);
		if (file.exists()) {
			try {
				Document doc = Dom4jUtils.readXml(file);
				Element root = doc.getRootElement();
				List<Element> children = root.elements();
				int childLength = children.size();
				Element node = null;
				for (int i = 0; i < childLength; i++) {
					node = children.get(i);
					config = Dom4jUtils.getNodeAttr(node);
					json.add(config);
				}
			} catch (Exception e) {
				log.error("loadConfig[exception]", new Throwable(e));
			}
		} else {
			log.warn("loadConfig[messgae] - 系统配置文件不存在[" + configXmlPath + "]");
		}
		return json;
	}
	
	/**
	 * 功能描述：添加数据访问接口配置<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Nov 3, 2016 1:56:14 PM<br>
	 * @param nodeName 数据访问接口
	 * @param title 数据接口说明
	 * @param value 数据接口SQL语句
	 * @return JSONObject 返回操作状态
	 */
	public JSONObject saveConfig(String nodeName,String title,String value){
		JSONObject json = new JSONObject();
		json.put("state", false);
		json.put("error", "");
		String configXmlPath = ApplicationListener.sqlconfigFilePath;
		File file = new File(configXmlPath);
		if (file.exists()) {
			try {
				Document doc = Dom4jUtils.readXml(file);
				Element root = doc.getRootElement();
				Element node = root.element(nodeName);
				if (node == null) {
					node = root.addElement(nodeName);
				}
				node.setText(value);
				node.addAttribute("title", title);
				Dom4jUtils.writeXml(doc, configXmlPath);
				json.put("state", true);
			} catch (Exception e) {
				json.put("state", false);
				json.put("error", e.getMessage());
				log.error("addConfig[exception]", new Throwable(e));
			}
		} else {
			json.put("state", false);
			json.put("error", "系统配置文件不存在[" + configXmlPath + "]");
			log.warn("addConfig[messgae] - 系统配置文件不存在[" + configXmlPath + "]");
		}
		return json;
	}
	
	/**
	 * 功能描述：从系统页面配置信息获取指定节点的值<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：2013-8-21 下午02:29:27<br>
	 * @param configName
	 * @return String
	 */
	public String getConfigSQL(String configName){
		JSONObject sqlConfig = loadConfig();
		String value = sqlConfig.getString(configName);
		if(value==null){
			value = "";
		}
		return value;
	}
	
	/**
	 * 读取后台管理的xml文件
	 * @param configName
	 * @return
	 * @author wangyue
	 */
	public String getManagerConfigSQL(String configName){
		JSONObject sqlConfig = loadManagerConfig();
		String value = sqlConfig.getString(configName);
		if(value==null){
			value = "";
		}
		return value;
	}
	
	
	/**
	 * 功能描述：移除指定节点名称的节点对象<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Nov 3, 2016 11:32:56 AM<br>
	 * @param configName 节点名称
	 * @return void
	 */
	public JSONObject removeConfig(String configName) {
		JSONObject json = new JSONObject();
		json.put("state", false);
		json.put("error", "");
		String configXmlPath = ApplicationListener.sqlconfigFilePath;
		File file = new File(configXmlPath);
		if (file.exists()) {
			try {
				Document doc = Dom4jUtils.readXml(file);
				Element root = doc.getRootElement();
				Element node = root.element(configName);
				if (node != null) {
					root.remove(node);
				}
				Dom4jUtils.writeXml(doc, configXmlPath);
				json.put("state", true);
			} catch (Exception e) {
				json.put("state", false);
				json.put("error", e.getMessage());
				log.error("removeConfig[exception]", new Throwable(e));
			}
		} else {
			json.put("state", false);
			json.put("error", "系统配置文件不存在[" + configXmlPath + "]");
			log.warn("removeConfig[messgae] - 系统配置文件不存在[" + configXmlPath + "]");
		}
		return json;
	}
	/**
	 * 功能描述：替换SQL语句中的参数<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Nov 3, 2016 1:47:15 PM<br>
	 * @param sql
	 * @param paraMap
	 * @return
	 * @return String
	 */
	public String getReplaceSqlParam(String sql,JSONObject paraMap){
		String newSql = sql;
		if(sql != null){
			if(paraMap!=null){
				Set<Entry<String, Object>> set = paraMap.entrySet();
				Iterator<Entry<String, Object>> iterator = set.iterator();
				Entry<String, Object> entry = null;
				String key = null;
				String val = null;
				while(iterator.hasNext()){
					entry = iterator.next();
					key = entry.getKey();
					val = String.valueOf(entry.getValue());
					if(val==null){
						val="null";
					}else{
						if(val.startsWith("javaclass:")){
							String[] tmpArr = val.split("\\:");
							System.out.println(tmpArr[0]+","+tmpArr[1]+","+tmpArr[2]);
							val = ParamConfig.getColumnValue(tmpArr[1], tmpArr[2]);
							tmpArr = null;
						}else{
							val = val.replaceAll("\\$", "\\\\$");
						}
					}
					newSql = newSql.replaceAll("#"+key+"#", val);
				}
			}
		}
		return newSql;
	}
	/**
	 * 功能描述：替换SQL语句中的参数<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Nov 3, 2016 1:47:15 PM<br>
	 * @param sql
	 * @param paraMap
	 * @return
	 * @return String
	 */
	public String getReplaceSqlParam(String sql,HashMap<String, String> paraMap){
		String newSql = sql;
		if(sql != null){
			if(paraMap!=null){
				Set<Entry<String, String>> set = paraMap.entrySet();
				Iterator<Entry<String, String>> iterator = set.iterator();
				Entry<String, String> entry = null;
				String key = null;
				String val = null;
				while(iterator.hasNext()){
					entry = iterator.next();
					key = entry.getKey();
					val = String.valueOf(entry.getValue());
					if(val==null){
						val="null";
					}else{
						if(val.startsWith("javaclass:")){
							String[] tmpArr = val.split("\\:");
							System.out.println(tmpArr[0]+","+tmpArr[1]+","+tmpArr[2]);
							val = ParamConfig.getColumnValue(tmpArr[1], tmpArr[2]);
							tmpArr = null;
						}else{
							val = val.replaceAll("\\$", "\\\\$");
						}
					}
					newSql = newSql.replace("#"+key+"#", val);
				}
			}
		}
		return newSql;
	}
	
	public List<String> getSqlParam(String sql){
		int length = 0;
		if(sql!=null){
			length = sql.length();
		}
		List<String> list = new ArrayList<String>();
		List<Integer> indexList = new ArrayList<Integer>();
		for(int i=0;i<length;i++){
			if(sql.charAt(i)=='#'){
				indexList.add(i);
			}
		}
		String param = "";
		int firstIndex = 0;
		int nextIndex = 0;
		for(int i=0;i<indexList.size()-1;i=i+2){
			firstIndex = indexList.get(i);
			nextIndex = indexList.get(i+1);
			param = sql.substring(firstIndex+1, nextIndex);
			list.add(param);
		}
		return list;
	}
	
	public static void main(String[] args) {
		testLoadConfig();
//		String sql = "select * from t_table where id=#id# and name=#name# and age=#age#";
//		QuerySqlConfig config = new QuerySqlConfig();
//		String baseSql = config.getConfigSQL("loadRecordById");
//		System.out.println(baseSql);
//		JSONObject paraMap = new JSONObject();
//		paraMap.put("tableName", "T_DIC");
//		paraMap.put("condition", "id=5");
//		sql = config.getReplaceSqlParam(baseSql, paraMap);
//		System.out.println(sql);
//		System.out.println("OK");
	}
	
	public static void testLoadConfig(){
		QuerySqlConfig config = new QuerySqlConfig();
		JSONArray json = config.loadConfigList();
		System.out.println(json);
		String sql = "begin  ";
		for (Object object : json) {
			JSONObject record = (JSONObject)object;
			sql += "insert into t_webconfig (id, key, value, type, description, author) values ('"+ParamConfig.getuid()+"','"+record.getString("nodeName")+"','"+record.getString("code")+"','errorconfig','"+record.getString("nodeValue")+"','雷志强');\n";
		}
		sql += " end;";
		System.out.println(sql);
	}
	
	public static void testSaveConfig(){
		QuerySqlConfig config = new QuerySqlConfig();
		JSONObject json = config.saveConfig("addVisitLog", "用户访问日志记录12", "insert into T_VISITLOG(ip,sid,country,province,city) values('#ip#','#sid#','#country#','#province#','#city#')");
		System.out.println(json);
	}
	
	public static void test1(){
		String sql="insert into T_Test(id,name,age,jqbm,userpwd) values('#id#','#name#','#age#','#jqbm#','#userPwd#')";
		System.out.println("sql="+sql);
		JSONObject param = new JSONObject();
		param.put("id", 1234);
		param.put("name", "asasas");
		param.put("age", 22);
		param.put("jqbm", "javaclass:getJqbm:null");
		param.put("userPwd", "javaclass:getMd5pwd:null");
		QuerySqlConfig config = new QuerySqlConfig();
		sql = config.getReplaceSqlParam(sql, param);
		System.out.println("sql="+sql);
		//String sql = "aa=#dd#=CC=1.$";
		//sql = sql.replaceAll("#dd#", "2.01$");
		String string = ParamConfig.getColumnValue("getJqbm",null);
		System.out.println(string);
		string = ParamConfig.getColumnValue("getMd5pwd","123456");
		System.out.println(string);
		
	}
}
