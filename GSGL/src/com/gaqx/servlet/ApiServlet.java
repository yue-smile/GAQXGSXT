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
/**
 * 这里是文件说明
 */
package com.gaqx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.gaqx.service.DataImpl;
import com.gaqx.util.HttpRequest;

/**
 * 模块名称：该模块名称
 * 功能描述：该文件详细功能描述
 * 文档作者：雷志强
 * 创建时间：2017年10月23日 下午1:49:51
 * 初始版本：V1.0
 * 修改记录：
 * *************************************************
 * 修改人：雷志强
 * 修改时间：2017年10月23日 下午1:49:51
 * 修改内容：
 * *************************************************
 */
public class ApiServlet extends HttpServlet {
	
	private static Logger log = Logger.getLogger(ApiServlet.class);

	private static final long serialVersionUID = 1L;
	//Api方法枚举类
	public enum ApiMethod{
		//查询所有数据列表
		listAll,
		//查询记录总数
		countAll,
		//保存数据
		saveData,
		//修改数据
		editData,
		//删除数据
		deleteData,
		//分页查询
		listByPage
	}
	
	public ApiServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//获取请求参数
		Map<String,String[]> pMap = request.getParameterMap();
		//将请求参数转换成json对象
		JSONObject paramJson = HttpRequest.paramMapToJson(pMap);
		log.info("paramJson="+paramJson);
		String method = "";
		if(paramJson.containsKey("method")){
			method = paramJson.getString("method");
		}
		paramJson.put("userno", request.getSession().getAttribute("userno"));
		ApiMethod apiMethod = ApiMethod.valueOf(method);
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("msg", "");
		json.put("data", "");
		DataImpl dataImpl = new DataImpl();
		int result = -1;
		switch (apiMethod) {
			case listAll:
				List<JSONObject> data = dataImpl.listAll(paramJson);
				json.put("data", data);
				break;
			case listByPage:
				List<JSONObject> datapage = dataImpl.listByPage(paramJson);
				json.put("data", datapage);
				break;	
			case countAll:
				int count = dataImpl.countAll(paramJson);
				json.put("data", count);
				break;
			case saveData:
				JSONObject info = dataImpl.saveData(paramJson);
				if(info.getIntValue("result")>0){
					json.put("data", info.getString("dataKey"));
				}
				break;
			case editData:
				result = dataImpl.editData(paramJson);
				json.put("data", result);
				break;
			case deleteData:
				result = dataImpl.editData(paramJson);
				json.put("data", result);
				break;
			default:
				json.put("code", 101);
				json.put("msg", "找不到["+method+"]对应的接口！");
				break;
		}
		out.print(json.toJSONString());
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
	}
}
