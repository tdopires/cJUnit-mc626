package org.unicamp.mc626.example;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.fzi.cjunit.ConcurrentTest;
import de.fzi.cjunit.runners.ConcurrentRunner;
import de.fzi.cjunit.util.TestBarrier;

@RunWith(ConcurrentRunner.class)
public class SimpleDateFormatTest {
	
	private static final long EXPECTED_TIMESTAMP = 1287712800000l;
	private static final String SAMPLE_DATE = "20101022 00:00:00";
	
	private DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	
	@ConcurrentTest(threadCount = 2)
	public void testWithCJUnit() throws ParseException {
		Date date = format.parse(SAMPLE_DATE);
		TestBarrier.await();
		assertEquals(EXPECTED_TIMESTAMP, date.getTime());
	}
	
	
	// based on http://fahdshariff.blogspot.com.br/2010/08/dateformat-with-multiple-threads.html
	@Test
	public void testWithoutCJUnit() throws InterruptedException, ExecutionException {
		Callable<Date> task = new Callable<Date>() {
			public Date call() throws Exception {
				return format.parse(SAMPLE_DATE);
			}
		};

		// lets try 2 threads only
		ExecutorService exec = Executors.newFixedThreadPool(2);
		List<Future<Date>> results = new ArrayList<Future<Date>>();

		// perform 5 date conversions
		for (int i = 0; i < 5; i++) {
			results.add(exec.submit(task));
		}
		exec.shutdown();

		// look at the results
		for (Future<Date> result : results) {
			assertEquals(EXPECTED_TIMESTAMP, result.get().getTime());
		}
	}
}
