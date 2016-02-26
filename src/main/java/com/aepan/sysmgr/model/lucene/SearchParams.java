/**
 * 
 */
package com.aepan.sysmgr.model.lucene;

import java.io.Serializable;
import java.util.List;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.util.JSONUtil;

/**
 * 搜索条件
 * @author lanker
 * 2015年11月16日下午3:35:05
 */
public class SearchParams implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SearchParams.class);
	
	public String key;
	public String filed;
	
	public int storeType;
	public List<ProductAttribute> productAttrs;//[{1,[1,2]},{2,[1]}]
	public float priceMax; 
	public float priceMin;
	
	public String sortBy;
	public boolean sortType;//true:desc false:asc
	
	public int pn;
	public int ps;
	public static void main(String[] args) {
		SearchParams sp = new SearchParams();
		sp.productAttrs = ProductAttribute.init();
		logger.info(JSONUtil.toJson(sp));
	}
	public Query toQeury() throws ParseException{
		if(filed==null||filed.trim().isEmpty()||key==null||key.trim().isEmpty()){
			return null;
		}
		if(key.equals("*")){
			Query query = new WildcardQuery(new Term(filed, key));
			return query;
		}else{
			String[] fields = filed.split("\\,");
			Analyzer analyzer = new PaodingAnalyzer();
			MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_33, fields, analyzer);
			Query query = null;
			query = parser.parse(key.trim());
			return query;
		}
	}
	public Filter toFilter(){
		BooleanQuery bQuery = new BooleanQuery();
		if(storeType>0){//过滤播放器类型
			Query stQuery = new TermQuery(new Term("type",storeType+""));
			bQuery.add(stQuery, BooleanClause.Occur.MUST);
		}
		if(productAttrs!=null&&productAttrs.size()>0){//过滤产品属性
			BooleanQuery bQueryPattrs = new BooleanQuery();
			for (ProductAttribute pa : productAttrs) {
				if(pa!=null&&pa.values!=null&&pa.values.size()>0){
					BooleanQuery bQueryPattr = new BooleanQuery();
					Query queryAttrName = new TermQuery(new Term("p_attrname",pa.id+""));
					BooleanQuery bQueryAttrValues = new BooleanQuery();
					for (Integer v : pa.values) {
						Query queryAttrValue = new TermQuery(new Term("p_attrvalue",v+""));
						bQueryAttrValues.add(queryAttrValue, BooleanClause.Occur.SHOULD);
					}
					bQueryPattr.add(queryAttrName,BooleanClause.Occur.MUST);
					bQueryPattr.add(bQueryAttrValues,BooleanClause.Occur.MUST);
					bQueryPattrs.add(bQueryPattr, BooleanClause.Occur.MUST);
				}
			}
			bQuery.add(bQueryPattrs,BooleanClause.Occur.MUST);
		}
		if(priceMax>0&&priceMin>=0&&priceMax>=priceMin){//过滤价格
			Query priceQueryMax = NumericRangeQuery.newFloatRange("p_pricemax",priceMin, priceMax,true, true);
			Query priceQueryMin = NumericRangeQuery.newFloatRange("p_pricemin", priceMin, priceMax, true, true);
			BooleanQuery bPriceQuery = new BooleanQuery();
			bPriceQuery.add(priceQueryMax,BooleanClause.Occur.SHOULD);
			bPriceQuery.add(priceQueryMin,BooleanClause.Occur.SHOULD);
			bQuery.add(bPriceQuery,BooleanClause.Occur.MUST);
		}
		if(bQuery.getClauses().length>0){
			Filter f = new QueryWrapperFilter(bQuery);
			return f;
		}else{
			return null;
		}
	}
	public Sort toSort(){
		SortField sortField = null;
		if(sortBy.equals("v_hot")){
			sortField = new SortField(sortBy, SortField.INT,sortType);
		}else if(sortBy.equals("p_pricemax")){
			sortField = new SortField(sortBy, SortField.FLOAT,sortType);
		}else {
			return null;
		}
		Sort sort = new Sort(sortField);
		return sort;
	}
}
