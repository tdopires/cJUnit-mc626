package org.unicamp.mc626.example;

import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import de.fzi.cjunit.ConcurrentTest;
import de.fzi.cjunit.runners.ConcurrentRunner;
import de.fzi.cjunit.util.TestBarrier;

@RunWith(ConcurrentRunner.class)
public class ConcurrentCounterTest {
	int count = 0;
	ConcurrentCounter counter = new ConcurrentCounter();

	// @Test
	@ConcurrentTest(threadCount = 2)
	public void test() {
		counter.getNext();
		TestBarrier.await();
		assertEquals(2, counter.getCurrent());
	}

	class ConcurrentCounter {
		int count = 0;

		public int getNext() {
			int c;
			synchronized (this) {
				c = ++count;
			}
			return c;
		}

		public synchronized int getCurrent() {
			return count;
		}
	}
}
