package com.gaqx.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.alibaba.fastjson.JSONObject;

/** 统一数据访问接口实现 */
public class BaseDAOImpl implements BaseDAO {
	
	//通过@Resource注解注入HibernateTemplate实例
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	/** 条件更新数据 */
	public int updateObject(String sql) {
		final String sql1 = sql; 
		return ((Integer)hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createSQLQuery(sql1);
				return query.executeUpdate();
			}
		})).intValue();	
	}
	
	
	/** 从连接池中取得一个JDBC连接 */
	@SuppressWarnings("deprecation")
	public Connection getConnection() {
		return hibernateTemplate.getSessionFactory().getCurrentSession().connection();
	}
	
	public int saveObject(String sql) {
		final String sql1 = sql; 
		return ((Integer)hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createSQLQuery(sql1);
				return query.executeUpdate();
			}
		})).intValue();
	}

	/* 
	 * 方法描述：请用一句话描述这个方法<br>	
	 * 创建时间：2015-8-26 下午04:19:29<br>
	 * @see com.zondy.base.DAO.BaseDAO#loadObject(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject loadObject(final String sql){
		return ((JSONObject)hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createSQLQuery(sql);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Object object = query.uniqueResult();
				if(object!=null){
					return mapToJson((HashMap<String, Object>)object);
				}else{
					return new JSONObject();
				}
			}
		}));
	}
	/* 
	 * 方法描述：请用一句话描述这个方法<br>	
	 * 创建时间：2015-8-26 下午03:42:39<br>
	 * @see com.zondy.base.DAO.BaseDAO#countAll(java.lang.String)
	 */
	public int countAll(final String sql) {
		return ((BigDecimal)hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createSQLQuery(sql);
				return query.uniqueResult();
			}
		})).intValue();
	}
	
	/* 
	 * 方法描述：请用一句话描述这个方法<br>	
	 * 创建时间：2015-8-26 下午03:41:24<br>
	 * @see com.zondy.base.DAO.BaseDAO#listAll(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> listAll(String sql) {
		final String hql1 = sql;
		return hibernateTemplate.executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createSQLQuery(hql1);
				List<?> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if (!Hibernate.isInitialized(list))Hibernate.initialize(list);
				List<JSONObject> jsonList = listToJsonArray(list);
				return jsonList;
			}
		});	
	}
	
	/* 
	 * 方法描述：请用一句话描述这个方法<br>	
	 * 创建时间：2015-8-26 下午03:40:39<br>
	 * @see com.zondy.base.DAO.BaseDAO#listAll(java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> listAll(String sql, int pageNo, int pageSize) {
		final int pNo = pageNo;
		final int pSize = pageSize;
		final String hql1 = sql;
		return hibernateTemplate.executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				Query query = session.createSQLQuery(hql1);
				query.setMaxResults(pSize);
				query.setFirstResult((pNo-1)*pSize);
				List<?> result = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if (!Hibernate.isInitialized(result))Hibernate.initialize(result);
				List<JSONObject> jsonList = listToJsonArray(result);
				return jsonList;
			}
		});	
	}
	
	/**
	 * 功能描述：将hashmap转换成JSONObject对象<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Apr 9, 2017 8:28:52 PM<br>
	 * @param pMap map对象
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	private static JSONObject mapToJson(HashMap<String, Object> pMap){
		JSONObject paraJson = new JSONObject();
		if(pMap!=null){
			Set<?> set = pMap.entrySet();
			Iterator<?> iterator =  set.iterator();
			Entry<String, Object> entry = null;
			while(iterator.hasNext()){
				entry = (Entry<String, Object>)iterator.next();
				String key = entry.getKey().toString();
				key = key.toLowerCase();
				Object value = "";
				if(entry.getValue()!=null){
					value = entry.getValue();
				}
				paraJson.put(key,value);
			}
		}
		return paraJson;
	}
	/**
	 * 功能描述：将List对象转换成JSONArray对象<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Apr 9, 2017 8:27:33 PM<br>
	 * @param list List对象
	 * @return List<JSONObject> JSONArray对象
	 */
	@SuppressWarnings("unchecked")
	private static List<JSONObject> listToJsonArray(List<?> list){
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		int count = list.size();
		JSONObject recordJson = null;
		HashMap<String, Object> map = null;
		for(int i=0;i<count;i++){
			recordJson = new JSONObject();
			map = (HashMap<String, Object>)list.get(i);
			recordJson = mapToJson(map);
			dataList.add(recordJson);
		}
		return dataList;
	}
}
