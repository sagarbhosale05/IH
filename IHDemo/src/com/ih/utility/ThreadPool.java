package com.ih.utility;

/**
 * @author Extentia
 * @description This class is responsible to control the number of threads that are running simultaneously within the
 * app. No more than 3-4 threads must run simultaneously for the app efficiency. Eveyy thread in the application must
 * use thread pool to start itself. 
 */


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The Class ThreadPool.
 */
public class ThreadPool 
{
	
	/** The pool size. */
	private static int poolSize = 4;
	
	/** The max pool size. */
	private static int maxPoolSize = 5;
	
	/** The keep alive time. */
	private static long keepAliveTime = 60*3;
 
	/** The thread pool executor. */
	private static ThreadPoolExecutor threadPoolExecutor = null;
	
	/** The thread pool. */
	private static ThreadPool threadPool = null;
	
	/** The queue. */
	private static ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10);
    
    // to implement singleton pattern
    /**
     * Instantiates a new thread pool.
     */
    private ThreadPool()
    {
    	threadPoolExecutor = new ThreadPoolExecutor(poolSize, maxPoolSize,keepAliveTime, TimeUnit.SECONDS, queue);
    	queue = new ArrayBlockingQueue<Runnable>(10);
    }
    
    /**
     * Gets the thread pool object.
     *
     * @return the thread pool object
     */
    public static synchronized ThreadPool getThreadPoolObject()
    {
    	
    	if(threadPool == null)
    	{
    		threadPool = new ThreadPool();
    	}
    	
    	return threadPool;
		
    }
 
    /**
     * Run task.
     *
     * @param task the task
     */
    public void runTask(Runnable task)
    {        
        threadPoolExecutor.execute(task);
        
    }
 
    /**
     * Shut down.
     */
    public void shutDown()
    {
    	threadPoolExecutor.shutdown();
    }
    
    /**
     * Destroy thread pool object.
     */
    public void destroyThreadPoolObject()
    {
    	threadPoolExecutor = null;
    	threadPool = null;
    }
 
}
