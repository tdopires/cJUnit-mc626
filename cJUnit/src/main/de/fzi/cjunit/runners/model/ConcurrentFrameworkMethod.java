/*
 * This file is covered by the terms of the Common Public License v1.0.
 *
 * Copyright (c) SZEDER Gábor
 *
 * Parts of this software were developed within the JEOPARD research
 * project, which received funding from the European Union's Seventh
 * Framework Programme under grant agreement No. 216682.
 */

package de.fzi.cjunit.runners.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;

import de.fzi.cjunit.ConcurrentTest;
import de.fzi.cjunit.ConcurrentTest.None;

public class ConcurrentFrameworkMethod extends FrameworkMethod {

	List<ConcurrentFrameworkMethod> threadGroupMembers;

	public ConcurrentFrameworkMethod(Method method) {
		super(method);
		threadGroupMembers = new ArrayList<ConcurrentFrameworkMethod>();
	}

	public Class<? extends Throwable> getExpected() {
		Class<? extends Throwable> expected
			= getAnnotation(ConcurrentTest.class).expected();
		if (expected == None.class) {
			return null;
		} else {
			return expected;
		}
	}

	public int getThreadCount() {
		return getAnnotation(ConcurrentTest.class).threadCount();
	}

	public int getThreadGroup() {
		return getAnnotation(ConcurrentTest.class).threadGroup();
	}

	public void addThreadGroupMember(ConcurrentFrameworkMethod cfm) {
		threadGroupMembers.add(cfm);
	}

	public List<ConcurrentFrameworkMethod> getThreadGroupMembers() {
		return threadGroupMembers;
	}
}
