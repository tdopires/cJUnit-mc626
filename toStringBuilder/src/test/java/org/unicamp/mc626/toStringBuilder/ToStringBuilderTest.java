package org.unicamp.mc626.toStringBuilder;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.fzi.cjunit.ConcurrentTest;
import de.fzi.cjunit.runners.ConcurrentRunner;

@RunWith(ConcurrentRunner.class)
public class ToStringBuilderTest {
	
	private final Integer base = Integer.valueOf(5);
	
	@ConcurrentTest(threadCount = 2)
//	@Test
    public void testConstructorEx1() {
        assertEquals("<null>", new ToStringBuilder(null).toString());
    }

	@ConcurrentTest(threadCount = 2)
//	@Test
    public void testConstructorEx2() {
        assertEquals("<null>", new ToStringBuilder(null, null).toString());
        new ToStringBuilder(this.base, null).toString();
    }

	@ConcurrentTest(threadCount = 2)
//	@Test
    public void testConstructorEx3() {
        assertEquals("<null>", new ToStringBuilder(null, null, null).toString());
        new ToStringBuilder(this.base, null, null).toString();
        new ToStringBuilder(this.base, ToStringStyle.DEFAULT_STYLE, null).toString();
    }
	
	/**
     * Create the same toString() as Object.toString().
     * @param o the object to create the string for.
     * @return a String in the Object.toString format.
     */
    private String toBaseString(final Object o) {
        return o.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(o));
    }
    
	// Reflection Array tests

    //
    // Note on the following line of code repeated in the reflection array tests.
    //
    // assertReflectionArray("<null>", array);
    //
    // The expected value is not baseStr + "[<null>]" since array==null and is typed as Object.
    // The null array does not carry array type information.
    // If we added a primitive array type constructor and pile of associated methods,
    // then type declaring type information could be carried forward. IMHO, null is null.
    //
    // Gary Gregory - 2003-03-12 - ggregory@seagullsw.com
    //

    public void assertReflectionArray(final String expected, final Object actual) {
        if (actual == null) {
            // Until ToStringBuilder supports null objects.
            return;
        }
        assertEquals(expected, ToStringBuilder.reflectionToString(actual));
        assertEquals(expected, ToStringBuilder.reflectionToString(actual, null));
        assertEquals(expected, ToStringBuilder.reflectionToString(actual, null, true));
        assertEquals(expected, ToStringBuilder.reflectionToString(actual, null, false));
    }

    @ConcurrentTest(threadCount = 2)
    public void testReflectionObjectArray() {
        Object[] array = new Object[] { null, base, new int[] { 3, 6 } };
        final String baseString = this.toBaseString(array);
        assertEquals(baseString + "[{<null>,5,{3,6}}]", ToStringBuilder.reflectionToString(array));
        array = null;
        assertReflectionArray("<null>", array);
    }

    @ConcurrentTest(threadCount = 2)
    public void testReflectionLongArray() {
        long[] array = new long[] { 1, 2, -3, 4 };
        final String baseString = this.toBaseString(array);
        assertEquals(baseString + "[{1,2,-3,4}]", ToStringBuilder.reflectionToString(array));
        array = null;
        assertReflectionArray("<null>", array);
    }

    @ConcurrentTest(threadCount = 2)
    public void testReflectionIntArray() {
        int[] array = new int[] { 1, 2, -3, 4 };
        final String baseString = this.toBaseString(array);
        assertEquals(baseString + "[{1,2,-3,4}]", ToStringBuilder.reflectionToString(array));
        array = null;
        assertReflectionArray("<null>", array);
    }

    @ConcurrentTest(threadCount = 2)
    public void testReflectionShortArray() {
        short[] array = new short[] { 1, 2, -3, 4 };
        final String baseString = this.toBaseString(array);
        assertEquals(baseString + "[{1,2,-3,4}]", ToStringBuilder.reflectionToString(array));
        array = null;
        assertReflectionArray("<null>", array);
    }

    @ConcurrentTest(threadCount = 2)
    public void testReflectionyteArray() {
        byte[] array = new byte[] { 1, 2, -3, 4 };
        final String baseString = this.toBaseString(array);
        assertEquals(baseString + "[{1,2,-3,4}]", ToStringBuilder.reflectionToString(array));
        array = null;
        assertReflectionArray("<null>", array);
    }

    @ConcurrentTest(threadCount = 2)
    public void testReflectionCharArray() {
        char[] array = new char[] { 'A', '2', '_', 'D' };
        final String baseString = this.toBaseString(array);
        assertEquals(baseString + "[{A,2,_,D}]", ToStringBuilder.reflectionToString(array));
        array = null;
        assertReflectionArray("<null>", array);
    }

    @ConcurrentTest(threadCount = 2)
    public void testReflectionDoubleArray() {
        double[] array = new double[] { 1.0, 2.9876, -3.00001, 4.3 };
        final String baseString = this.toBaseString(array);
        assertEquals(baseString + "[{1.0,2.9876,-3.00001,4.3}]", ToStringBuilder.reflectionToString(array));
        array = null;
        assertReflectionArray("<null>", array);
    }

    @ConcurrentTest(threadCount = 2)
    public void testReflectionFloatArray() {
        float[] array = new float[] { 1.0f, 2.9876f, -3.00001f, 4.3f };
        final String baseString = this.toBaseString(array);
        assertEquals(baseString + "[{1.0,2.9876,-3.00001,4.3}]", ToStringBuilder.reflectionToString(array));
        array = null;
        assertReflectionArray("<null>", array);
    }

    @ConcurrentTest(threadCount = 2)
    public void testReflectionBooleanArray() {
        boolean[] array = new boolean[] { true, false, false };
        final String baseString = this.toBaseString(array);
        assertEquals(baseString + "[{true,false,false}]", ToStringBuilder.reflectionToString(array));
        array = null;
        assertReflectionArray("<null>", array);
    }
}
