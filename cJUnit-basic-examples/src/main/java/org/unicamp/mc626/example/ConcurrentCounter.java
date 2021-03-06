package org.unicamp.mc626.example;

public class ConcurrentCounter {

	private int count = 0;

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
