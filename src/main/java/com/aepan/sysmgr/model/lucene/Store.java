/**
 * 
 */
package com.aepan.sysmgr.model.lucene;

import java.util.HashSet;
import java.util.Set;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author lanker
 * 2016年3月4日下午3:43:34
 */
public class Store {
	@Field
	private String id;
	@Field
	private String partnerUserId;
	@Field
	private String name;
	@Field
	private String desc;
	@Field
	private Set<String> type;
	@Field
	private String update_time;
	@Field
	private String p_ids;
	@Field
	private Set<String> p_name;
	@Field
	private Set<String> p_desc;
	@Field
	private Set<String> p_type;
	@Field
	private Set<String> p_attrname;
	@Field
	private Set<String> p_attrvalue;
	@Field
	private float p_pricemax;
	@Field
	private float p_pricemin;
	@Field
	private Set<String> v_name;
	@Field
	private Set<String> v_desc;
	@Field
	private int v_hot;
	@Field
	private String v_img;
	@Field
	private String v_img_max;
	public static Store init4test(){
		Store s = new Store();
		s.id = "1111";
		s.name="add store for test 哈哈这就是一个测试视频";
		s.desc = "add store for test 哈哈这就是一个测试视频";
		Set<String> type = new HashSet<String>();
		type.add("1");
		type.add("12");
		type.add("15");
		type.add("55");
		s.type = type;
		s.p_ids = "1,2,3";
		Set<String> pname = new HashSet<String>();
		pname.add("产品一");
		pname.add("产品二");
		s.p_name = pname;
		return s;
		
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the partnerUserId
	 */
	public String getPartnerUserId() {
		return partnerUserId;
	}
	/**
	 * @param partnerUserId the partnerUserId to set
	 */
	public void setPartnerUserId(String partnerUserId) {
		this.partnerUserId = partnerUserId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * @return the type
	 */
	public Set<String> getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Set<String> type) {
		this.type = type;
	}
	
	/**
	 * @return the update_time
	 */
	public String getUpdate_time() {
		return update_time;
	}
	/**
	 * @param update_time the update_time to set
	 */
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	/**
	 * @return the p_ids
	 */
	public String getP_ids() {
		return p_ids;
	}
	/**
	 * @param p_ids the p_ids to set
	 */
	public void setP_ids(String p_ids) {
		this.p_ids = p_ids;
	}
	/**
	 * @return the p_name
	 */
	public Set<String> getP_name() {
		return p_name;
	}
	/**
	 * @param p_name the p_name to set
	 */
	public void setP_name(Set<String> p_name) {
		this.p_name = p_name;
	}
	/**
	 * @return the p_desc
	 */
	public Set<String> getP_desc() {
		return p_desc;
	}
	/**
	 * @param p_desc the p_desc to set
	 */
	public void setP_desc(Set<String> p_desc) {
		this.p_desc = p_desc;
	}
	/**
	 * @return the p_type
	 */
	public Set<String> getP_type() {
		return p_type;
	}
	/**
	 * @param p_type the p_type to set
	 */
	public void setP_type(Set<String> p_type) {
		this.p_type = p_type;
	}
	/**
	 * @return the p_attrname
	 */
	public Set<String> getP_attrname() {
		return p_attrname;
	}
	/**
	 * @param p_attrname the p_attrname to set
	 */
	public void setP_attrname(Set<String> p_attrname) {
		this.p_attrname = p_attrname;
	}
	/**
	 * @return the p_attrvalue
	 */
	public Set<String> getP_attrvalue() {
		return p_attrvalue;
	}
	/**
	 * @param p_attrvalue the p_attrvalue to set
	 */
	public void setP_attrvalue(Set<String> p_attrvalue) {
		this.p_attrvalue = p_attrvalue;
	}
	/**
	 * @return the p_pricemax
	 */
	public float getP_pricemax() {
		return p_pricemax;
	}
	/**
	 * @param p_pricemax the p_pricemax to set
	 */
	public void setP_pricemax(float p_pricemax) {
		this.p_pricemax = p_pricemax;
	}
	/**
	 * @return the p_pricemin
	 */
	public float getP_pricemin() {
		return p_pricemin;
	}
	/**
	 * @param p_pricemin the p_pricemin to set
	 */
	public void setP_pricemin(float p_pricemin) {
		this.p_pricemin = p_pricemin;
	}
	/**
	 * @return the v_name
	 */
	public Set<String> getV_name() {
		return v_name;
	}
	/**
	 * @param v_name the v_name to set
	 */
	public void setV_name(Set<String> v_name) {
		this.v_name = v_name;
	}
	/**
	 * @return the v_desc
	 */
	public Set<String> getV_desc() {
		return v_desc;
	}
	/**
	 * @param v_desc the v_desc to set
	 */
	public void setV_desc(Set<String> v_desc) {
		this.v_desc = v_desc;
	}
	/**
	 * @return the v_hot
	 */
	public int getV_hot() {
		return v_hot;
	}
	/**
	 * @param v_hot the v_hot to set
	 */
	public void setV_hot(int v_hot) {
		this.v_hot = v_hot;
	}
	/**
	 * @return the v_img
	 */
	public String getV_img() {
		return v_img;
	}
	/**
	 * @param v_img the v_img to set
	 */
	public void setV_img(String v_img) {
		this.v_img = v_img;
	}
	/**
	 * @return the v_img_max
	 */
	public String getV_img_max() {
		return v_img_max;
	}
	/**
	 * @param v_img_max the v_img_max to set
	 */
	public void setV_img_max(String v_img_max) {
		this.v_img_max = v_img_max;
	}
	
}
