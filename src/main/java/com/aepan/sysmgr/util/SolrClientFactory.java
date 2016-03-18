package com.aepan.sysmgr.util;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrClientFactory {
	private static HttpSolrClient solrClient=null;
	private static SolrClientFactory solrClientFactory=null;
	private SolrClientFactory(){
	}
	public static synchronized SolrClientFactory getInstance(){
		if(solrClientFactory==null){
			solrClientFactory = new SolrClientFactory();
		}
		return solrClientFactory;
	}
	public synchronized HttpSolrClient getSolrClient(String url){
		if(solrClient==null){
			//solrClient = new HttpSolrClient("http://localhost:8080/solr/store");
			//solrClient = new HttpSolrClient("http://192.168.1.223:8983/solr/mycollection_shard2_replica1");
			solrClient = new HttpSolrClient(url);
		}
		return solrClient;
	}
}
