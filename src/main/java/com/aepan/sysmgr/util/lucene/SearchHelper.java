/**
 * 
 */
package com.aepan.sysmgr.util.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.model.lucene.SearchParams;
import com.aepan.sysmgr.model.lucene.StoreSub;
import com.aepan.sysmgr.service.ConfigService;

/**
 * 播放器搜索帮助类
 * @author lanker
 * 2015年10月27日上午11:45:53
 */
public class SearchHelper {
	private static final Logger logger = LoggerFactory.getLogger(SearchHelper.class);
	public static void insert(ConfigService configService,Document doc){
		try {
            IndexWriter writer = LuceneManager.instance(configService).getIndexWriter();
            writer.updateDocument(new Term("id" , doc.get("id")),doc); 
            writer.commit();
            LuceneManager.instance(configService).closeAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void delete(ConfigService configService,String id){
		try {
			IndexWriter writer = LuceneManager.instance(configService).getIndexWriter();
			writer.deleteDocuments(new Term("id" , id));
			writer.commit();
            LuceneManager.instance(configService).closeAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static List<StoreSub> search(ConfigService configService,String queryString,String field,int pageNo,int hitsPerPage){
		try {
			IndexSearcher searcher = new IndexSearcher(LuceneManager.instance(configService).getIndexReader());
			Analyzer analyzer = new PaodingAnalyzer();
			QueryParser parser = new QueryParser(Version.LUCENE_33, field, analyzer);
			Query query = parser.parse(queryString.trim());
			logger.info("Searching for: " + query.toString(field));
			TopScoreDocCollector results = TopScoreDocCollector.create(pageNo*hitsPerPage, false);
			searcher.search(query, results);
			return toStore(searcher, results, pageNo, hitsPerPage);
		} catch (CorruptIndexException e) {
			logger.info("搜索出错，索引异常CorruptIndexException");
		} catch (IOException e) {
			logger.info("搜索出错，IO异常IOException");
		} catch (ParseException e) {
			logger.info("构建庖丁查询语句出错，可能搜索词为保留关键词or等ParseException");
		}
		return new ArrayList<StoreSub>();
	}
	public static List<StoreSub> search(ConfigService configService,SearchParams sparams){
		
	try{
		IndexSearcher searcher = new IndexSearcher(LuceneManager.instance(configService).getIndexReader());
		TopFieldDocs  docs = searcher.search(sparams.toQeury(), sparams.toFilter(), sparams.pn*sparams.ps, sparams.toSort());
		return toStore(searcher, docs, sparams.pn, sparams.ps);
	} catch (CorruptIndexException e) {
		logger.info("搜索出错，索引异常CorruptIndexException");
	} catch (IOException e) {
		logger.info("搜索出错，IO异常IOException");
	} catch (ParseException e) {
		logger.info("构建庖丁查询语句出错，可能搜索词为保留关键词or等ParseException");
	}
		//step1:query
		
		//step2:filter
		//step3:sort
		//step4:page
		return new ArrayList<StoreSub>();
	}
	private static List<StoreSub> toStore(IndexSearcher searcher,TopScoreDocCollector results,int pageNo,int hitsPerPage) throws CorruptIndexException, IOException{
		List<StoreSub> slist = new ArrayList<StoreSub>();
		ScoreDoc[] hits = results.topDocs((pageNo-1)*hitsPerPage, pageNo*hitsPerPage).scoreDocs;
        int numTotalHits = results.getTotalHits();
        logger.info(numTotalHits + " total matching documents");
        for (int i = 0; i < hits.length; i++) {
            Document doc = searcher.doc(hits[i].doc);
            StoreSub ss = new StoreSub(
            		Integer.parseInt(doc.get("id")), 
            		doc.get("name"), 
            		doc.get("desc"), 
            		doc.get("v_img"),
            		doc.get("v_img_max"),
            		doc.get("type"),"",0,0,0,null,"");
            slist.add(ss);
            logger.info(
            		"ID:" + doc.get("id") + 
            		"\nNAME:" + doc.get("name")+
            		"\ntype:" + doc.get("type")+
            		"\nDESCRIPTION:" + doc.get("desc")
            		);
        }
        //searcher.close();
		return slist;
	}
	private static List<StoreSub> toStore(IndexSearcher searcher,TopFieldDocs  results,int pageNo,int hitsPerPage) throws CorruptIndexException, IOException{
		List<StoreSub> slist = new ArrayList<StoreSub>();
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		logger.info(numTotalHits + " total matching documents");
		int max = pageNo*hitsPerPage>hits.length?hits.length:pageNo*hitsPerPage;
		for (int i = (pageNo-1)*hitsPerPage; i < max; i++) {
			Document doc = searcher.doc(hits[i].doc);
			 StoreSub ss = new StoreSub(
	            		Integer.parseInt(doc.get("id")), 
	            		doc.get("name"), 
	            		doc.get("desc"), 
	            		doc.get("v_img"),
	            		doc.get("v_img_max"),
	            		doc.get("type"),
	            		doc.get("p_ids"),
	            		Float.parseFloat(doc.get("p_pricemax")),
	            		Float.parseFloat(doc.get("p_pricemin")),
	            		Integer.parseInt(doc.get("v_hot")),
	            		null,""
	            		);
	            slist.add(ss);
	            logger.info(
	            		"ID:" + doc.get("id") + 
	            		"\nPIDS:" + doc.get("p_ids") + 
	            		"\nNAME:" + doc.get("name")+
	            		"\ntype:" + doc.get("type")+
	            		"\nDESCRIPTION:" + doc.get("desc")+
	            		"\np_pricemax:"+doc.get("p_pricemax")+
	            		"\np_pricemin:"+doc.get("p_pricemin")
	            		);
		}
		//searcher.close();
		return slist;
	}
}
