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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaqx.listener.ApplicationListener;
import com.gaqx.util.DateUtils;
import com.gaqx.util.Dom4jUtils;

/**
 * 模块名称：WebConfig <br>
 * 功能描述：后台管理--系统页面配置类 <br>
 * 文档作者：雷志强 <br>
 * 创建时间：2013-8-21 下午02:17:02 <br>
 * 初始版本：V1.0 最初版本号 <br>
 * 修改记录： <br>
 * *************************************************<br>
 * 修改人：雷志强 <br>
 * 修改时间：2013-8-21 下午02:17:02 <br> wanghui1429179899
 * 修改内容： <br>
 * *************************************************<br>
 */
public class WebConfig {

	private static Logger log = Logger.getLogger(WebConfig.class);
	/**
	 * 功能描述：读取系统页面配置文件<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：2013-8-21 下午02:29:09<br>
	 * @return JSONObject 返回配置json对象
	 */
	@SuppressWarnings("unchecked")
	public JSONObject loadWebConfig() {
		JSONObject config = new JSONObject();
		String configXmlPath = ApplicationListener.webconfigFilePath;
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
					config.put(node.getName(), node.getText());
				}
			} catch (Exception e) {
				log.error("loadWebConfig[exception]", new Throwable(e));
			}
		} else {
			log.warn("loadWebConfig[messgae] - 系统配置文件不存在[" + configXmlPath + "]");
		}
		return config;
	}
	/**
	 * 功能描述：读取系统页面配置文件<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：2013-8-21 下午02:29:09<br>
	 * @return JSONObject 返回配置json对象
	 */
	@SuppressWarnings("unchecked")
	public JSONObject loadConfigs() {
		JSONObject config = new JSONObject();
		String configXmlPath = ApplicationListener.webconfigFilePath;
		File file = new File(configXmlPath);
		if (file.exists()) {
			try {
				Document doc = Dom4jUtils.readXml(file);
				Element root = doc.getRootElement();
				List<Element> children = root.elements();
				int childLength = children.size();
				Element node = null;
				JSONObject nodeInfo = null;
				for (int i = 0; i < childLength; i++) {
					node = children.get(i);
					nodeInfo = new JSONObject();
					nodeInfo = Dom4jUtils.getNodeAttr(node);
					config.put(node.getName(), nodeInfo);
				}
			} catch (Exception e) {
				log.error("loadWebConfig[exception]", new Throwable(e));
			}
		} else {
			log.warn("loadWebConfig[messgae] - 系统配置文件不存在[" + configXmlPath + "]");
		}
		return config;
	}
	/**
	 * 功能描述：读取系统页面配置文件并转所有节点转成json数组<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：2013-8-21 下午02:29:09<br>
	 * @return JSONObject 返回配置json对象
	 */
	@SuppressWarnings("unchecked")
	public JSONArray loadConfigList() {
		JSONArray json = new JSONArray();
		JSONObject config = new JSONObject();
		String configXmlPath = ApplicationListener.webconfigFilePath;
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
				log.error("loadWebConfigList[exception]", new Throwable(e));
			}
		} else {
			log.warn("loadWebConfigList[messgae] - 系统配置文件不存在[" + configXmlPath + "]");
		}
		return json;
	}
	/**
	 * 功能描述：在WebConfig配置文件添加结点信息<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：2013-9-13 下午02:10:22<br>
	 * @param configName 结点名
	 * @param title 节点标题
	 * @param configValue 节点值
	 * @return JSONObject 返回操作状态
	 */
	public JSONObject saveConfig(String configName,String title,String configValue) {
		JSONObject json = new JSONObject();
		json.put("state", false);
		json.put("error", "");
		String configXmlPath = ApplicationListener.webconfigFilePath;
		File file = new File(configXmlPath);
		if (file.exists()) {
			try {
				Document doc = Dom4jUtils.readXml(file);
				Element root = doc.getRootElement();
				Element node = root.element(configName);
				if (node == null) {
					node = Dom4jUtils.addNode(root, configName, null, configValue);
				} else {
					node.setText(configValue);
				}
				node.addAttribute("title", title);
				Dom4jUtils.writeXml(doc, configXmlPath);
				json.put("state", true);
			} catch (Exception e) {
				json.put("state", false);
				json.put("error", e.getMessage());
				log.error("saveConfig[exception]", new Throwable(e));
			}
		} else {
			json.put("state", false);
			json.put("error", "系统配置文件不存在[" + configXmlPath + "]");
			log.warn("loadWebConfig[messgae] - 系统配置文件不存在[" + configXmlPath + "]");
		}
		return json;
	}
	/**
	 * 功能描述：在WebConfig配置文件添加结点对象信息<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：2013-9-13 下午02:10:22<br>
	 * @param nodeInfo 结点对象(nodeName:节点名称,value:节点值,必须要有这两个参数)
	 * @return JSONObject 返回操作状态
	 */
	@SuppressWarnings("unchecked")
	public JSONObject saveAttConfig(HashMap<String, String> nodeInfo) {
		JSONObject json = new JSONObject();
		json.put("state", false);
		json.put("error", "");
		String configXmlPath = ApplicationListener.webconfigFilePath;
		File file = new File(configXmlPath);
		if (file.exists()) {
			try {
				Document doc = Dom4jUtils.readXml(file);
				Element root = doc.getRootElement();
				String nodeName = nodeInfo.get("nodeName");
				Element node = root.element(nodeName);
				if (node == null) {
					node = root.addElement(nodeName);
				}
				nodeInfo.remove("nodeName");
				Set<Entry<String, String>> set = nodeInfo.entrySet();
				Iterator<?> iterator = set.iterator();
				Entry<String, String> entry = null;
				while(iterator.hasNext()){
					entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().toLowerCase().equals("value")){
						node.setText(entry.getValue());
					}else{
						node.addAttribute(entry.getKey(), entry.getValue());
					}
				}
				Dom4jUtils.writeXml(doc, configXmlPath);
				json.put("state", true);
			} catch (Exception e) {
				json.put("state", false);
				json.put("error", e.getMessage());
				log.error("saveAttConfig[exception]", new Throwable(e));
			}
		} else {
			json.put("state", false);
			json.put("error", "系统配置文件不存在[" + configXmlPath + "]");
			log.warn("saveAttConfig[messgae] - 系统配置文件不存在[" + configXmlPath + "]");
		}
		return json;
	}
	
	/**
	 * 功能描述：从系统页面配置信息获取指定节点的值<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：2013-8-21 下午02:29:27<br>
	 * @param configName 节点名称
	 * @return String 节点值
	 */
	public static String getConfig(String configName) {
		String configValue = null;
		WebConfig wc = new WebConfig();
		JSONObject config = wc.loadWebConfig();
		//System.out.println(config);
		configValue = config.getString(configName);
		return configValue;
	}
	/**
	 * 功能描述：从系统页面配置信息获取指定节点的对象信息(节点名称，属性、值)<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：2013-8-21 下午02:29:27<br>
	 * @param configName 节点名称
	 * @return JSONObject 节点对象(节点名称，属性，值)
	 */
	public JSONObject getAttConfig(String configName) {
		JSONObject json = new JSONObject();
		WebConfig wc = new WebConfig();
		JSONObject config = wc.loadConfigs();
		json = config.getJSONObject(configName);
		return json;
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
		String configXmlPath = ApplicationListener.webconfigFilePath;
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

//	/******以下为测试代码******/
//	public static void main(String[] args) {
//		WebConfig config = new WebConfig();
//		System.out.println(config.getAttConfig("getJingPingService"));
//		System.out.println(config.getConfig("getJingPingService"));
//		testLoadWebConfig();
//		testLoadConfigs();
//		testLoadConfigList();
//		testSaveConfig();
//		testSaveAttConfig();
//		testGetConfig();
//		testGetAttConfig();
//		tesRemoveConfig();
//		System.out.println("OK");
//	}
	
	public static void testLoadWebConfig(){
		WebConfig wc = new WebConfig();
		JSONObject config = wc.loadWebConfig();
		System.out.println("config="+config);
	}
	
	public static void testLoadConfigs(){
		WebConfig wc = new WebConfig();
		JSONObject config = wc.loadConfigs();
		System.out.println("config="+config);
	}
	
	public static void testLoadConfigList(){
		WebConfig wc = new WebConfig();
		JSONArray json = wc.loadConfigList();
		System.out.println("json="+json);
	}
	
	public static void testSaveConfig(){
		WebConfig wc = new WebConfig();
		String nodeName = "serverUrl";
		String title = "接口服务地址";
		String configValue = "http://192.168.10.148:8080/gw/query.do?a=1&b=1&c=3";
		JSONObject json = wc.saveConfig(nodeName, title, configValue);
		System.out.println("config="+json);
	}
	
	public static void testSaveAttConfig(){
		WebConfig wc = new WebConfig();
		HashMap<String, String> nodeInfo = new LinkedHashMap<String, String>();
		nodeInfo.put("nodeName", "aaaa");
		nodeInfo.put("title", "测试节点名称");
		nodeInfo.put("datetime", DateUtils.date2String("yyyy-MM-dd HH:mm:ss"));
		nodeInfo.put("value", "这里是测试节点的值--更新");
		JSONObject json = wc.saveAttConfig(nodeInfo);
		System.out.println("config="+json);
	}
	
	public static void testGetConfig(){
		String value = WebConfig.getConfig("uploadForld");
		System.out.println("value="+value);
	}
	
	public static void testGetAttConfig(){
		WebConfig wc = new WebConfig();
		JSONObject json = wc.getAttConfig("uploadForld");
		System.out.println("json="+json);
	}
	
	public static void tesRemoveConfig(){
		WebConfig wc = new WebConfig();
		JSONObject json = wc.removeConfig("nodeName-19b25c8e-81bd-4a0f-a91c-7933a64fdc3a");
		System.out.println("json="+json);
	}
}
