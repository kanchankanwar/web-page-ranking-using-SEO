package webapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchThread implements Runnable{

	String words;
	
	public SearchThread(String words) {
		// TODO Auto-generated constructor stub
		try {
			this.words = words;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// start existing approach algorithm
			callExisting();
			// start proposed approach algorithm
			callProposed();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void callExisting() throws Exception
	{
		final ExecutorService service = Executors.newFixedThreadPool(1);
		try {
			List<Callable<TaskResponse>> tasks = new ArrayList<>();
			tasks.add(new PageRankTask(words));
			final List<Future<TaskResponse>> results = service.invokeAll(tasks);
			final List<Map<String, Double>> resultList = handleResults(results);

			final Map<String, Double> finalResults = addAllResults(resultList);
			System.out.println("Final Existing result:"
					+ MapUtil.entriesSortedByValues(finalResults, false));
			ConstantsVar.listExisting =MapUtil.entriesSortedByValues(finalResults, false);
		} catch (Exception e) {
			throw new Exception();
		} finally {
			if (service != null)
				service.shutdown();
		}
	}
	
	public void callProposed() throws Exception
	{
		final ExecutorService service = Executors.newFixedThreadPool(3);
		try {
			List<Callable<TaskResponse>> tasks = new ArrayList<>();
			tasks.add(new PageRankTask(words));
			tasks.add(new WordFrequencyTask(words));
			tasks.add(new DocumentLocationTask(words));
			final List<Future<TaskResponse>> results = service.invokeAll(tasks);
			final List<Map<String, Double>> resultList = handleResults(results);

			final Map<String, Double> finalResults = addAllResults(resultList);
			System.out.println("Final Proposed result:"
					+ MapUtil.entriesSortedByValues(finalResults, false));
			ConstantsVar.listProposed =MapUtil.entriesSortedByValues(finalResults, false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
			
		} finally {
			if (service != null)
				service.shutdown();
		}
	}
	
	private List<Map<String, Double>> handleResults(
			final List<Future<TaskResponse>> results)
			throws InterruptedException, ExecutionException {
		final List<Map<String, Double>> resultList = new ArrayList<>();
		for (final Future<TaskResponse> result : results) {
			TaskResponse taskResponse = result.get();
			if (taskResponse.taskClass.equals(WordFrequencyTask.class)) {
				addWeighting(taskResponse.resultMap, 1.0);
				System.out.println("Word Frequency: "
						+ MapUtil.entriesSortedByValues(taskResponse.resultMap,
								false));
				resultList.add(taskResponse.resultMap);
			} else if (taskResponse.taskClass
					.equals(DocumentLocationTask.class)) {
				addWeighting(taskResponse.resultMap, 1.0);
				System.out.println("Document Location: "
						+ MapUtil.entriesSortedByValues(taskResponse.resultMap,
								false));
				resultList.add(taskResponse.resultMap);
			} else if (taskResponse.taskClass.equals(PageRankTask.class)) {
				addWeighting(taskResponse.resultMap, 2.0);
				System.out.println("Page Rank: "
						+ MapUtil.entriesSortedByValues(taskResponse.resultMap,
								false));
				resultList.add(taskResponse.resultMap);
			} else {
				System.out.println("No NN response.");
			}
		}
		return resultList;
	}
	
	private void addWeighting(final Map<String, Double> resultMap,
			final double weight) {
		for (final Map.Entry<String, Double> entry : resultMap.entrySet()) {
			entry.setValue(entry.getValue() * weight);

		}
	}
	
	private Map<String, Double> addAllResults(
			final List<Map<String, Double>> resultList) {
		final Map<String, Double> finalResults = new HashMap<>();
		for (final String url : resultList.get(0).keySet()) {
			double sum = 0;
			for (Map<String, Double> map : resultList) {
				sum += map.get(url) == null ? 0 : map.get(url);
			}
			finalResults.put(url, sum);
		}
		return finalResults;
	}
	
}
