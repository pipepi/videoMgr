/**
 * 
 */
package com.aepan.sysmgr.model.lucene;

/**
 * 按搜索条件 拼接查询字符串
 * @author lanker
 * 2016年3月14日上午11:15:54
 */
public class StoreQuery {
	private StringBuffer query = new StringBuffer();
	public void setQ(String q){
		query.append(q);
	}
}
