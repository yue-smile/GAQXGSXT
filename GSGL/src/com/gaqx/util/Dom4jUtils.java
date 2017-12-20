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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 模块名称：Dom4jUtils									<br>
 * 功能描述：该文件详细功能描述							<br>
 * 文档作者：雷志强									<br>
 * 创建时间：2014-10-30 下午04:53:13					<br>
 * 初始版本：V1.0	最初版本号							<br>
 * 修改记录：											<br>
 * *************************************************<br>
 * 修改人：雷志强										<br>
 * 修改时间：2014-10-30 下午04:53:13					<br>
 * 修改内容：											<br>
 * *************************************************<br>
 */
public class Dom4jUtils {
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(Dom4jUtils.class.getSimpleName());
	public Dom4jUtils(){
		
	}
	
	public static Document readXml(File xmlF) throws DocumentException{
		if(!xmlF.exists()){
			return null;
		}
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(xmlF);
		return doc;
	}
	
	public static Document readXml(InputStream is) throws DocumentException{
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(is);
		return doc;
	}
	
	public static void writeXml(Document doc, String filepath) throws TransformerException, IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		OutputStream os = new FileOutputStream(new File(filepath));
		XMLWriter writer = new XMLWriter(new OutputStreamWriter(os,"UTF-8"),format);
		writer.write(doc);
		writer.close();
		os.close();
	}
	
	public static Document createDocument() {
		Document doc = DocumentHelper.createDocument();
		return doc;
	}
	/**
	 * 功能描述：获取指定节点的属性信息<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Nov 3, 2016 10:52:32 AM<br>
	 * @param node
	 * @return
	 * @return JSONObject
	 */
	public static JSONObject getNodeAttr(Element node) {
		JSONObject nodeAttr = new JSONObject();
		List<?> attrs = node.attributes();
		int attLength = attrs.size();
		DefaultAttribute attr = null;
		for (int j = 0; j < attLength; j++) {
			attr = (DefaultAttribute)attrs.get(j);
			nodeAttr.put(attr.getName(), attr.getText());
			nodeAttr.put("nodeName", node.getName());
			nodeAttr.put("nodeValue", node.getText());
		}
		return nodeAttr;
	}
	
	public static Element addRootNode(Document doc,String nodeName,JSONObject attrs){
		Element root = doc.addElement(nodeName);
		if(attrs!=null){
			Iterator<String> iterator = attrs.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = (String)attrs.get(key);
				if (value == null || value.equals("null")) {
					value = "";
				}
				root.addAttribute(key, value);
			}
		}
		return root;
	}
	
	public static Element addNode(Element node,String nodeName,JSONObject attrs,String nodeValue){
		Element child = node.addElement(nodeName);
		if(attrs!=null){
			Iterator<String> iterator = attrs.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = (String)attrs.get(key);
				if (value == null || value.equals("null")) {
					value = "";
				}
				child.addAttribute(key, value);
			}
		}
		nodeValue = nodeValue==null?"":nodeValue;
		child.setText(nodeValue);
		return child;
	}
	/**
	 * 功能描述：将XML文件中的指定节点转换成Json数组对象<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Nov 3, 2016 11:02:50 AM<br>
	 * @param nodeList
	 * @return
	 * @return JSONArray
	 */
	public static JSONArray getXmlNodeInfoList(Iterator<Element> nodeList) {
		JSONArray list = new JSONArray();
		Element node = null;
		while(nodeList.hasNext()){
			node = nodeList.next();
			list.add(getNodeAttr(node));
		}
		return list;
	}
	
	public static void main(String[] args) {
		try {
			//testCreateDataXml();
			testCreateDataXml();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("OK");
	}
	
	public static void testCreateDataXml(){
		String xmlFilePath = "d:/test_003.xml";
		Document doc = Dom4jUtils.createDocument();
		Element root = Dom4jUtils.addRootNode(doc, "rows", null);
		root.addAttribute("title", "数据列表");
		int count = 100;
		Element row = null;
		for(int i=0;i<count;i++){
			row = root.addElement("row");
			row.addAttribute("id", String.valueOf(i+1));
			row.addAttribute("title", "标题-"+i);
			row.addAttribute("name", "名称-"+i);
			row.addAttribute("lon", "经度-"+i);
			row.addAttribute("lat", "纬度-"+i);
		}
		try {
			OutputStream os = new FileOutputStream(new File(xmlFilePath));
			Dom4jUtils.writeXml(doc, xmlFilePath);
			os.close();
			os = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testCreateXml(){
		String xmlFilePath = "d:/test/test_003.xml";
		Document doc = Dom4jUtils.createDocument();
		JSONObject attrs = new JSONObject();
		attrs.put("ID", "0014");
		attrs.put("title", "产品信息");
		attrs.put("name", "");
		attrs.put("value", null);
		Element root = Dom4jUtils.addRootNode(doc, "productInfo", attrs);
		attrs = new JSONObject();
		attrs.put("title", "产品存放地址");
		attrs.put("ID", "003");
		Element child = Dom4jUtils.addNode(root, "productPath", null, "d:/space/sys_software/mapgis/test/SURF");
		child.addAttribute("title", "产品存放地址");
		Element productNaming = root.addElement("productNaming");
		productNaming.addAttribute("title", "产品命名规则");
		productNaming.setText("HUBEI_HOU_PRE");
		Element productPath = root.addElement("productPath");
		productPath.addAttribute("title", "产品存放地址");
		productPath.setText("d:/space/sys_software/mapgis/test/SURF");
		Element test = root.addElement("test");
		test.addAttribute("title", "产品存放地址");
		test.addAttribute("datetime", DateUtils.date2String("yyyy-MM-dd HH:mm:ss"));
		//test.setText("d:/space/sys_software/mapgis/test/SURF");
		try {
			OutputStream os = new FileOutputStream(new File(xmlFilePath));
			Dom4jUtils.writeXml(doc, xmlFilePath);
			os.close();
			os = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testReadXml() throws DocumentException{
		String xmlFilePath = "d:/test/test_003.xml";
		Document doc = Dom4jUtils.readXml(new File(xmlFilePath));
		Element root = doc.getRootElement();
		System.out.println(root);
		List<?> list = root.elements();
		int count = list.size();
		System.out.println(count);
		Element element = null;
		//List attrs = null;
		//Attribute attr = null;
		for(int i=0;i<count;i++){
			element = (Element)list.get(i);
			System.out.println(Dom4jUtils.getNodeAttr(element));
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void testReadDataXml() throws DocumentException{
		String xmlFilePath = "d:/test/test_001.xml";
		Document doc = Dom4jUtils.readXml(new File(xmlFilePath));
		System.out.println(doc);
		Element rootElement = doc.getRootElement();
		Iterator<Element> iterator = rootElement.elementIterator("row");
		JSONArray list = Dom4jUtils.getXmlNodeInfoList(iterator);
		System.out.println(list);
	}
	
	public static void testReadXmlNode() throws DocumentException{
		String xmlFilePath = "d:/space/flow_hubei.xml";
		Document doc = Dom4jUtils.readXml(new File(xmlFilePath));
		Element rootElement = doc.getRootElement();
		Element productPath = rootElement.element("productPath");
		System.out.println(productPath.getText());
	}
	
	public static void testUpdateXml() throws DocumentException{
		String xmlFilePath = "d:/space/flow_hubei.xml";
		Document doc = Dom4jUtils.readXml(new File(xmlFilePath));
		Element root = doc.getRootElement();
//		HashMap<String, String> attrs = new HashMap<String, String>();
//		Element child = Dom4jUtils.addNode(root, "productResultLine", null, "d:/space/line.png");
//		child.setAttributeValue("title", "等值线路径");
//		child = Dom4jUtils.addNode(root, "productResultReg", null, "d:/space/reg.png");
//		child.setAttributeValue("title", "色斑图路径");
		Element node = root.elementByID("012");
		node.setText("d:/space/line_update111.png");
		node.addAttribute("column", "RegResult");
		//root.remove(node);
		try {
			OutputStream os = new FileOutputStream(new File(xmlFilePath));
			Dom4jUtils.writeXml(doc, xmlFilePath);
			os.close();
			os = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
