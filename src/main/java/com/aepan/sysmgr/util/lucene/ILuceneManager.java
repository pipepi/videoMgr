/**
 * 
 */
package com.aepan.sysmgr.util.lucene;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.LockObtainFailedException;

/**
 * 保证多线程调用单writer/reader对象实例
 * @author lanker
 * 2015年10月30日上午11:52:30
 */
public interface ILuceneManager {
	public IndexWriter getIndexWriter() throws CorruptIndexException, LockObtainFailedException, IOException;
	public IndexReader getIndexReader() throws CorruptIndexException, IOException;
	public void  closeIndexWriter() throws IOException;
	public  void closeIndexReader() throws IOException;
	public  void closeAll() throws IOException;
}
