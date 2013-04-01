package at.favre.tools.tagger.analyzer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public class WorkerManager {
	private static final int MAX_THREADS = 8;

	private static AtomicInteger finishedThreads = new AtomicInteger(0);
	private static WorkerManager ourInstance = new WorkerManager();

	private final ExecutorService threadPool;


	public static WorkerManager getInstance() {
		return ourInstance;
	}

	private WorkerManager() {
		threadPool = Executors.newFixedThreadPool(MAX_THREADS);
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public static AtomicInteger getFinishedThreads() {
		return finishedThreads;
	}
}
