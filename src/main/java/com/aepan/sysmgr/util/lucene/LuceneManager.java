/**
 * 
 */
package com.aepan.sysmgr.util.lucene;

import java.io.File;
import java.io.IOException;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.model.config.LuceneConfig;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.util.ConfigManager;

/**
 * @author Administrator
 * 2015年10月30日上午11:54:55
 */
public class LuceneManager implements ILuceneManager {
	private static final Logger logger = LoggerFactory.getLogger(LuceneManager.class);
	private static ILuceneManager luceneManager;
	private static LuceneConfig conf;
	
	private LuceneManager() {
	}
	public static ILuceneManager instance(ConfigService configService){
		if(luceneManager==null){
			luceneManager = new LuceneManager();
			conf = ConfigManager.getInstance().getLuceneConfig(configService);
		}
		return luceneManager;
	}
	private IndexWriter  indexWriter=null;
	private IndexReader  indexReader=null;
	//------lock 1
	private Object lock_w=new Object();
	//------lock 2
	private Object lock_r=new Object();
	
    @Override
    public IndexWriter getIndexWriter() throws CorruptIndexException, LockObtainFailedException, IOException {        
        synchronized(lock_w){
           if(indexWriter==null){
        	 logger.info("创建对象");
             if(IndexWriter.isLocked(FSDirectory.open(new File(conf.INDEX_URL)))){
                 IndexWriter.unlock(FSDirectory.open(new File(conf.INDEX_URL)));
             };
             Analyzer analyzer = new PaodingAnalyzer();
             IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_33,analyzer);
             iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
             indexWriter=new IndexWriter(FSDirectory.open(new File(conf.INDEX_URL)),iwc);
           };
           
       }
        return indexWriter;
    }

    @Override
    public IndexReader getIndexReader() throws CorruptIndexException, IOException {
      synchronized (lock_r) {
            if(indexReader==null){
            	File f = new File(conf.INDEX_URL);
            	System.out.println(f.getAbsolutePath());
                indexReader=IndexReader.open(FSDirectory.open(new File(conf.INDEX_URL)));
            };
        }
        return  indexReader;
    }

    @Override
    public void closeIndexWriter() throws IOException {
    	synchronized (lock_w) {
            if(this.indexWriter!=null){
                this.indexWriter.close();
                this.indexWriter = null;
            };
        }
        
    }

    @Override
    public void closeIndexReader() throws IOException {
    	synchronized (lock_r) {
            if(this.indexReader!=null){
                this.indexReader.close();
                this.indexReader = null;
            };
        }
        
        
    }

    @Override
    public void closeAll() throws IOException {
        // TODO Auto-generated method stub
        this.closeIndexReader();
        this.closeIndexWriter();
        
    }
	
}
