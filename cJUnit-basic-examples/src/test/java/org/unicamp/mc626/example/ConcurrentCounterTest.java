package org.unicamp.mc626.example;

// import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import de.fzi.cjunit.ConcurrentTest;
import de.fzi.cjunit.runners.ConcurrentRunner;
import de.fzi.cjunit.util.TestBarrier;

@RunWith(ConcurrentRunner.class)
public class ConcurrentCounterTest {

	ConcurrentCounter counter = new ConcurrentCounter();
	ConcurrentCounterWithBug counterWithBug = new ConcurrentCounterWithBug();

	// @Test
	@ConcurrentTest(threadCount = 2)
	public void test() {
		counter.getNext();
		TestBarrier.await();
		assertEquals(2, counter.getCurrent());
	}

	@ConcurrentTest(threadCount = 2)
	public void test2() {
		counterWithBug.getNext();
		TestBarrier.await();
		assertEquals(2, counter.getCurrent());
	}

}
