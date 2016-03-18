package com.aepan.sysmgr.service.implement;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.model.config.LuceneConfig;
import com.aepan.sysmgr.model.lucene.Store;
import com.aepan.sysmgr.model.lucene.StoreArray;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.SearchService;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.SolrClientFactory;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private ConfigService configService;
	public void add(Store store) throws IOException, SolrServerException {
		LuceneConfig luceneConfig = ConfigManager.getInstance().getLuceneConfig(configService);
		HttpSolrClient client = SolrClientFactory.getInstance().getSolrClient(luceneConfig.SOLR_URL);
		client.addBean(store);
		client.commit();
	}
	public void delete(String id) throws SolrServerException, IOException {
		LuceneConfig luceneConfig = ConfigManager.getInstance().getLuceneConfig(configService);
		HttpSolrClient client = SolrClientFactory.getInstance().getSolrClient(luceneConfig.SOLR_URL);
		client.deleteById(id);
		client.commit();
	}
	public List<StoreArray> select(SolrQuery solrQuery) throws SolrServerException, IOException {
		LuceneConfig luceneConfig = ConfigManager.getInstance().getLuceneConfig(configService);
		HttpSolrClient client = SolrClientFactory.getInstance().getSolrClient(luceneConfig.SOLR_URL);
		QueryResponse qr = client.query(solrQuery);
		List<StoreArray> ssa  = qr.getBeans(StoreArray.class);
		//List<Store> ss =  qr.getBeans(Store.class);
		return ssa;
	}
	

}
