/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com._21cn.framework.util.PageList;
import com._21cn.framework.util.PageTurn;
import com.aepan.sysmgr.dao.ProductDao;
import com.aepan.sysmgr.dao.rowmapper.ProductRowMapper;
import com.aepan.sysmgr.dao.rowmapper.ProductTypeRowMapper;
import com.aepan.sysmgr.model.ProductInfo;
import com.aepan.sysmgr.model.ProductType;
import com.aepan.sysmgr.model.StoreProduct;
import com.aepan.sysmgr.model.lucene.ProductAttribute;
import com.aepan.sysmgr.util.JSONUtil;
import com.aepan.sysmgr.util.StringUtil;

/**
 * 
 * @author rakika
 * 2015年7月14日下午6:31:33
 */
@Repository
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	/* 
	 * 产品列表
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<ProductInfo> getList(Map<String, Object> params,
			int pageNo, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id, name, user_id, photo_url1, photo_url2, photo_url3, photo_url4, photo_url5,");
		sb.append(" type, price, weight, reserve, status, ext, create_time ");
		sb.append(" from t_sysmgr_product_info ");
		
		//params sortName
		if(params.get("sortName") != null){
			sb.append(" order by " + params.get("sortName"));
			sb.append(" " + params.get("sortOrder"));
			
		}
		List<ProductInfo> list = jdbcTemplate.query(sb.toString(), new ProductRowMapper());
		PageList<ProductInfo> pageList = new PageList<ProductInfo>();
		PageTurn pageTurn = new PageTurn(pageNo, pageSize,
				params.get("iDisplayStart") == null ? 1 :
				Integer.parseInt(params.get("iDisplayStart").toString()));
		for(int i = 0; i < list.size(); i++){
		    pageList.add(list.get(i));	
		}
		pageList.setPageTurn(pageTurn);
		return pageList;
	}

	/* 
	 * 产品类型列表
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#getTypeList(java.util.Map, int, int)
	 */
	@Override
	public PageList<ProductType> getTypeList(Map<String, Object> params,
			int pageNo, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select t.id, t.name, t.create_time from t_sysmgr_product_type t ");
		sb.append(" where 1 = 1 ");
        //params userName
        if(params.get("eqUsername") != null){
        	sb.append(" and t.name like '%");
        	sb.append(params.get("eqUsername") + "%' ");
        }
		//params sortName
		if(params.get("sortName") != null){
			sb.append(" order by " + params.get("sortName"));
			sb.append(" " + params.get("sortOrder"));
		}
		List<ProductType> list = jdbcTemplate.query(sb.toString(), new ProductTypeRowMapper());
		PageList<ProductType> pageList = new PageList<ProductType>();
		PageTurn pageTurn = new PageTurn(pageNo, pageSize,
				params.get("iDisplayStart") == null ? 1 :
				Integer.parseInt(params.get("iDisplayStart").toString()));
		for(int i = 0; i < list.size(); i++){
		    pageList.add(list.get(i));	
		}
		pageList.setPageTurn(pageTurn);
		return pageList;
	}
	@Override
	@Transactional
	public void reInit(List<ProductType> list){
		final List<ProductType> rlist = list;
		String sql = "delete from t_sysmgr_product_type";
		jdbcTemplate.update(sql);
		jdbcTemplate.batchUpdate(" insert t_sysmgr_product_type(id, name, create_time) "
                + " values(?, ?, ?)", new BatchPreparedStatementSetter(){
					@Override
					public int getBatchSize() {
						return rlist.size();
					}
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ProductType sp = (ProductType)rlist.get(i);
						ps.setInt(1, sp.getId());
						ps.setString(2, sp.getName());
						ps.setDate(3, new Date(sp.getCreateTime().getTime()));
					}
                });
	}
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#create(com._aepan.sysmgr.model.ProductInfo)
	 */
	@Override
	public boolean create(ProductInfo productInfo) {
		StringBuffer sb = new StringBuffer(" insert into t_sysmgr_product_info(name, user_id, photo_url1, ");
		sb.append(" photo_url2, photo_url3, photo_url4, photo_url5, type, price, weight, ");
		sb.append(" reserve, status, ext) values(?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,?, ?)");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			productInfo.getName(),
			productInfo.getUserId(),
			productInfo.getPhotoUrl1(),
			productInfo.getPhotoUrl2(),
			productInfo.getPhotoUrl3(),
			productInfo.getPhotoUrl4(),
			productInfo.getPhotoUrl5(),
			productInfo.getType(),
			productInfo.getPrice(),
			productInfo.getWeight(),
			productInfo.getReserve(),
			productInfo.getStatus(),
			productInfo.getExt()
		});
		return ret > 0;
	}
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#createType(com._aepan.sysmgr.model.ProductType)
	 */
	@Override
	public boolean saveType(ProductType productType) {
		StringBuffer sb = new StringBuffer(" insert into t_sysmgr_product_type(name) values( ? )");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			productType.getName()
		});
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#update(com._aepan.sysmgr.model.ProductInfo)
	 */
	@Override
	public boolean update(ProductInfo productInfo) {
		List<Object> list = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(" update t_sysmgr_product_info set ");
		sb.append(" name = ?, ");
		list.add(productInfo.getName());
		if(!StringUtil.isBlank(productInfo.getPhotoUrl1())){
			sb.append(" photo_url1 = ?, ");
			list.add(productInfo.getPhotoUrl1());
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl2())){
			sb.append(" photo_url2 = ?, ");
			list.add(productInfo.getPhotoUrl2());
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl3())){
			sb.append(" photo_url3 = ?, ");
			list.add(productInfo.getPhotoUrl3());
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl4())){
			sb.append(" photo_url4 = ?, ");
			list.add(productInfo.getPhotoUrl4());
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl5())){
			sb.append(" photo_url5 = ?, ");
			list.add(productInfo.getPhotoUrl5());
		}
		sb.append(" type = ?, ");
		sb.append(" price = ?, ");
		sb.append(" weight = ?, ");
		sb.append(" reserve = ?, ");
		sb.append(" status =? ");
		sb.append(" where id = ? ");
		list.add(productInfo.getType());
		list.add(productInfo.getPrice());
		list.add(productInfo.getWeight());
	    list.add(productInfo.getReserve());
		list.add(productInfo.getStatus());
	    list.add(productInfo.getId());

		int ret = jdbcTemplate.update(sb.toString(), list.toArray());
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#getById(java.lang.Integer)
	 */
	@Override
	public ProductInfo getById(Integer id) {
		return jdbcTemplate.queryForObject(" select id, name, user_id, photo_url1,photo_url2, photo_url3, "
				+ " photo_url4, photo_url5, type, price, weight, reserve, status, ext, create_time "
				+ " from t_sysmgr_product_info where id = ? ", 
				new Object[] {id}, new ProductRowMapper());
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#save(com._aepan.sysmgr.model.ProductInfo)
	 */
	@Override
	public boolean save(ProductInfo productInfo) {
		List<Object> list = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into t_sysmgr_product_info(name, user_id, ");
		list.add(productInfo.getName());
	    list.add(productInfo.getUserId());
		if(!StringUtil.isBlank(productInfo.getPhotoUrl1())){
			sb.append(" photo_url1, ");
			list.add(productInfo.getPhotoUrl1());
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl2())){
			sb.append(" photo_url2, ");
			list.add(productInfo.getPhotoUrl2());
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl3())){
			sb.append(" photo_url3, ");
			list.add(productInfo.getPhotoUrl3());
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl4())){
			sb.append(" photo_url4, ");
			list.add(productInfo.getPhotoUrl4());
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl5())){
			sb.append(" photo_url5, ");
			list.add(productInfo.getPhotoUrl5());
		}
		sb.append(" type, price, weight, reserve, status) values(");
		sb.append("?, ?, ");
		if(!StringUtil.isBlank(productInfo.getPhotoUrl1())){
			sb.append("?, ");
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl2())){
			sb.append("?, ");
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl3())){
			sb.append("?, ");
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl4())){
			sb.append("?, ");
		}
		if(!StringUtil.isBlank(productInfo.getPhotoUrl5())){
			sb.append("?, ");
		}
		sb.append("?, ?, ?, ?, ? ) ");
		
		list.add(productInfo.getType());
		list.add(productInfo.getPrice());
		list.add(productInfo.getWeight());
	    list.add(productInfo.getReserve());
		list.add(productInfo.getStatus());
		
		int ret = jdbcTemplate.update(sb.toString(), list.toArray());
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#getByUserList(int)
	 */
	@Override
	public List<ProductInfo> getByUserList(int userId) {
		 return jdbcTemplate.query(" select id, name, user_id, photo_url1,photo_url2, photo_url3, "
				+ " photo_url4, photo_url5, type, price, weight, reserve, status, ext, create_time "
				+ " from t_sysmgr_product_info where user_id = ? ", 
				new Object[] {userId}, new ProductRowMapper());
		 
	}
	
	
	@Override
	public List<Integer> getStoreProductIdList(int storeId) {
		
		String sql = "select product_id from t_sysmgr_store_product where store_id=?";
		
		List<Integer> productIdList = jdbcTemplate.queryForList(sql, new Object[] {storeId},Integer.class);
		
		return productIdList;	 
	}
	

	
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#getByUserIdAndStoreIdList(int, int)
	 */
	@Override
	public List<ProductInfo> getByUserIdAndStoreIdList(int userId, int StoreId) {
		StringBuffer sb = new StringBuffer("select i.id, i.name, i.user_id, i.photo_url1, i.photo_url2, ");
		sb.append(" i.photo_url3, i.photo_url4, i.photo_url5, i.type, i.price, i.weight, i.reserve, ");
		sb.append(" i.status, i.ext, i.create_time from t_sysmgr_store_product r, t_sysmgr_product_info i, ");
		sb.append(" t_sysmgr_store s where r.store_id = s.id and r.product_id = i.id and r.user_id = ? ");
		sb.append(" and r.store_id = ? ");
		return jdbcTemplate.query(sb.toString(), new Object[]{userId, StoreId}, new ProductRowMapper());
	}
	

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#getById(int)
	 */
	@Override
	public ProductType getById(int id) {
		return jdbcTemplate.queryForObject(" select t.id, t.name, t.create_time from t_sysmgr_product_type t where t.id = ? ", 
				new Object[] {id}, new ProductTypeRowMapper());
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#batchInsert(int, java.util.List, java.lang.Integer)
	 */
	@Override
	public void batchInsert(int batchSize, List<StoreProduct> batchList,
			Integer userId) {

		final List<StoreProduct> rlist = batchList;
		jdbcTemplate.batchUpdate(" insert t_sysmgr_store_product(user_id, store_id, product_id,product_name,product_desc,product_type,product_attrs,product_price) "
                    + " values(?, ?, ?,?,?,?,?,?)", new BatchPreparedStatementSetter(){

						/* (non-Javadoc)
						 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#getBatchSize()
						 */
						@Override
						public int getBatchSize() {
							return rlist.size();
						}

						/* (non-Javadoc)
						 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
						 */
						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							StoreProduct sp = (StoreProduct)rlist.get(i);
							ps.setInt(1, sp.getUserId());
							ps.setInt(2, sp.getStoreId());
							ps.setInt(3, sp.getProductId());
							ps.setString(4, sp.getProductName());
							ps.setString(5, sp.getProductDesc());
							ps.setString(6, sp.getProductType());
							ps.setString(7, sp.productAttrs!=null?JSONUtil.toJson(sp.productAttrs):"[]");
							ps.setFloat(8, sp.productPrice);
						}
                    });
		
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#deleteByUserIdANDStoreId(int, int)
	 */
	@Override
	public boolean deleteByUserIdANDStoreId(int userId, int storeId) {
		return jdbcTemplate.update(" delete from t_sysmgr_store_product where user_id = ? "
				+ " and store_id = ?", 
				new Object[]{userId, storeId}) > 0;
		
	}
	@Override
	public void deleteLinkRelationByProductId(int productId){
		jdbcTemplate.update(" delete from t_sysmgr_store_product where "
				+ " product_id = ?", 
				new Object[]{productId});
	}
	@Override
	public void update(StoreProduct storeProduct){
		String sql = "update t_sysmgr_store_product set product_price = ? where id = ? ";
		jdbcTemplate.update(sql, storeProduct.productPrice,storeProduct.getId());
	}
	@Override
	public List<StoreProduct> getStoreProductList(int userId,int storeId){
		String sql = "select * from t_sysmgr_store_product where user_id = "+userId+" and store_id = "+storeId;
		return jdbcTemplate.query(sql,STORE_PRODUCT_HANDDLE);
	}
	@Override
	public StoreProduct getStoreProduct(int storeId,int pId){
		String sql = "select * from t_sysmgr_store_product where store_id = "+storeId+" and product_id = "+pId;
		return jdbcTemplate.queryForObject(sql, STORE_PRODUCT_HANDDLE);
	}
	@Override
	public List<StoreProduct> getStoreProductListByUserId(int userId){
		String sql = "select * from t_sysmgr_store_product where user_id = "+userId;
		return jdbcTemplate.query(sql,STORE_PRODUCT_HANDDLE);
	}
	@Override
	public List<StoreProduct> getStoreProductListByProductId(int pId) {
		String sql = "select * from t_sysmgr_store_product where product_id = "+pId;
		return jdbcTemplate.query(sql,STORE_PRODUCT_HANDDLE);
	}

	@Override
	public int getStoreProductCountByUserId(int userId){
		String sql = "select count(1) from t_sysmgr_store_product where user_id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{userId},Integer.class);
	}
	
	private static  RowMapper<StoreProduct> STORE_PRODUCT_HANDDLE = new RowMapper<StoreProduct>(){
		@Override
		public StoreProduct mapRow(ResultSet rs, int index) throws SQLException {
			StoreProduct sv = new StoreProduct();
			sv.id = rs.getInt("id");
			sv.userId = rs.getInt("user_id");
			sv.storeId = rs.getInt("store_id");
			sv.productId = rs.getInt("product_id");
			sv.productName = rs.getString("product_name");
			sv.productDesc = rs.getString("product_desc");
			sv.productType = rs.getString("product_type");
			String attr = rs.getString("product_attrs");
			if(attr!=null&&attr.trim().length()>0){
				sv.productAttrs = JSONUtil.fromJsonList(attr, ProductAttribute.class);
			}
			sv.productPrice = rs.getFloat("product_price");
			sv.createTime = rs.getTimestamp("create_time");
			return sv;
		}
		
	};
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ProductDao#updateType(com._aepan.sysmgr.model.ProductType)
	 */
	@Override
	public boolean updateType(ProductType productType) {
		return jdbcTemplate.update(" update t_sysmgr_product_type set name = ? where id = ? ",
				new Object[]{productType.getName(), productType.getId()}) > 0;
	}
	@Override
	public List<Integer> getLinkedProductNumPerStore(int userId){
		String sql = "select count(product_id) from t_sysmgr_store_product where user_id = ? group by store_id ";
		return jdbcTemplate.queryForList(sql, new Object[] {userId}, Integer.class);
	}
}
