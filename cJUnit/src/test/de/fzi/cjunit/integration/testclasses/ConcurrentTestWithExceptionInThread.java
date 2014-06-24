/*
 * This file is covered by the terms of the Common Public License v1.0.
 *
 * Copyright (c) SZEDER Gábor
 *
 * Parts of this software were developed within the JEOPARD research
 * project, which received funding from the European Union's Seventh
 * Framework Programme under grant agreement No. 216682.
 */

package de.fzi.cjunit.integration.testclasses;

import de.fzi.cjunit.ConcurrentTest;
import de.fzi.cjunit.testutils.OtherTestException;
import de.fzi.cjunit.testutils.TestException;


public class ConcurrentTestWithExceptionInThread {

	public static String message = "something went wrong";
	public static String causeMessage = "something else went wrong, too";

	public void throwCause() {
		throw new OtherTestException(causeMessage);
	}

	@ConcurrentTest
	public void concurrentTestMethod() throws InterruptedException {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					throwCause();
				} catch (Throwable cause) {
					cause.getStackTrace();
					TestException t = new TestException(
							message.toString(),
							cause);
					throw t;
				}
			}
		};
		t.start();
		t.join();
	}
}
