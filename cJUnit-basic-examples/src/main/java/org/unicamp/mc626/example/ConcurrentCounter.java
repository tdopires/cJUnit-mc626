package org.unicamp.mc626.example;

public class ConcurrentCounter {
	int count = 0;

	public int getNext() {
		int c;
		synchronized (this) {
			c = ++count;
		}
		return ++count;
	}

	public synchronized int getCurrent() {
		return count;
	}
}
