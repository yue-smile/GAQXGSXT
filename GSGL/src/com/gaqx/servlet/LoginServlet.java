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


public class LoginServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(LoginServlet.class);

	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
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
		JSONObject json = new JSONObject();
		json.put("code", 0);
		json.put("msg", "");
		json.put("data", "");
		DataImpl dataImpl = new DataImpl();
		int result = -1;
		List<JSONObject> data = dataImpl.listAll(paramJson);
		if(data.size()==1){
			JSONObject user=data.get(0);
			String userpwd="";
			if(user.containsKey("userpwd"))userpwd=user.getString("userpwd");
			if(userpwd!=null&&userpwd!=""&&userpwd.equals(paramJson.getString("userpwd"))){
				request.getSession().setAttribute("userno", paramJson.getString("userno"));
				json.put("data",user);
			}else{
				json.put("code", 101);
				json.put("data",result);
				json.put("msg", "密码错误！");
			}
		}else{
			json.put("code", 101);
			json.put("data",result);
			json.put("msg", "该用户不存在！");
		}
		out.print(json.toJSONString());
		out.flush();
		out.close();
	}

	public void init() throws ServletException {

	}
}
