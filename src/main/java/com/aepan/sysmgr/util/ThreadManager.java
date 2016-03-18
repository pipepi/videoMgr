/**
 * 
 */
package com.aepan.sysmgr.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lanker
 * 2016年3月1日下午3:19:18
 */
public class ThreadManager {
	public static final int POOL_SIZE = 10;
	private static ExecutorService fixedThreadPool;
	private static ThreadManager threadManager;
	private ThreadManager(){
		fixedThreadPool =  Executors.newFixedThreadPool(POOL_SIZE);
	}
	public static ExecutorService getInstance(){
		if(threadManager==null){
			threadManager = new ThreadManager();
		}
		return fixedThreadPool;
	}
}
