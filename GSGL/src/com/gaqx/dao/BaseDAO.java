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

package com.gaqx.dao;

import java.sql.Connection;
import java.util.List;
import com.alibaba.fastjson.JSONObject;

/**
 * 模块名称：BaseDAO									<br>
 * 功能描述：该文件详细功能描述							<br>
 * 文档作者：雷志强									<br>
 * 创建时间：2014-2-27 下午02:35:18					<br>
 * 初始版本：V1.0	最初版本号							<br>
 * 修改记录：											<br>
 * *************************************************<br>
 * 修改人：雷志强										<br>
 * 修改时间：2014-2-27 下午02:35:18					<br>
 * 修改内容：											<br>
 * *************************************************<br>
 */
public interface BaseDAO {
	/** 保存对象返回主键信息 */
	public int saveObject(String sql);
	/** 更新或者删除对象返回信息：1成功,0失败*/
	public int updateObject(String sql);
	/** 加载指定ID的数据对象，返回该对象 */
	public JSONObject loadObject(String sql);
	/** 装载指定类的所有持久化对象 */
	public List<JSONObject> listAll(String sql);
	/** 分页装载指定类的所有持久化对象 */
	public List<JSONObject> listAll(String sql,int pageNo,int pageSize);
	/** 统计指定类的所有持久化对象 */
	public int countAll(String sql);
	/** 从连接池中取得一个JDBC连接 */
	public Connection getConnection();
}
