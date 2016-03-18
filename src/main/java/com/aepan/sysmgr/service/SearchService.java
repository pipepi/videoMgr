package com.aepan.sysmgr.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import com.aepan.sysmgr.model.lucene.Store;
import com.aepan.sysmgr.model.lucene.StoreArray;



public interface SearchService {
	public void add(Store store) throws IOException, SolrServerException;
	public void delete(String id) throws SolrServerException, IOException;
	public List<StoreArray> select(SolrQuery solrQuery) throws SolrServerException, IOException ;
}
