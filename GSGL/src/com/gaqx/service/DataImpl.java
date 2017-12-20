package com.gaqx.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.alibaba.fastjson.JSONObject;
import com.gaqx.dao.BaseDAOImpl;
import com.gaqx.config.ParamConfig;
import com.gaqx.config.QuerySqlConfig;
import com.gaqx.listener.ApplicationListener;

public class DataImpl {
	private static Logger log = Logger.getLogger(DataImpl.class);
	private static BaseDAOImpl dao = ApplicationListener.dao;

	//初始化SQL语句配置
	public static QuerySqlConfig config = new QuerySqlConfig();

	//保存数据
		public JSONObject saveData(JSONObject paramJson)throws HibernateException{
			JSONObject json = new JSONObject();
			QuerySqlConfig config = new QuerySqlConfig();
			String primaryKey = ParamConfig.getColumnValue("getTmpNo", "");
			paramJson.put("primaryKey", primaryKey);
			String sql = config.getManagerConfigSQL(paramJson.get("sqlapi").toString());
			sql = config.getReplaceSqlParam(sql,paramJson);
			log.info("sql="+sql);
			int result = dao.saveObject(sql);
			json.put("result", result);
			if(result>0){
				json.put("dataKey", primaryKey);
			}
			return json;
		}
		
		//修改数据
		public int editData(JSONObject paramJson)throws HibernateException{
			QuerySqlConfig config = new QuerySqlConfig();
			String sql = config.getManagerConfigSQL(paramJson.get("sqlapi").toString());
			sql = config.getReplaceSqlParam(sql,paramJson);
			log.info("sql="+sql);
			int result = dao.updateObject(sql);
			return result;
		}
		
		//根据条件统计查询记录总数
		public int countAll(JSONObject paramJson){
			QuerySqlConfig config = new QuerySqlConfig();
			String sql = config.getManagerConfigSQL(paramJson.get("sqlapi").toString());
			sql = config.getReplaceSqlParam(sql,paramJson);
			log.info("sql="+sql);
			int result = dao.countAll(sql);
			return result;
		}
		
		//根据查询条件查询所有记录
		public List<JSONObject> listAll(JSONObject paramJson){
			List<JSONObject> list = new ArrayList<JSONObject>();
			QuerySqlConfig config = new QuerySqlConfig();
			String sql = config.getManagerConfigSQL(paramJson.get("sqlapi").toString());
			sql = config.getReplaceSqlParam(sql,paramJson);
			log.info("sql="+sql);
			list = dao.listAll(sql);
			return list;
		}
		
		//根据查询条件查询分页记录
		public List<JSONObject> listByPage(JSONObject paramJson){
			List<JSONObject> list = new ArrayList<JSONObject>();
			QuerySqlConfig config = new QuerySqlConfig();
			String sql = config.getManagerConfigSQL(paramJson.get("sqlapi").toString());
			sql = config.getReplaceSqlParam(sql,paramJson);
			log.info("sql="+sql);
			int pageNo = 1;
			int pageSize = 100;
			if(paramJson.get("page")!=null){
				pageNo = Integer.parseInt(paramJson.get("page").toString());
			}
			if(paramJson.get("limit")!=null){
				pageSize = Integer.parseInt(paramJson.get("limit").toString());
			}
			list = dao.listAll(sql,pageNo,pageSize);
			return list;
		}
}
