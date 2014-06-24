package org.unicamp.mc626.example;

public class ConcurrentCounterWithBug {

	private int count = 0;

	public int getNext() {
		return ++count;
	}

	public synchronized int getCurrent() {
		return count;
	}
}
