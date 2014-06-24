/*
 * This file is covered by the terms of the Common Public License v1.0.
 *
 * Copyright (c) SZEDER Gábor
 *
 * Parts of this software were developed within the JEOPARD research
 * project, which received funding from the European Union's Seventh
 * Framework Programme under grant agreement No. 216682.
 */

package de.fzi.cjunit.runners.statements;

import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import de.fzi.cjunit.runners.model.ConcurrentFrameworkMethod;

import static de.fzi.cjunit.jpf.inside.TestWrapperOptions.*;

import de.fzi.cjunit.jpf.inside.TestWrapper;
import de.fzi.cjunit.jpf.outside.JPFInvoker;


public class ConcurrentStatement extends Statement {

	public class MethodInfo {
		ConcurrentFrameworkMethod method;
		Class<? extends Throwable> exception;

		MethodInfo(ConcurrentFrameworkMethod method) {
			this.method = method;
		}

		MethodInfo(ConcurrentFrameworkMethod method,
				Class<? extends Throwable> exception) {
			this.method = method;
			this.exception = exception;
		}

		public ConcurrentFrameworkMethod getMethod() {
			return method;
		}

		public Class<? extends Throwable> getException() {
			return exception;
		}
	}

	protected List<MethodInfo> testMethods = new ArrayList<MethodInfo>();
	protected Object target;
	protected List<FrameworkMethod> befores;
	protected List<FrameworkMethod> afters;

	// for testing
	ConcurrentStatement() {}

	public ConcurrentStatement(ConcurrentFrameworkMethod testMethod,
			Object target) {
		testMethods.add(new MethodInfo(testMethod));
		this.target = target;
		befores = new ArrayList<FrameworkMethod>();
		afters = new ArrayList<FrameworkMethod>();
	}

	@Override
	public void evaluate() throws Throwable {
		invokeJPF();
	}

	protected void invokeJPF() throws Throwable {
		new JPFInvoker().run(createJPFArgs());
	}

	public void expectException(Class<? extends Throwable> expected) {
		testMethods.get(0).exception = expected;
	}

	public void addBefores(List<FrameworkMethod> befores) {
		this.befores = befores;
	}

	public void addAfters(List<FrameworkMethod> afters) {
		this.afters = afters;
	}

	public void addTestMethod(ConcurrentFrameworkMethod testMethod,
			Class<? extends Throwable> expected) {
		testMethods.add(new MethodInfo(testMethod, expected));
	}

	public List<MethodInfo> getTestMethods() {
		return testMethods;
	}

	protected String[] createJPFArgs() {
		List<String> testArgs = new ArrayList<String>();
		testArgs.add(TestWrapper.class.getName());
		testArgs.add(TestClassOpt + target.getClass().getName());
		for (MethodInfo mi : testMethods) {
			testArgs.add(TestOpt + MethodSubOpt
					+ mi.method.getName() + ","
					+ ExceptionSubOpt
					+ getExceptionClassName(mi.exception));
		}
		for (FrameworkMethod beforeMethod : befores) {
			testArgs.add(BeforeMethodOpt +
					beforeMethod.getName());
		}
		for (FrameworkMethod afterMethod : afters) {
			testArgs.add(AfterMethodOpt +
					afterMethod.getName());
		}

		return testArgs.toArray(new String[testArgs.size()]);
	}

	protected String getExceptionClassName(
			Class<? extends Throwable> exceptionClass) {
		if (exceptionClass == null) {
			return "";
		} else {
			return exceptionClass.getName();
		}
	}
}
