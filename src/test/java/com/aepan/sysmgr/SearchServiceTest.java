/**
 * 
 */
package com.aepan.sysmgr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aepan.sysmgr.model.lucene.Store;
import com.aepan.sysmgr.service.SearchService;
import com.aepan.sysmgr.service.implement.SearchServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class SearchServiceTest {
	private SearchService searchService;
	public static void main(String[] args) throws IOException, SolrServerException {
		SearchServiceTest s = new SearchServiceTest();
		s.add();
		//s.delete("1000");
	}
	@Test
	public void delete() throws SolrServerException, IOException{
		searchService = new SearchServiceImpl();
		searchService.delete("1111");
	}
	//@Test
	public void add() throws IOException, SolrServerException{
		searchService = new SearchServiceImpl();
		searchService.add(Store.init4test());
	}
	//@Test
	public void select() throws SolrServerException, IOException{
		searchService = new SearchServiceImpl();
		SolrQuery solrQuery = new SolrQuery();
		//String qstr = "id:(2 OR 6 ) AND name:*球星*";
		String qstr = "*:*";
		solrQuery.setQuery(qstr);
		searchService.select(solrQuery);
	}

}