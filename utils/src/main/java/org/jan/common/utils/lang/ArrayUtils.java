package org.jan.common.utils.lang;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.jan.common.utils.reflect.ClassUtils;

/**
 * <p>Operations on arrays, primitive arrays (like <code>int[]</code>) and
 * primitive wrapper arrays (like <code>Integer[]</code>).</p>
 *
 * <p>This class tries to handle <code>null</code> input gracefully.
 * An exception will not be thrown for a <code>null</code>
 * array input. However, an Object array that contains a <code>null</code>
 * element may throw an exception. Each method documents its behaviour.</p>
 *
 * <p>#ThreadSafe#</p>
 * @author Jan.Wong
 * @since 1.0
 */
public class ArrayUtils {

    /**
     * An empty immutable <code>Object</code> array.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    /**
     * An empty immutable <code>Class</code> array.
     */
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    /**
     * An empty immutable <code>String</code> array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    /**
     * An empty immutable <code>long</code> array.
     */
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    /**
     * An empty immutable <code>Long</code> array.
     */
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
    /**
     * An empty immutable <code>int</code> array.
     */
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    /**
     * An empty immutable <code>Integer</code> array.
     */
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
    /**
     * An empty immutable <code>short</code> array.
     */
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    /**
     * An empty immutable <code>Short</code> array.
     */
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
    /**
     * An empty immutable <code>byte</code> array.
     */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    /**
     * An empty immutable <code>Byte</code> array.
     */
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
    /**
     * An empty immutable <code>double</code> array.
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    /**
     * An empty immutable <code>Double</code> array.
     */
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
    /**
     * An empty immutable <code>float</code> array.
     */
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    /**
     * An empty immutable <code>Float</code> array.
     */
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
    /**
     * An empty immutable <code>boolean</code> array.
     */
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    /**
     * An empty immutable <code>Boolean</code> array.
     */
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
    /**
     * An empty immutable <code>char</code> array.
     */
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    /**
     * An empty immutable <code>Character</code> array.
     */
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

    /**
     * The index value when an element is not found in a list or array: <code>-1</code>.
     * This value is returned by methods in this class and can also be used in comparisons with values returned by
     * various method from {@link java.util.List}.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * <p>ArrayUtils instances should NOT be constructed in standard programming.
     * Instead, the class should be used as <code>ArrayUtils.clone(new int[] {2})</code>.</p>
     *
     * <p>This constructor is public to permit tools that require a JavaBean instance
     * to operate.</p>
     */
    public ArrayUtils() {
      super();
    }

    // Basic methods handling multi-dimensional arrays
    //-----------------------------------------------------------------------
    /**
     * <p>Outputs an array as a String, treating <code>null</code> as an empty array.</p>
     *
     * <p>Multi-dimensional arrays are handled correctly, including
     * multi-dimensional primitive arrays.</p>
     *
     * <p>The format is that of Java source code, for example <code>{a,b}</code>.</p>
     *
     * @param array  the array to get a toString for, may be <code>null</code>
     * @return a String representation of the array, '{}' if null array input
     */
    public static String toString(Object array) {
        return toString(array, "{}");
    }

    /**
     * <p>Outputs an array as a String handling <code>null</code>s.</p>
     *
     * <p>Multi-dimensional arrays are handled correctly, including
     * multi-dimensional primitive arrays.</p>
     *
     * <p>The format is that of Java source code, for example <code>{a,b}</code>.</p>
     *
     * @param array  the array to get a toString for, may be <code>null</code>
     * @param stringIfNull  the String to return if the array is <code>null</code>
     * @return a String representation of the array
     */
    public static String toString(Object array, String stringIfNull) {
        if (array == null) {
            return stringIfNull;
        }
        ToStringStyle style = ToStringStyle.SIMPLE_STYLE;
        StringBuffer buffer = new StringBuffer(512);
        style.appendStart(buffer, array);
        style.append(buffer, null, array, null);
        style.appendEnd(buffer, array);
        return buffer.toString();
    }

    /**
     * <p>Get a hashCode for an array handling multi-dimensional arrays correctly.</p>
     *
     * <p>Multi-dimensional primitive arrays are also handled correctly by this method.</p>
     *
     * @param array  the array to get a hashCode for, may be <code>null</code>
     * @return a hashCode for the array, zero if null array input
     */
    public static int hashCode(Object array) {
        return new HashCodeBuilder().append(array).toHashCode();
    }

    /**
     * <p>Compares two arrays, using equals(), handling multi-dimensional arrays
     * correctly.</p>
     *
     * <p>Multi-dimensional primitive arrays are also handled correctly by this method.</p>
     *
     * @param array1  the left hand array to compare, may be <code>null</code>
     * @param array2  the right hand array to compare, may be <code>null</code>
     * @return <code>true</code> if the arrays are equal
     */
    public static boolean isEquals(Object array1, Object array2) {
        return new EqualsBuilder().append(array1, array2).isEquals();
    }

    // To map
    //-----------------------------------------------------------------------
    /**
     * <p>Converts the given array into a {@link java.util.Map}. Each element of the array
     * must be either a {@link java.util.Map.Entry} or an Array, containing at least two
     * elements, where the first element is used as key and the second as
     * value.</p>
     *
     * <p>This method can be used to initialize:</p>
     * <pre>
     * // Create a Map mapping colors.
     * Map colorMap = MapUtils.toMap(new String[][] {{
     *     {"RED", "#FF0000"},
     *     {"GREEN", "#00FF00"},
     *     {"BLUE", "#0000FF"}});
     * </pre>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  an array whose elements are either a {@link java.util.Map.Entry} or
     *  an Array containing at least two elements, may be <code>null</code>
     * @return a <code>Map</code> that was created from the array
     * @throws IllegalArgumentException  if one element of this Array is
     *  itself an Array containing less then two elements
     * @throws IllegalArgumentException  if the array contains elements other
     *  than {@link java.util.Map.Entry} and an Array
     */
    public static Map<Object, Object> toMap(Object[] array) {
        if (array == null) {
            return null;
        }
        final Map<Object, Object> map = new HashMap<Object, Object>((int) (array.length * 1.5));
        for (int i = 0; i < array.length; i++) {
            Object object = array[i];
            if (object instanceof Map.Entry<?, ?>) {
                Map.Entry<?,?> entry = (Map.Entry<?,?>) object;
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[]) {
                Object[] entry = (Object[]) object;
                if (entry.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + ", '"
                        + object
                        + "', has a length less than 2");
                }
                map.put(entry[0], entry[1]);
            } else {
                throw new IllegalArgumentException("Array element " + i + ", '"
                        + object
                        + "', is neither of type Map.Entry nor an Array");
            }
        }
        return map;
    }

 // Generic array
    //-----------------------------------------------------------------------
    /**
     * <p>Create a type-safe generic array.</p>
     *
     * <p>The Java language does not allow an array to be created from a generic type:</p>
     *
     * <pre>
    public static &lt;T&gt; T[] createAnArray(int size) {
        return new T[size]; // compiler error here
    }
    public static &lt;T&gt; T[] createAnArray(int size) {
        return (T[])new Object[size]; // ClassCastException at runtime
    }
     * </pre>
     *
     * <p>Therefore new arrays of generic types can be created with this method.
     * For example, an array of Strings can be created:</p>
     *
     * <pre>
    String[] array = ArrayUtils.toArray("1", "2");
    String[] emptyArray = ArrayUtils.&lt;String&gt;toArray();
     * </pre>
     *
     * <p>The method is typically used in scenarios, where the caller itself uses generic types
     * that have to be combined into an array.</p>
     *
     * <p>Note, this method makes only sense to provide arguments of the same type so that the
     * compiler can deduce the type of the array itself. While it is possible to select the
     * type explicitly like in
     * <code>Number[] array = ArrayUtils.&lt;Number&gt;toArray(Integer.valueOf(42), Double.valueOf(Math.PI))</code>,
     * there is no real advantage when compared to
     * <code>new Number[] {Integer.valueOf(42), Double.valueOf(Math.PI)}</code>.</p>
     *
     * @param  <T>   the array's element type
     * @param  items  the varargs array items, null allowed
     * @return the array, not null unless a null array is passed in
     * @since  3.0
     */
    public static <T> T[] toArray(final T... items) {
        return items;
    }

    // Clone
    //-----------------------------------------------------------------------
    /**
     * <p>Shallow clones an array returning a typecast result and handling
     * <code>null</code>.</p>
     *
     * <p>The objects in the array are not cloned, thus there is no special
     * handling for multi-dimensional arrays.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  the array to shallow clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static <T> T[] clone(T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  the array to clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static long[] clone(long[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  the array to clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static int[] clone(int[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  the array to clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static short[] clone(short[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  the array to clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static char[] clone(char[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  the array to clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  the array to clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static double[] clone(double[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  the array to clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static float[] clone(float[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  the array to clone, may be <code>null</code>
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static boolean[] clone(boolean[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    // nullToEmpty
    //-----------------------------------------------------------------------
    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static Object[] nullToEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static String[] nullToEmpty(String[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static long[] nullToEmpty(long[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static int[] nullToEmpty(int[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static short[] nullToEmpty(short[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static char[] nullToEmpty(char[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static byte[] nullToEmpty(byte[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static double[] nullToEmpty(double[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static float[] nullToEmpty(float[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static boolean[] nullToEmpty(boolean[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static Long[] nullToEmpty(Long[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static Integer[] nullToEmpty(Integer[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static Short[] nullToEmpty(Short[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static Character[] nullToEmpty(Character[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static Byte[] nullToEmpty(Byte[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static Double[] nullToEmpty(Double[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static Float[] nullToEmpty(Float[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a <code>null</code>
     * reference to an empty one.</p>
     *
     * <p>This method returns an empty array for a <code>null</code> input array.</p>
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty <code>public static</code> references in this class.</p>
     *
     * @param array  the array to check for <code>null</code> or empty
     * @return the same array, <code>public static</code> empty array if <code>null</code> or empty input
     * @since 1.0
     */
    public static Boolean[] nullToEmpty(Boolean[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        return array;
    }

    // Subarrays
    //-----------------------------------------------------------------------
    /**
     * <p>Produces a new array containing the elements between
     * the start and end indices.</p>
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.</p>
     *
     * <p>The component type of the subarray is always the same as
     * that of the input array. Thus, if the input is an array of type
     * <code>Date</code>, the following usage is envisaged:</p>
     *
     * <pre>
     * Date[] someDates = (Date[])ArrayUtils.subarray(allDates, 2, 5);
     * </pre>
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 1.0
     */
    public static <T> T[] subarray(T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        Class<?> type = array.getClass().getComponentType();
        if (newSize <= 0) {
            @SuppressWarnings("unchecked") // OK, because array is of type T
            final T[] emptyArray = (T[]) Array.newInstance(type, 0);
            return emptyArray;
        }
        @SuppressWarnings("unchecked") // OK, because array is of type T
        T[] subarray = (T[]) Array.newInstance(type, newSize);
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new <code>long</code> array containing the elements
     * between the start and end indices.</p>
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.</p>
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 1.0
     */
    public static long[] subarray(long[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_LONG_ARRAY;
        }

        long[] subarray = new long[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new <code>int</code> array containing the elements
     * between the start and end indices.</p>
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.</p>
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 1.0
     */
    public static int[] subarray(int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_INT_ARRAY;
        }

        int[] subarray = new int[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new <code>short</code> array containing the elements
     * between the start and end indices.</p>
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.</p>
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 1.0
     */
    public static short[] subarray(short[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_SHORT_ARRAY;
        }

        short[] subarray = new short[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new <code>char</code> array containing the elements
     * between the start and end indices.</p>
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.</p>
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 1.0
     */
    public static char[] subarray(char[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_CHAR_ARRAY;
        }

        char[] subarray = new char[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new <code>byte</code> array containing the elements
     * between the start and end indices.</p>
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.</p>
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 1.0
     */
    public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_BYTE_ARRAY;
        }

        byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new <code>double</code> array containing the elements
     * between the start and end indices.</p>
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.</p>
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 1.0
     */
    public static double[] subarray(double[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_DOUBLE_ARRAY;
        }

        double[] subarray = new double[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new <code>float</code> array containing the elements
     * between the start and end indices.</p>
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.</p>
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 1.0
     */
    public static float[] subarray(float[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_FLOAT_ARRAY;
        }

        float[] subarray = new float[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new <code>boolean</code> array containing the elements
     * between the start and end indices.</p>
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.</p>
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 1.0
     */
    public static boolean[] subarray(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }

        boolean[] subarray = new boolean[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    // Is same length
    //-----------------------------------------------------------------------
    /**
     * <p>Checks whether two arrays are the same length, treating
     * <code>null</code> arrays as length <code>0</code>.
     *
     * <p>Any multi-dimensional aspects of the arrays are ignored.</p>
     *
     * @param array1 the first array, may be <code>null</code>
     * @param array2 the second array, may be <code>null</code>
     * @return <code>true</code> if length of arrays matches, treating
     *  <code>null</code> as an empty array
     */
    public static boolean isSameLength(Object[] array1, Object[] array2) {
        if ((array1 == null && array2 != null && array2.length > 0) ||
            (array2 == null && array1 != null && array1.length > 0) ||
            (array1 != null && array2 != null && array1.length != array2.length)) {
                return false;
        }
        return true;
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * <code>null</code> arrays as length <code>0</code>.</p>
     *
     * @param array1 the first array, may be <code>null</code>
     * @param array2 the second array, may be <code>null</code>
     * @return <code>true</code> if length of arrays matches, treating
     *  <code>null</code> as an empty array
     */
    public static boolean isSameLength(long[] array1, long[] array2) {
        if ((array1 == null && array2 != null && array2.length > 0) ||
            (array2 == null && array1 != null && array1.length > 0) ||
            (array1 != null && array2 != null && array1.length != array2.length)) {
                return false;
        }
        return true;
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * <code>null</code> arrays as length <code>0</code>.</p>
     *
     * @param array1 the first array, may be <code>null</code>
     * @param array2 the second array, may be <code>null</code>
     * @return <code>true</code> if length of arrays matches, treating
     *  <code>null</code> as an empty array
     */
    public static boolean isSameLength(int[] array1, int[] array2) {
        if ((array1 == null && array2 != null && array2.length > 0) ||
            (array2 == null && array1 != null && array1.length > 0) ||
            (array1 != null && array2 != null && array1.length != array2.length)) {
                return false;
        }
        return true;
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * <code>null</code> arrays as length <code>0</code>.</p>
     *
     * @param array1 the first array, may be <code>null</code>
     * @param array2 the second array, may be <code>null</code>
     * @return <code>true</code> if length of arrays matches, treating
     *  <code>null</code> as an empty array
     */
    public static boolean isSameLength(short[] array1, short[] array2) {
        if ((array1 == null && array2 != null && array2.length > 0) ||
            (array2 == null && array1 != null && array1.length > 0) ||
            (array1 != null && array2 != null && array1.length != array2.length)) {
                return false;
        }
        return true;
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * <code>null</code> arrays as length <code>0</code>.</p>
     *
     * @param array1 the first array, may be <code>null</code>
     * @param array2 the second array, may be <code>null</code>
     * @return <code>true</code> if length of arrays matches, treating
     *  <code>null</code> as an empty array
     */
    public static boolean isSameLength(char[] array1, char[] array2) {
        if ((array1 == null && array2 != null && array2.length > 0) ||
            (array2 == null && array1 != null && array1.length > 0) ||
            (array1 != null && array2 != null && array1.length != array2.length)) {
                return false;
        }
        return true;
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * <code>null</code> arrays as length <code>0</code>.</p>
     *
     * @param array1 the first array, may be <code>null</code>
     * @param array2 the second array, may be <code>null</code>
     * @return <code>true</code> if length of arrays matches, treating
     *  <code>null</code> as an empty array
     */
    public static boolean isSameLength(byte[] array1, byte[] array2) {
        if ((array1 == null && array2 != null && array2.length > 0) ||
            (array2 == null && array1 != null && array1.length > 0) ||
            (array1 != null && array2 != null && array1.length != array2.length)) {
                return false;
        }
        return true;
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * <code>null</code> arrays as length <code>0</code>.</p>
     *
     * @param array1 the first array, may be <code>null</code>
     * @param array2 the second array, may be <code>null</code>
     * @return <code>true</code> if length of arrays matches, treating
     *  <code>null</code> as an empty array
     */
    public static boolean isSameLength(double[] array1, double[] array2) {
        if ((array1 == null && array2 != null && array2.length > 0) ||
            (array2 == null && array1 != null && array1.length > 0) ||
            (array1 != null && array2 != null && array1.length != array2.length)) {
                return false;
        }
        return true;
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * <code>null</code> arrays as length <code>0</code>.</p>
     *
     * @param array1 the first array, may be <code>null</code>
     * @param array2 the second array, may be <code>null</code>
     * @return <code>true</code> if length of arrays matches, treating
     *  <code>null</code> as an empty array
     */
    public static boolean isSameLength(float[] array1, float[] array2) {
        if ((array1 == null && array2 != null && array2.length > 0) ||
            (array2 == null && array1 != null && array1.length > 0) ||
            (array1 != null && array2 != null && array1.length != array2.length)) {
                return false;
        }
        return true;
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * <code>null</code> arrays as length <code>0</code>.</p>
     *
     * @param array1 the first array, may be <code>null</code>
     * @param array2 the second array, may be <code>null</code>
     * @return <code>true</code> if length of arrays matches, treating
     *  <code>null</code> as an empty array
     */
    public static boolean isSameLength(boolean[] array1, boolean[] array2) {
        if ((array1 == null && array2 != null && array2.length > 0) ||
            (array2 == null && array1 != null && array1.length > 0) ||
            (array1 != null && array2 != null && array1.length != array2.length)) {
                return false;
        }
        return true;
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Returns the length of the specified array.
     * This method can deal with <code>Object</code> arrays and with primitive arrays.</p>
     *
     * <p>If the input array is <code>null</code>, <code>0</code> is returned.</p>
     *
     * <pre>
     * ArrayUtils.getLength(null)            = 0
     * ArrayUtils.getLength([])              = 0
     * ArrayUtils.getLength([null])          = 1
     * ArrayUtils.getLength([true, false])   = 2
     * ArrayUtils.getLength([1, 2, 3])       = 3
     * ArrayUtils.getLength(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param array  the array to retrieve the length from, may be null
     * @return The length of the array, or <code>0</code> if the array is <code>null</code>
     * @throws IllegalArgumentException if the object arguement is not an array.
     * @since 1.0
     */
    public static int getLength(Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    /**
     * <p>Checks whether two arrays are the same type taking into account
     * multi-dimensional arrays.</p>
     *
     * @param array1 the first array, must not be <code>null</code>
     * @param array2 the second array, must not be <code>null</code>
     * @return <code>true</code> if type of arrays matches
     * @throws IllegalArgumentException if either array is <code>null</code>
     */
    public static boolean isSameType(Object array1, Object array2) {
        if (array1 == null || array2 == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        return array1.getClass().getName().equals(array2.getClass().getName());
    }

    // Reverse
    //-----------------------------------------------------------------------
    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>There is no special handling for multi-dimensional arrays.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array  the array to reverse, may be <code>null</code>
     */
    public static void reverse(Object[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        Object tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array  the array to reverse, may be <code>null</code>
     */
    public static void reverse(long[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        long tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array  the array to reverse, may be <code>null</code>
     */
    public static void reverse(int[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array  the array to reverse, may be <code>null</code>
     */
    public static void reverse(short[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        short tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array  the array to reverse, may be <code>null</code>
     */
    public static void reverse(char[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        char tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array  the array to reverse, may be <code>null</code>
     */
    public static void reverse(byte[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        byte tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array  the array to reverse, may be <code>null</code>
     */
    public static void reverse(double[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        double tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array  the array to reverse, may be <code>null</code>
     */
    public static void reverse(float[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        float tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.</p>
     *
     * <p>This method does nothing for a <code>null</code> input array.</p>
     *
     * @param array  the array to reverse, may be <code>null</code>
     */
    public static void reverse(boolean[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        boolean tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    // IndexOf search
    // ----------------------------------------------------------------------

    // Object IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given object in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param objectToFind  the object to find, may be <code>null</code>
     * @return the index of the object within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    /**
     * <p>Finds the index of the given object in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param objectToFind  the object to find, may be <code>null</code>
     * @param startIndex  the index to start searching at
     * @return the index of the object within the array starting at the index,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the last index of the given object within the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to travers backwords looking for the object, may be <code>null</code>
     * @param objectToFind  the object to find, may be <code>null</code>
     * @return the last index of the object within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(Object[] array, Object objectToFind) {
        return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given object in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} (<code>-1</code>). A startIndex larger than
     * the array length will search from the end of the array.</p>
     *
     * @param array  the array to traverse for looking for the object, may be <code>null</code>
     * @param objectToFind  the object to find, may be <code>null</code>
     * @param startIndex  the start index to travers backwards from
     * @return the last index of the object within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
            for (int i = startIndex; i >= 0; i--) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks if the object is in the given array.</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
     *
     * @param array  the array to search through
     * @param objectToFind  the object to find
     * @return <code>true</code> if the array contains the object
     */
    public static boolean contains(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind) != INDEX_NOT_FOUND;
    }

    // long IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(long[] array, long valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(long[] array, long valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the last index of the given value within the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to travers backwords looking for the object, may be <code>null</code>
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(long[] array, long valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} (<code>-1</code>). A startIndex larger than the
     * array length will search from the end of the array.</p>
     *
     * @param array  the array to traverse for looking for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the start index to travers backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(long[] array, long valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks if the value is in the given array.</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return <code>true</code> if the array contains the object
     */
    public static boolean contains(long[] array, long valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    // int IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(int[] array, int valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the last index of the given value within the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to travers backwords looking for the object, may be <code>null</code>
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(int[] array, int valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} (<code>-1</code>). A startIndex larger than the
     * array length will search from the end of the array.</p>
     *
     * @param array  the array to traverse for looking for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the start index to travers backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks if the value is in the given array.</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return <code>true</code> if the array contains the object
     */
    public static boolean contains(int[] array, int valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    // short IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(short[] array, short valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the last index of the given value within the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to travers backwords looking for the object, may be <code>null</code>
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(short[] array, short valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} (<code>-1</code>). A startIndex larger than the
     * array length will search from the end of the array.</p>
     *
     * @param array  the array to traverse for looking for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the start index to travers backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks if the value is in the given array.</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return <code>true</code> if the array contains the object
     */
    public static boolean contains(short[] array, short valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    // char IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     * @since 1.0
     */
    public static int indexOf(char[] array, char valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     * @since 1.0
     */
    public static int indexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the last index of the given value within the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to travers backwords looking for the object, may be <code>null</code>
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     * @since 1.0
     */
    public static int lastIndexOf(char[] array, char valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} (<code>-1</code>). A startIndex larger than the
     * array length will search from the end of the array.</p>
     *
     * @param array  the array to traverse for looking for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the start index to travers backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     * @since 1.0
     */
    public static int lastIndexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks if the value is in the given array.</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return <code>true</code> if the array contains the object
     * @since 1.0
     */
    public static boolean contains(char[] array, char valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    // byte IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(byte[] array, byte valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(byte[] array, byte valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the last index of the given value within the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to travers backwords looking for the object, may be <code>null</code>
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(byte[] array, byte valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} (<code>-1</code>). A startIndex larger than the
     * array length will search from the end of the array.</p>
     *
     * @param array  the array to traverse for looking for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the start index to travers backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(byte[] array, byte valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks if the value is in the given array.</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return <code>true</code> if the array contains the object
     */
    public static boolean contains(byte[] array, byte valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    // double IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(double[] array, double valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * <p>Finds the index of the given value within a given tolerance in the array.
     * This method will return the index of the first value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param tolerance tolerance of the search
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance);
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(double[] array, double valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.
     * This method will return the index of the first value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @param tolerance tolerance of the search
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        double min = valueToFind - tolerance;
        double max = valueToFind + tolerance;
        for (int i = startIndex; i < array.length; i++) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the last index of the given value within the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to travers backwords looking for the object, may be <code>null</code>
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(double[] array, double valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value within a given tolerance in the array.
     * This method will return the index of the last value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param tolerance tolerance of the search
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(double[] array, double valueToFind, double tolerance) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE, tolerance);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} (<code>-1</code>). A startIndex larger than the
     * array length will search from the end of the array.</p>
     *
     * @param array  the array to traverse for looking for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the start index to travers backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(double[] array, double valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.
     * This method will return the index of the last value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} (<code>-1</code>). A startIndex larger than the
     * array length will search from the end of the array.</p>
     *
     * @param array  the array to traverse for looking for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the start index to travers backwards from
     * @param tolerance  search for value within plus/minus this amount
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        double min = valueToFind - tolerance;
        double max = valueToFind + tolerance;
        for (int i = startIndex; i >= 0; i--) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks if the value is in the given array.</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return <code>true</code> if the array contains the object
     */
    public static boolean contains(double[] array, double valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks if a value falling within the given tolerance is in the
     * given array.  If the array contains a value within the inclusive range
     * defined by (value - tolerance) to (value + tolerance).</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array
     * is passed in.</p>
     *
     * @param array  the array to search
     * @param valueToFind  the value to find
     * @param tolerance  the array contains the tolerance of the search
     * @return true if value falling within tolerance is in array
     */
    public static boolean contains(double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance) != INDEX_NOT_FOUND;
    }

    // float IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(float[] array, float valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(float[] array, float valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the last index of the given value within the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to travers backwords looking for the object, may be <code>null</code>
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(float[] array, float valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} (<code>-1</code>). A startIndex larger than the
     * array length will search from the end of the array.</p>
     *
     * @param array  the array to traverse for looking for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the start index to travers backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(float[] array, float valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks if the value is in the given array.</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return <code>true</code> if the array contains the object
     */
    public static boolean contains(float[] array, float valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    // boolean IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int indexOf(boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} (<code>-1</code>).</p>
     *
     * @param array  the array to search through for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code>
     *  array input
     */
    public static int indexOf(boolean[] array, boolean valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Finds the last index of the given value within the array.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) if
     * <code>null</code> array input.</p>
     *
     * @param array  the array to travers backwords looking for the object, may be <code>null</code>
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(boolean[] array, boolean valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.</p>
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.</p>
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} (<code>-1</code>). A startIndex larger than
     * the array length will search from the end of the array.</p>
     *
     * @param array  the array to traverse for looking for the object, may be <code>null</code>
     * @param valueToFind  the value to find
     * @param startIndex  the start index to travers backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or <code>null</code> array input
     */
    public static int lastIndexOf(boolean[] array, boolean valueToFind, int startIndex) {
        if (ArrayUtils.isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>Checks if the value is in the given array.</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return <code>true</code> if the array contains the object
     */
    public static boolean contains(boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    // Primitive/Object array converters
    // ----------------------------------------------------------------------

    // Character array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Characters to primitives.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Character</code> array, may be <code>null</code>
     * @return a <code>char</code> array, <code>null</code> if null array input
     * @throws NullPointerException if array content is <code>null</code>
     */
    public static char[] toPrimitive(Character[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].charValue();
        }
        return result;
    }

    /**
     * <p>Converts an array of object Character to primitives handling <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Character</code> array, may be <code>null</code>
     * @param valueForNull  the value to insert if <code>null</code> found
     * @return a <code>char</code> array, <code>null</code> if null array input
     */
    public static char[] toPrimitive(Character[] array, char valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            Character b = array[i];
            result[i] = (b == null ? valueForNull : b.charValue());
        }
        return result;
    }

    /**
     * <p>Converts an array of primitive chars to objects.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array a <code>char</code> array
     * @return a <code>Character</code> array, <code>null</code> if null array input
     */
    public static Character[] toObject(char[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        final Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Character.valueOf(array[i]);
        }
        return result;
     }

    // Long array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Longs to primitives.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Long</code> array, may be <code>null</code>
     * @return a <code>long</code> array, <code>null</code> if null array input
     * @throws NullPointerException if array content is <code>null</code>
     */
    public static long[] toPrimitive(Long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    /**
     * <p>Converts an array of object Long to primitives handling <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Long</code> array, may be <code>null</code>
     * @param valueForNull  the value to insert if <code>null</code> found
     * @return a <code>long</code> array, <code>null</code> if null array input
     */
    public static long[] toPrimitive(Long[] array, long valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            Long b = array[i];
            result[i] = (b == null ? valueForNull : b.longValue());
        }
        return result;
    }

    /**
     * <p>Converts an array of primitive longs to objects.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>long</code> array
     * @return a <code>Long</code> array, <code>null</code> if null array input
     */
    public static Long[] toObject(long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        final Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Long.valueOf(array[i]);
        }
        return result;
    }

    // Int array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Integers to primitives.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Integer</code> array, may be <code>null</code>
     * @return an <code>int</code> array, <code>null</code> if null array input
     * @throws NullPointerException if array content is <code>null</code>
     */
    public static int[] toPrimitive(Integer[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    /**
     * <p>Converts an array of object Integer to primitives handling <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Integer</code> array, may be <code>null</code>
     * @param valueForNull  the value to insert if <code>null</code> found
     * @return an <code>int</code> array, <code>null</code> if null array input
     */
    public static int[] toPrimitive(Integer[] array, int valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            Integer b = array[i];
            result[i] = (b == null ? valueForNull : b.intValue());
        }
        return result;
    }

    /**
     * <p>Converts an array of primitive ints to objects.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  an <code>int</code> array
     * @return an <code>Integer</code> array, <code>null</code> if null array input
     */
    public static Integer[] toObject(int[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        final Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.valueOf(array[i]);
        }
        return result;
    }

    // Short array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Shorts to primitives.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Short</code> array, may be <code>null</code>
     * @return a <code>byte</code> array, <code>null</code> if null array input
     * @throws NullPointerException if array content is <code>null</code>
     */
    public static short[] toPrimitive(Short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    /**
     * <p>Converts an array of object Short to primitives handling <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Short</code> array, may be <code>null</code>
     * @param valueForNull  the value to insert if <code>null</code> found
     * @return a <code>byte</code> array, <code>null</code> if null array input
     */
    public static short[] toPrimitive(Short[] array, short valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            Short b = array[i];
            result[i] = (b == null ? valueForNull : b.shortValue());
        }
        return result;
    }

    /**
     * <p>Converts an array of primitive shorts to objects.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>short</code> array
     * @return a <code>Short</code> array, <code>null</code> if null array input
     */
    public static Short[] toObject(short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        final Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Short.valueOf(array[i]);
        }
        return result;
    }

    // Byte array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Bytes to primitives.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Byte</code> array, may be <code>null</code>
     * @return a <code>byte</code> array, <code>null</code> if null array input
     * @throws NullPointerException if array content is <code>null</code>
     */
    public static byte[] toPrimitive(Byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    /**
     * <p>Converts an array of object Bytes to primitives handling <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Byte</code> array, may be <code>null</code>
     * @param valueForNull  the value to insert if <code>null</code> found
     * @return a <code>byte</code> array, <code>null</code> if null array input
     */
    public static byte[] toPrimitive(Byte[] array, byte valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            Byte b = array[i];
            result[i] = (b == null ? valueForNull : b.byteValue());
        }
        return result;
    }

    /**
     * <p>Converts an array of primitive bytes to objects.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>byte</code> array
     * @return a <code>Byte</code> array, <code>null</code> if null array input
     */
    public static Byte[] toObject(byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        final Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Byte.valueOf(array[i]);
        }
        return result;
    }

    // Double array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Doubles to primitives.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Double</code> array, may be <code>null</code>
     * @return a <code>double</code> array, <code>null</code> if null array input
     * @throws NullPointerException if array content is <code>null</code>
     */
    public static double[] toPrimitive(Double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    /**
     * <p>Converts an array of object Doubles to primitives handling <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Double</code> array, may be <code>null</code>
     * @param valueForNull  the value to insert if <code>null</code> found
     * @return a <code>double</code> array, <code>null</code> if null array input
     */
    public static double[] toPrimitive(Double[] array, double valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            Double b = array[i];
            result[i] = (b == null ? valueForNull : b.doubleValue());
        }
        return result;
    }

    /**
     * <p>Converts an array of primitive doubles to objects.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>double</code> array
     * @return a <code>Double</code> array, <code>null</code> if null array input
     */
    public static Double[] toObject(double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        final Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Double.valueOf(array[i]);
        }
        return result;
    }

    //   Float array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Floats to primitives.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Float</code> array, may be <code>null</code>
     * @return a <code>float</code> array, <code>null</code> if null array input
     * @throws NullPointerException if array content is <code>null</code>
     */
    public static float[] toPrimitive(Float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    /**
     * <p>Converts an array of object Floats to primitives handling <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Float</code> array, may be <code>null</code>
     * @param valueForNull  the value to insert if <code>null</code> found
     * @return a <code>float</code> array, <code>null</code> if null array input
     */
    public static float[] toPrimitive(Float[] array, float valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            Float b = array[i];
            result[i] = (b == null ? valueForNull : b.floatValue());
        }
        return result;
    }

    /**
     * <p>Converts an array of primitive floats to objects.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>float</code> array
     * @return a <code>Float</code> array, <code>null</code> if null array input
     */
    public static Float[] toObject(float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        final Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Float.valueOf(array[i]);
        }
        return result;
    }

    // Boolean array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Booleans to primitives.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Boolean</code> array, may be <code>null</code>
     * @return a <code>boolean</code> array, <code>null</code> if null array input
     * @throws NullPointerException if array content is <code>null</code>
     */
    public static boolean[] toPrimitive(Boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].booleanValue();
        }
        return result;
    }

    /**
     * <p>Converts an array of object Booleans to primitives handling <code>null</code>.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>Boolean</code> array, may be <code>null</code>
     * @param valueForNull  the value to insert if <code>null</code> found
     * @return a <code>boolean</code> array, <code>null</code> if null array input
     */
    public static boolean[] toPrimitive(Boolean[] array, boolean valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            Boolean b = array[i];
            result[i] = (b == null ? valueForNull : b.booleanValue());
        }
        return result;
    }

    /**
     * <p>Converts an array of primitive booleans to objects.</p>
     *
     * <p>This method returns <code>null</code> for a <code>null</code> input array.</p>
     *
     * @param array  a <code>boolean</code> array
     * @return a <code>Boolean</code> array, <code>null</code> if null array input
     */
    public static Boolean[] toObject(boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        final Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (array[i] ? Boolean.TRUE : Boolean.FALSE);
        }
        return result;
    }

    // ----------------------------------------------------------------------
    /**
     * <p>Checks if an array of Objects is empty or <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 1.0
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive longs is empty or <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 1.0
     */
    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive ints is empty or <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 1.0
     */
    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive shorts is empty or <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 1.0
     */
    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive chars is empty or <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 1.0
     */
    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive bytes is empty or <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 1.0
     */
    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive doubles is empty or <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 1.0
     */
    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive floats is empty or <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 1.0
     */
    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive booleans is empty or <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 1.0
     */
    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    // ----------------------------------------------------------------------
    /**
     * <p>Checks if an array of Objects is not empty or not <code>null</code>.</p>
     * @param <T>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is not empty or not <code>null</code>
     * @since 1.0
     */
     public static <T> boolean isNotEmpty(T[] array) {
         return (array != null && array.length != 0);
     }

    /**
     * <p>Checks if an array of primitive longs is not empty or not <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is not empty or not <code>null</code>
     * @since 1.0
     */
    public static boolean isNotEmpty(long[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive ints is not empty or not <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is not empty or not <code>null</code>
     * @since 1.0
     */
    public static boolean isNotEmpty(int[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive shorts is not empty or not <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is not empty or not <code>null</code>
     * @since 1.0
     */
    public static boolean isNotEmpty(short[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive chars is not empty or not <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is not empty or not <code>null</code>
     * @since 1.0
     */
    public static boolean isNotEmpty(char[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive bytes is not empty or not <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is not empty or not <code>null</code>
     * @since 1.0
     */
    public static boolean isNotEmpty(byte[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive doubles is not empty or not <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is not empty or not <code>null</code>
     * @since 1.0
     */
    public static boolean isNotEmpty(double[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive floats is not empty or not <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is not empty or not <code>null</code>
     * @since 1.0
     */
    public static boolean isNotEmpty(float[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive booleans is not empty or not <code>null</code>.</p>
     *
     * @param array  the array to test
     * @return <code>true</code> if the array is not empty or not <code>null</code>
     * @since 1.0
     */
    public static boolean isNotEmpty(boolean[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(null, null)     = null
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll([null], [null]) = [null, null]
     * ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array, may be <code>null</code>
     * @param array2  the second array whose elements are added to the new array, may be <code>null</code>
     * @return The new array, <code>null</code> if both arrays are <code>null</code>.
     *      The type of the new array is the type of the first array,
     *      unless the first array is null, in which case the type is the same as the second array.
     * @since 1.0
     * @throws IllegalArgumentException if the array types are incompatible
     */
    public static <T> T[] addAll(T[] array1, T... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        final Class<?> type1 = array1.getClass().getComponentType();
        @SuppressWarnings("unchecked") // OK, because array is of type T
        T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (ArrayStoreException ase) {
            // Check if problem was due to incompatible types
            /*
             * We do this here, rather than before the copy because:
             * - it would be a wasted check most of the time
             * - safer, in case check turns out to be too strict
             */
            final Class<?> type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)){
                throw new IllegalArgumentException("Cannot store "+type2.getName()+" in an array of "
                        +type1.getName(), ase);
            }
            throw ase; // No, so rethrow original
        }
        return joinedArray;
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new boolean[] array.
     * @since 1.0
     */
    public static boolean[] addAll(boolean[] array1, boolean... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        boolean[] joinedArray = new boolean[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new char[] array.
     * @since 1.0
     */
    public static char[] addAll(char[] array1, char... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        char[] joinedArray = new char[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new byte[] array.
     * @since 1.0
     */
    public static byte[] addAll(byte[] array1, byte... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new short[] array.
     * @since 1.0
     */
    public static short[] addAll(short[] array1, short... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        short[] joinedArray = new short[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new int[] array.
     * @since 1.0
     */
    public static int[] addAll(int[] array1, int... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        int[] joinedArray = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new long[] array.
     * @since 1.0
     */
    public static long[] addAll(long[] array1, long... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        long[] joinedArray = new long[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new float[] array.
     * @since 1.0
     */
    public static float[] addAll(float[] array1, float... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        float[] joinedArray = new float[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of <code>array1</code> followed
     * by all of the elements <code>array2</code>. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new double[] array.
     * @since 1.0
     */
    public static double[] addAll(double[] array1, double... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        double[] joinedArray = new double[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element, unless the element itself is null,
     *  in which case the return type is Object[]</p>
     *
     * <pre>
     * ArrayUtils.add(null, null)      = [null]
     * ArrayUtils.add(null, "a")       = ["a"]
     * ArrayUtils.add(["a"], null)     = ["a", null]
     * ArrayUtils.add(["a"], "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param array  the array to "add" the element to, may be <code>null</code>
     * @param element  the object to add, may be <code>null</code>
     * @return A new array containing the existing elements plus the new element
     * The returned array type will be that of the input array (unless null),
     * in which case it will have the same type as the element.
     * @since 1.0
     */
    public static <T> T[] add(T[] array, T element) {
        Class<?> type;
        if (array != null){
            type = array.getClass();
        } else if (element != null) {
            type = element.getClass();
        } else {
            throw new IllegalArgumentException("Arguments cannot both be null");
        }
        @SuppressWarnings("unchecked") // type must be T
        T[] newArray = (T[]) copyArrayGrow1(array, type);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, true)          = [true]
     * ArrayUtils.add([true], false)       = [true, false]
     * ArrayUtils.add([true, false], true) = [true, false, true]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be <code>null</code>
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 1.0
     */
    public static boolean[] add(boolean[] array, boolean element) {
        boolean[] newArray = (boolean[])copyArrayGrow1(array, Boolean.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be <code>null</code>
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 1.0
     */
    public static byte[] add(byte[] array, byte element) {
        byte[] newArray = (byte[])copyArrayGrow1(array, Byte.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, '0')       = ['0']
     * ArrayUtils.add(['1'], '0')      = ['1', '0']
     * ArrayUtils.add(['1', '0'], '1') = ['1', '0', '1']
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be <code>null</code>
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 1.0
     */
    public static char[] add(char[] array, char element) {
        char[] newArray = (char[])copyArrayGrow1(array, Character.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be <code>null</code>
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 1.0
     */
    public static double[] add(double[] array, double element) {
        double[] newArray = (double[])copyArrayGrow1(array, Double.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be <code>null</code>
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 1.0
     */
    public static float[] add(float[] array, float element) {
        float[] newArray = (float[])copyArrayGrow1(array, Float.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be <code>null</code>
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 1.0
     */
    public static int[] add(int[] array, int element) {
        int[] newArray = (int[])copyArrayGrow1(array, Integer.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be <code>null</code>
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 1.0
     */
    public static long[] add(long[] array, long element) {
        long[] newArray = (long[])copyArrayGrow1(array, Long.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be <code>null</code>
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 1.0
     */
    public static short[] add(short[] array, short element) {
        short[] newArray = (short[])copyArrayGrow1(array, Short.TYPE);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * Returns a copy of the given array of size 1 greater than the argument.
     * The last value of the array is left to the default value.
     *
     * @param array The array to copy, must not be <code>null</code>.
     * @param newArrayComponentType If <code>array</code> is <code>null</code>, create a
     * size 1 array of this type.
     * @return A new copy of the array of size 1 greater than the input.
     */
    private static Object copyArrayGrow1(Object array, Class<?> newArrayComponentType) {
        if (array != null) {
            int arrayLength = Array.getLength(array);
            Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
            System.arraycopy(array, 0, newArray, 0, arrayLength);
            return newArray;
        }
        return Array.newInstance(newArrayComponentType, 1);
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0, null)      = [null]
     * ArrayUtils.add(null, 0, "a")       = ["a"]
     * ArrayUtils.add(["a"], 1, null)     = ["a", null]
     * ArrayUtils.add(["a"], 1, "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], 3, "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param array  the array to add the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > array.length).
     */
    public static <T> T[] add(T[] array, int index, T element) {
        Class<?> clss = null;
        if (array != null) {
            clss = array.getClass().getComponentType();
        } else if (element != null) {
            clss = element.getClass();
        } else {
            throw new IllegalArgumentException("Array and element cannot both be null");
        }
        @SuppressWarnings("unchecked") // the add method creates an array of type clss, which is type T
        final T[] newArray = (T[]) add(array, index, element, clss);
        return newArray;
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0, true)          = [true]
     * ArrayUtils.add([true], 0, false)       = [false, true]
     * ArrayUtils.add([false], 1, true)       = [false, true]
     * ArrayUtils.add([true, false], 1, true) = [true, true, false]
     * </pre>
     *
     * @param array  the array to add the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > array.length).
     */
    public static boolean[] add(boolean[] array, int index, boolean element) {
        return (boolean[]) add(array, index, Boolean.valueOf(element), Boolean.TYPE);
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0, 'a')            = ['a']
     * ArrayUtils.add(['a'], 0, 'b')           = ['b', 'a']
     * ArrayUtils.add(['a', 'b'], 0, 'c')      = ['c', 'a', 'b']
     * ArrayUtils.add(['a', 'b'], 1, 'k')      = ['a', 'k', 'b']
     * ArrayUtils.add(['a', 'b', 'c'], 1, 't') = ['a', 't', 'b', 'c']
     * </pre>
     *
     * @param array  the array to add the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > array.length).
     */
    public static char[] add(char[] array, int index, char element) {
        return (char[]) add(array, index, Character.valueOf(element), Character.TYPE);
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 3)      = [2, 6, 3]
     * ArrayUtils.add([2, 6], 0, 1)      = [1, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > array.length).
     */
    public static byte[] add(byte[] array, int index, byte element) {
        return (byte[]) add(array, index, Byte.valueOf(element), Byte.TYPE);
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 10)     = [2, 6, 10]
     * ArrayUtils.add([2, 6], 0, -4)     = [-4, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > array.length).
     */
    public static short[] add(short[] array, int index, short element) {
        return (short[]) add(array, index, Short.valueOf(element), Short.TYPE);
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 10)     = [2, 6, 10]
     * ArrayUtils.add([2, 6], 0, -4)     = [-4, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > array.length).
     */
    public static int[] add(int[] array, int index, int element) {
        return (int[]) add(array, index, Integer.valueOf(element), Integer.TYPE);
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add([1L], 0, 2L)           = [2L, 1L]
     * ArrayUtils.add([2L, 6L], 2, 10L)      = [2L, 6L, 10L]
     * ArrayUtils.add([2L, 6L], 0, -4L)      = [-4L, 2L, 6L]
     * ArrayUtils.add([2L, 6L, 3L], 2, 1L)   = [2L, 6L, 1L, 3L]
     * </pre>
     *
     * @param array  the array to add the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > array.length).
     */
    public static long[] add(long[] array, int index, long element) {
        return (long[]) add(array, index, Long.valueOf(element), Long.TYPE);
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add([1.1f], 0, 2.2f)               = [2.2f, 1.1f]
     * ArrayUtils.add([2.3f, 6.4f], 2, 10.5f)        = [2.3f, 6.4f, 10.5f]
     * ArrayUtils.add([2.6f, 6.7f], 0, -4.8f)        = [-4.8f, 2.6f, 6.7f]
     * ArrayUtils.add([2.9f, 6.0f, 0.3f], 2, 1.0f)   = [2.9f, 6.0f, 1.0f, 0.3f]
     * </pre>
     *
     * @param array  the array to add the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > array.length).
     */
    public static float[] add(float[] array, int index, float element) {
        return (float[]) add(array, index, Float.valueOf(element), Float.TYPE);
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add([1.1], 0, 2.2)              = [2.2, 1.1]
     * ArrayUtils.add([2.3, 6.4], 2, 10.5)        = [2.3, 6.4, 10.5]
     * ArrayUtils.add([2.6, 6.7], 0, -4.8)        = [-4.8, 2.6, 6.7]
     * ArrayUtils.add([2.9, 6.0, 0.3], 2, 1.0)    = [2.9, 6.0, 1.0, 0.3]
     * </pre>
     *
     * @param array  the array to add the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > array.length).
     */
    public static double[] add(double[] array, int index, double element) {
        return (double[]) add(array, index, Double.valueOf(element), Double.TYPE);
    }

    /**
     * Underlying implementation of add(array, index, element) methods.
     * The last parameter is the class, which may not equal element.getClass
     * for primitives.
     *
     * @param array  the array to add the element to, may be <code>null</code>
     * @param index  the position of the new object
     * @param element  the object to add
     * @param clss the type of the element being added
     * @return A new array containing the existing elements and the new element
     */
    private static Object add(Object array, int index, Object element, Class<?> clss) {
        if (array == null) {
            if (index != 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
            }
            Object joinedArray = Array.newInstance(clss, 1);
            Array.set(joinedArray, 0, element);
            return joinedArray;
        }
        int length = Array.getLength(array);
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
        Object result = Array.newInstance(clss, length + 1);
        System.arraycopy(array, 0, result, 0, index);
        Array.set(result, index, element);
        if (index < length) {
            System.arraycopy(array, index, result, index + 1, length - index);
        }
        return result;
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove(["a"], 0)           = []
     * ArrayUtils.remove(["a", "b"], 0)      = ["b"]
     * ArrayUtils.remove(["a", "b"], 1)      = ["a"]
     * ArrayUtils.remove(["a", "b", "c"], 1) = ["a", "c"]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be <code>null</code>
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is <code>null</code>.
     * @since 1.0
     */
    @SuppressWarnings("unchecked") // remove() always creates an array of the same type as its input
    public static <T> T[] remove(T[] array, int index) {
        return (T[]) remove((Object) array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (substracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, "a")            = null
     * ArrayUtils.removeElement([], "a")              = []
     * ArrayUtils.removeElement(["a"], "b")           = ["a"]
     * ArrayUtils.removeElement(["a", "b"], "a")      = ["b"]
     * ArrayUtils.removeElement(["a", "b", "a"], "a") = ["b", "a"]
     * </pre>
     *
     * @param array  the array to remove the element from, may be <code>null</code>
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 1.0
     */
    public static <T> T[] removeElement(T[] array, Object element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove([true], 0)              = []
     * ArrayUtils.remove([true, false], 0)       = [false]
     * ArrayUtils.remove([true, false], 1)       = [true]
     * ArrayUtils.remove([true, true, false], 1) = [true, false]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be <code>null</code>
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is <code>null</code>.
     * @since 1.0
     */
    public static boolean[] remove(boolean[] array, int index) {
        return (boolean[]) remove((Object) array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (substracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, true)                = null
     * ArrayUtils.removeElement([], true)                  = []
     * ArrayUtils.removeElement([true], false)             = [true]
     * ArrayUtils.removeElement([true, false], false)      = [true]
     * ArrayUtils.removeElement([true, false, true], true) = [false, true]
     * </pre>
     *
     * @param array  the array to remove the element from, may be <code>null</code>
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 1.0
     */
    public static boolean[] removeElement(boolean[] array, boolean element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove([1], 0)          = []
     * ArrayUtils.remove([1, 0], 0)       = [0]
     * ArrayUtils.remove([1, 0], 1)       = [1]
     * ArrayUtils.remove([1, 0, 1], 1)    = [1, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be <code>null</code>
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is <code>null</code>.
     * @since 1.0
     */
    public static byte[] remove(byte[] array, int index) {
        return (byte[]) remove((Object) array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (substracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)        = null
     * ArrayUtils.removeElement([], 1)          = []
     * ArrayUtils.removeElement([1], 0)         = [1]
     * ArrayUtils.removeElement([1, 0], 0)      = [1]
     * ArrayUtils.removeElement([1, 0, 1], 1)   = [0, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be <code>null</code>
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 1.0
     */
    public static byte[] removeElement(byte[] array, byte element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove(['a'], 0)           = []
     * ArrayUtils.remove(['a', 'b'], 0)      = ['b']
     * ArrayUtils.remove(['a', 'b'], 1)      = ['a']
     * ArrayUtils.remove(['a', 'b', 'c'], 1) = ['a', 'c']
     * </pre>
     *
     * @param array  the array to remove the element from, may not be <code>null</code>
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is <code>null</code>.
     * @since 1.0
     */
    public static char[] remove(char[] array, int index) {
        return (char[]) remove((Object) array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (substracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, 'a')            = null
     * ArrayUtils.removeElement([], 'a')              = []
     * ArrayUtils.removeElement(['a'], 'b')           = ['a']
     * ArrayUtils.removeElement(['a', 'b'], 'a')      = ['b']
     * ArrayUtils.removeElement(['a', 'b', 'a'], 'a') = ['b', 'a']
     * </pre>
     *
     * @param array  the array to remove the element from, may be <code>null</code>
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 1.0
     */
    public static char[] removeElement(char[] array, char element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove([1.1], 0)           = []
     * ArrayUtils.remove([2.5, 6.0], 0)      = [6.0]
     * ArrayUtils.remove([2.5, 6.0], 1)      = [2.5]
     * ArrayUtils.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be <code>null</code>
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is <code>null</code>.
     * @since 1.0
     */
    public static double[] remove(double[] array, int index) {
        return (double[]) remove((Object) array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (substracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1.1)            = null
     * ArrayUtils.removeElement([], 1.1)              = []
     * ArrayUtils.removeElement([1.1], 1.2)           = [1.1]
     * ArrayUtils.removeElement([1.1, 2.3], 1.1)      = [2.3]
     * ArrayUtils.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be <code>null</code>
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 1.0
     */
    public static double[] removeElement(double[] array, double element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove([1.1], 0)           = []
     * ArrayUtils.remove([2.5, 6.0], 0)      = [6.0]
     * ArrayUtils.remove([2.5, 6.0], 1)      = [2.5]
     * ArrayUtils.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be <code>null</code>
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is <code>null</code>.
     * @since 1.0
     */
    public static float[] remove(float[] array, int index) {
        return (float[]) remove((Object) array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (substracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1.1)            = null
     * ArrayUtils.removeElement([], 1.1)              = []
     * ArrayUtils.removeElement([1.1], 1.2)           = [1.1]
     * ArrayUtils.removeElement([1.1, 2.3], 1.1)      = [2.3]
     * ArrayUtils.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be <code>null</code>
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 1.0
     */
    public static float[] removeElement(float[] array, float element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be <code>null</code>
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is <code>null</code>.
     * @since 1.0
     */
    public static int[] remove(int[] array, int index) {
        return (int[]) remove((Object) array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (substracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be <code>null</code>
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 1.0
     */
    public static int[] removeElement(int[] array, int element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be <code>null</code>
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is <code>null</code>.
     * @since 1.0
     */
    public static long[] remove(long[] array, int index) {
        return (long[]) remove((Object) array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (substracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be <code>null</code>
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 1.0
     */
    public static long[] removeElement(long[] array, long element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be <code>null</code>
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is <code>null</code>.
     * @since 1.0
     */
    public static short[] remove(short[] array, int index) {
        return (short[]) remove((Object) array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (substracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be <code>null</code>
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 1.0
     */
    public static short[] removeElement(short[] array, short element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (substracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is <code>null</code>, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * @param array  the array to remove the element from, may not be <code>null</code>
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is <code>null</code>.
     * @since 1.0
     */
    private static Object remove(Object array, int index) {
        int length = getLength(array);
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }

        return result;
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.removeAll(["a", "b", "c"], 0, 2) = ["b"]
     * ArrayUtils.removeAll(["a", "b", "c"], 1, 2) = ["a"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index < 0 || index >= array.length), or if the array is {@code null}.
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    // removeAll() always creates an array of the same type as its input
    public static <T> T[] removeAll(T[] array, int... indices) {
        return (T[]) removeAll((Object) array, clone(indices));
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index < 0 || index >= array.length), or if the array is {@code null}.
     * @since 1.0
     */
    public static byte[] removeAll(byte[] array, int... indices) {
        return (byte[]) removeAll((Object) array, clone(indices));
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index < 0 || index >= array.length), or if the array is {@code null}.
     * @since 1.0
     */
    public static short[] removeAll(short[] array, int... indices) {
        return (short[]) removeAll((Object) array, clone(indices));
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index < 0 || index >= array.length), or if the array is {@code null}.
     * @since 1.0
     */
    public static int[] removeAll(int[] array, int... indices) {
        return (int[]) removeAll((Object) array, clone(indices));
    }


    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index < 0 || index >= array.length), or if the array is {@code null}.
     * @since 1.0
     */
    public static char[] removeAll(char[] array, int... indices) {
        return (char[]) removeAll((Object) array, clone(indices));
    }


    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index < 0 || index >= array.length), or if the array is {@code null}.
     * @since 1.0
     */
    public static long[] removeAll(long[] array, int... indices) {
        return (long[]) removeAll((Object) array, clone(indices));
    }


    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index < 0 || index >= array.length), or if the array is {@code null}.
     * @since 1.0
     */
    public static float[] removeAll(float[] array, int... indices) {
        return (float[]) removeAll((Object) array, clone(indices));
    }


    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index < 0 || index >= array.length), or if the array is {@code null}.
     * @since 1.0
     */
    public static double[] removeAll(double[] array, int... indices) {
        return (double[]) removeAll((Object) array, clone(indices));
    }


    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.removeAll([true, false, true], 0, 2) = [false]
     * ArrayUtils.removeAll([true, false, true], 1, 2) = [true]
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index < 0 || index >= array.length), or if the array is {@code null}.
     * @since 1.0
     */
    public static boolean[] removeAll(boolean[] array, int... indices) {
        return (boolean[]) removeAll((Object) array, clone(indices));
    }

    /**
     * Removes multiple array elements specified by index.
     * @param array source
     * @param indices to remove, WILL BE SORTED--so only clones of user-owned arrays!
     * @return new array of same type minus elements specified by unique values of {@code indices}
     * @since 1.0
     */
    private static Object removeAll(Object array, int... indices) {
        int length = getLength(array);
        int diff = 0;

        if (isNotEmpty(indices)) {
            Arrays.sort(indices);

            int i = indices.length;
            int prevIndex = length;
            while (--i >= 0) {
                int index = indices[i];
                if (index < 0 || index >= length) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index >= prevIndex) {
                    continue;
                }
                diff++;
                prevIndex = index;
            }
        }
        Object result = Array.newInstance(array.getClass().getComponentType(), length - diff);
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; i--) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * Converts the given Collection into an array of Strings. The returned array does not contain <code>null</code>
     * entries. Note that {@link Arrays#sort(Object[])} will throw an {@link NullPointerException} if an array element
     * is <code>null</code>.
     *
     * @param collection
     *            The collection to convert
     * @return A new array of Strings.
     */
    static String[] toNoNullStringArray(Collection<?> collection) {
        if (collection == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return toNoNullStringArray(collection.toArray());
    }

    /**
     * Returns a new array of Strings without null elements. Internal method used to normalize exclude lists
     * (arrays and collections). Note that {@link Arrays#sort(Object[])} will throw an {@link NullPointerException}
     * if an array element is <code>null</code>.
     *
     * @param array
     *            The array to check
     * @return The given array or a new array without null.
     */
    static String[] toNoNullStringArray(Object[] array) {
        List<String> list = new ArrayList<String>(array.length);
        for (int i = 0; i < array.length; i++) {
            Object e = array[i];
            if (e != null) {
                list.add(e.toString());
            }
        }
        return (String[]) list.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

}
abstract class ToStringStyle implements Serializable {
    private static final long serialVersionUID = -1078081122889188948L;

    /**
     * The default toString style. Using the Using the <code>Person</code>
     * example from {@link ToStringBuilder}, the output would look like this:
     *
     * <pre>
     * Person@182f0db[name=John Doe,age=33,smoker=false]
     * </pre>
     */
    public static final ToStringStyle DEFAULT_STYLE = new DefaultToStringStyle();

    /**
     * The multi line toString style. Using the Using the <code>Person</code>
     * example from {@link ToStringBuilder}, the output would look like this:
     *
     * <pre>
     * Person@182f0db[
     *   name=John Doe
     *   age=33
     *   smoker=false
     * ]
     * </pre>
     */
    public static final ToStringStyle MULTI_LINE_STYLE = new MultiLineToStringStyle();

    /**
     * The no field names toString style. Using the Using the
     * <code>Person</code> example from {@link ToStringBuilder}, the output
     * would look like this:
     *
     * <pre>
     * Person@182f0db[John Doe,33,false]
     * </pre>
     */
    public static final ToStringStyle NO_FIELD_NAMES_STYLE = new NoFieldNameToStringStyle();

    /**
     * The short prefix toString style. Using the <code>Person</code> example
     * from {@link ToStringBuilder}, the output would look like this:
     *
     * <pre>
     * Person[name=John Doe,age=33,smoker=false]
     * </pre>
     *
     * @since 1.0
     */
    public static final ToStringStyle SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();

    /**
     * The simple toString style. Using the Using the <code>Person</code>
     * example from {@link ToStringBuilder}, the output would look like this:
     *
     * <pre>
     * John Doe,33,false
     * </pre>
     */
    public static final ToStringStyle SIMPLE_STYLE = new SimpleToStringStyle();

    /**
     * <p>
     * A registry of objects used by <code>reflectionToString</code> methods
     * to detect cyclical object references and avoid infinite loops.
     * </p>
     */
    private static final ThreadLocal<WeakHashMap<Object, Object>> REGISTRY =
        new ThreadLocal<WeakHashMap<Object,Object>>();

    /**
     * <p>
     * Returns the registry of objects being traversed by the <code>reflectionToString</code>
     * methods in the current thread.
     * </p>
     *
     * @return Set the registry of objects being traversed
     */
    static Map<Object, Object> getRegistry() {
        return REGISTRY.get();
    }

    /**
     * <p>
     * Returns <code>true</code> if the registry contains the given object.
     * Used by the reflection methods to avoid infinite loops.
     * </p>
     *
     * @param value
     *                  The object to lookup in the registry.
     * @return boolean <code>true</code> if the registry contains the given
     *             object.
     */
    static boolean isRegistered(Object value) {
        Map<Object, Object> m = getRegistry();
        return m != null && m.containsKey(value);
    }

    /**
     * <p>
     * Registers the given object. Used by the reflection methods to avoid
     * infinite loops.
     * </p>
     *
     * @param value
     *                  The object to register.
     */
    static void register(Object value) {
        if (value != null) {
            Map<Object, Object> m = getRegistry();
            if (m == null) {
                REGISTRY.set(new WeakHashMap<Object, Object>());
            }
            getRegistry().put(value, null);
        }
    }

    /**
     * <p>
     * Unregisters the given object.
     * </p>
     *
     * <p>
     * Used by the reflection methods to avoid infinite loops.
     * </p>
     *
     * @param value
     *                  The object to unregister.
     */
    static void unregister(Object value) {
        if (value != null) {
            Map<Object, Object> m = getRegistry();
            if (m != null) {
                m.remove(value);
                if (m.isEmpty()) {
                    REGISTRY.remove();
                }
            }
        }
    }

    /**
     * Whether to use the field names, the default is <code>true</code>.
     */
    private boolean useFieldNames = true;

    /**
     * Whether to use the class name, the default is <code>true</code>.
     */
    private boolean useClassName = true;

    /**
     * Whether to use short class names, the default is <code>false</code>.
     */
    private boolean useShortClassName = false;

    /**
     * Whether to use the identity hash code, the default is <code>true</code>.
     */
    private boolean useIdentityHashCode = true;

    /**
     * The content start <code>'['</code>.
     */
    private String contentStart = "[";

    /**
     * The content end <code>']'</code>.
     */
    private String contentEnd = "]";

    /**
     * The field name value separator <code>'='</code>.
     */
    private String fieldNameValueSeparator = "=";

    /**
     * Whether the field separator should be added before any other fields.
     */
    private boolean fieldSeparatorAtStart = false;

    /**
     * Whether the field separator should be added after any other fields.
     */
    private boolean fieldSeparatorAtEnd = false;

    /**
     * The field separator <code>','</code>.
     */
    private String fieldSeparator = ",";

    /**
     * The array start <code>'{'</code>.
     */
    private String arrayStart = "{";

    /**
     * The array separator <code>','</code>.
     */
    private String arraySeparator = ",";

    /**
     * The detail for array content.
     */
    private boolean arrayContentDetail = true;

    /**
     * The array end <code>'}'</code>.
     */
    private String arrayEnd = "}";

    /**
     * The value to use when fullDetail is <code>null</code>,
     * the default value is <code>true</code>.
     */
    private boolean defaultFullDetail = true;

    /**
     * The <code>null</code> text <code>'&lt;null&gt;'</code>.
     */
    private String nullText = "<null>";

    /**
     * The summary size text start <code>'<size'</code>.
     */
    private String sizeStartText = "<size=";

    /**
     * The summary size text start <code>'&gt;'</code>.
     */
    private String sizeEndText = ">";

    /**
     * The summary object text start <code>'&lt;'</code>.
     */
    private String summaryObjectStartText = "<";

    /**
     * The summary object text start <code>'&gt;'</code>.
     */
    private String summaryObjectEndText = ">";

    //----------------------------------------------------------------------------

    /**
     * <p>Constructor.</p>
     */
    protected ToStringStyle() {
        super();
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> the superclass toString.</p>
     * <p>NOTE: It assumes that the toString has been created from the same ToStringStyle. </p>
     *
     * <p>A <code>null</code> <code>superToString</code> is ignored.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param superToString  the <code>super.toString()</code>
     * @since 1.0
     */
    public void appendSuper(StringBuffer buffer, String superToString) {
        appendToString(buffer, superToString);
    }

    /**
     * <p>Append to the <code>toString</code> another toString.</p>
     * <p>NOTE: It assumes that the toString has been created from the same ToStringStyle. </p>
     *
     * <p>A <code>null</code> <code>toString</code> is ignored.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param toString  the additional <code>toString</code>
     * @since 1.0
     */
    public void appendToString(StringBuffer buffer, String toString) {
        if (toString != null) {
            int pos1 = toString.indexOf(contentStart) + contentStart.length();
            int pos2 = toString.lastIndexOf(contentEnd);
            if (pos1 != pos2 && pos1 >= 0 && pos2 >= 0) {
                String data = toString.substring(pos1, pos2);
                if (fieldSeparatorAtStart) {
                    removeLastFieldSeparator(buffer);
                }
                buffer.append(data);
                appendFieldSeparator(buffer);
            }
        }
    }

    /**
     * <p>Append to the <code>toString</code> the start of data indicator.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param object  the <code>Object</code> to build a <code>toString</code> for
     */
    public void appendStart(StringBuffer buffer, Object object) {
        if (object != null) {
            appendClassName(buffer, object);
            appendIdentityHashCode(buffer, object);
            appendContentStart(buffer);
            if (fieldSeparatorAtStart) {
                appendFieldSeparator(buffer);
            }
        }
    }

    /**
     * <p>Append to the <code>toString</code> the end of data indicator.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param object  the <code>Object</code> to build a
     *  <code>toString</code> for.
     */
    public void appendEnd(StringBuffer buffer, Object object) {
        if (this.fieldSeparatorAtEnd == false) {
            removeLastFieldSeparator(buffer);
        }
        appendContentEnd(buffer);
        unregister(object);
    }

    /**
     * <p>Remove the last field separator from the buffer.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @since 1.0
     */
    protected void removeLastFieldSeparator(StringBuffer buffer) {
        int len = buffer.length();
        int sepLen = fieldSeparator.length();
        if (len > 0 && sepLen > 0 && len >= sepLen) {
            boolean match = true;
            for (int i = 0; i < sepLen; i++) {
                if (buffer.charAt(len - 1 - i) != fieldSeparator.charAt(sepLen - 1 - i)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                buffer.setLength(len - sepLen);
            }
        }
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * value, printing the full <code>toString</code> of the
     * <code>Object</code> passed in.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param value  the value to add to the <code>toString</code>
     * @param fullDetail  <code>true</code> for detail, <code>false</code>
     *  for summary info, <code>null</code> for style decides
     */
    public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);

        if (value == null) {
            appendNullText(buffer, fieldName);

        } else {
            appendInternal(buffer, fieldName, value, isFullDetail(fullDetail));
        }

        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>,
     * correctly interpreting its type.</p>
     *
     * <p>This method performs the main lookup by Class type to correctly
     * route arrays, <code>Collections</code>, <code>Maps</code> and
     * <code>Objects</code> to the appropriate method.</p>
     *
     * <p>Either detail or summary views can be specified.</p>
     *
     * <p>If a cycle is detected, an object will be appended with the
     * <code>Object.toString()</code> format.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>,
     *  not <code>null</code>
     * @param detail  output detail or not
     */
    protected void appendInternal(StringBuffer buffer, String fieldName, Object value, boolean detail) {
        if (isRegistered(value)
            && !(value instanceof Number || value instanceof Boolean || value instanceof Character)) {
           appendCyclicObject(buffer, fieldName, value);
           return;
        }

        register(value);

        try {
            if (value instanceof Collection<?>) {
                if (detail) {
                    appendDetail(buffer, fieldName, (Collection<?>) value);
                } else {
                    appendSummarySize(buffer, fieldName, ((Collection<?>) value).size());
                }

            } else if (value instanceof Map<?, ?>) {
                if (detail) {
                    appendDetail(buffer, fieldName, (Map<?, ?>) value);
                } else {
                    appendSummarySize(buffer, fieldName, ((Map<?, ?>) value).size());
                }

            } else if (value instanceof long[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (long[]) value);
                } else {
                    appendSummary(buffer, fieldName, (long[]) value);
                }

            } else if (value instanceof int[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (int[]) value);
                } else {
                    appendSummary(buffer, fieldName, (int[]) value);
                }

            } else if (value instanceof short[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (short[]) value);
                } else {
                    appendSummary(buffer, fieldName, (short[]) value);
                }

            } else if (value instanceof byte[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (byte[]) value);
                } else {
                    appendSummary(buffer, fieldName, (byte[]) value);
                }

            } else if (value instanceof char[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (char[]) value);
                } else {
                    appendSummary(buffer, fieldName, (char[]) value);
                }

            } else if (value instanceof double[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (double[]) value);
                } else {
                    appendSummary(buffer, fieldName, (double[]) value);
                }

            } else if (value instanceof float[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (float[]) value);
                } else {
                    appendSummary(buffer, fieldName, (float[]) value);
                }

            } else if (value instanceof boolean[]) {
                if (detail) {
                    appendDetail(buffer, fieldName, (boolean[]) value);
                } else {
                    appendSummary(buffer, fieldName, (boolean[]) value);
                }

            } else if (value.getClass().isArray()) {
                if (detail) {
                    appendDetail(buffer, fieldName, (Object[]) value);
                } else {
                    appendSummary(buffer, fieldName, (Object[]) value);
                }

            } else {
                    if (detail) {
                        appendDetail(buffer, fieldName, value);
                    } else {
                        appendSummary(buffer, fieldName, value);
                    }
            }
        } finally {
            unregister(value);
        }
    }

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * value that has been detected to participate in a cycle. This
     * implementation will print the standard string value of the value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>,
     *  not <code>null</code>
     *
     * @since 1.0
     */
    protected void appendCyclicObject(StringBuffer buffer, String fieldName, Object value) {
       ObjectUtils.identityToString(buffer, value);
    }

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * value, printing the full detail of the <code>Object</code>.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
        buffer.append(value);
    }

    /**
     * <p>Append to the <code>toString</code> a <code>Collection</code>.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param coll  the <code>Collection</code> to add to the
     *  <code>toString</code>, not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, Collection<?> coll) {
        buffer.append(coll);
    }

    /**
     * <p>Append to the <code>toString</code> a <code>Map<code>.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param map  the <code>Map</code> to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, Map<?, ?> map) {
        buffer.append(map);
    }

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * value, printing a summary of the <code>Object</code>.</P>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendSummary(StringBuffer buffer, String fieldName, Object value) {
        buffer.append(summaryObjectStartText);
        buffer.append(getShortClassName(value.getClass()));
        buffer.append(summaryObjectEndText);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>long</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param value  the value to add to the <code>toString</code>
     */
    public void append(StringBuffer buffer, String fieldName, long value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> a <code>long</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, long value) {
        buffer.append(value);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> an <code>int</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param value  the value to add to the <code>toString</code>
     */
    public void append(StringBuffer buffer, String fieldName, int value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> an <code>int</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, int value) {
        buffer.append(value);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>short</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param value  the value to add to the <code>toString</code>
     */
    public void append(StringBuffer buffer, String fieldName, short value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> a <code>short</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, short value) {
        buffer.append(value);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>byte</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param value  the value to add to the <code>toString</code>
     */
    public void append(StringBuffer buffer, String fieldName, byte value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> a <code>byte</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, byte value) {
        buffer.append(value);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>char</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param value  the value to add to the <code>toString</code>
     */
    public void append(StringBuffer buffer, String fieldName, char value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> a <code>char</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, char value) {
        buffer.append(value);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>double</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param value  the value to add to the <code>toString</code>
     */
    public void append(StringBuffer buffer, String fieldName, double value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> a <code>double</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, double value) {
        buffer.append(value);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>float</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param value  the value to add to the <code>toString</code>
     */
    public void append(StringBuffer buffer, String fieldName, float value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> a <code>float</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, float value) {
        buffer.append(value);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>boolean</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param value  the value to add to the <code>toString</code>
     */
    public void append(StringBuffer buffer, String fieldName, boolean value) {
        appendFieldStart(buffer, fieldName);
        appendDetail(buffer, fieldName, value);
        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> a <code>boolean</code>
     * value.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param value  the value to add to the <code>toString</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, boolean value) {
        buffer.append(value);
    }

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param array  the array to add to the toString
     * @param fullDetail  <code>true</code> for detail, <code>false</code>
     *  for summary info, <code>null</code> for style decides
     */
    public void append(StringBuffer buffer, String fieldName, Object[] array, Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);

        if (array == null) {
            appendNullText(buffer, fieldName);

        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);

        } else {
            appendSummary(buffer, fieldName, array);
        }

        appendFieldEnd(buffer, fieldName);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> the detail of an
     * <code>Object</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, Object[] array) {
        buffer.append(arrayStart);
        for (int i = 0; i < array.length; i++) {
            Object item = array[i];
            if (i > 0) {
                buffer.append(arraySeparator);
            }
            if (item == null) {
                appendNullText(buffer, fieldName);

            } else {
                appendInternal(buffer, fieldName, item, arrayContentDetail);
            }
        }
        buffer.append(arrayEnd);
    }

    /**
     * <p>Append to the <code>toString</code> the detail of an array type.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     * @since 1.0
     */
    protected void reflectionAppendArrayDetail(StringBuffer buffer, String fieldName, Object array) {
        buffer.append(arrayStart);
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            Object item = Array.get(array, i);
            if (i > 0) {
                buffer.append(arraySeparator);
            }
            if (item == null) {
                appendNullText(buffer, fieldName);

            } else {
                appendInternal(buffer, fieldName, item, arrayContentDetail);
            }
        }
        buffer.append(arrayEnd);
    }

    /**
     * <p>Append to the <code>toString</code> a summary of an
     * <code>Object</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendSummary(StringBuffer buffer, String fieldName, Object[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>long</code>
     * array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param array  the array to add to the <code>toString</code>
     * @param fullDetail  <code>true</code> for detail, <code>false</code>
     *  for summary info, <code>null</code> for style decides
     */
    public void append(StringBuffer buffer, String fieldName, long[] array, Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);

        if (array == null) {
            appendNullText(buffer, fieldName);

        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);

        } else {
            appendSummary(buffer, fieldName, array);
        }

        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> the detail of a
     * <code>long</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, long[] array) {
        buffer.append(arrayStart);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(arraySeparator);
            }
            appendDetail(buffer, fieldName, array[i]);
        }
        buffer.append(arrayEnd);
    }

    /**
     * <p>Append to the <code>toString</code> a summary of a
     * <code>long</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendSummary(StringBuffer buffer, String fieldName, long[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> an <code>int</code>
     * array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param array  the array to add to the <code>toString</code>
     * @param fullDetail  <code>true</code> for detail, <code>false</code>
     *  for summary info, <code>null</code> for style decides
     */
    public void append(StringBuffer buffer, String fieldName, int[] array, Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);

        if (array == null) {
            appendNullText(buffer, fieldName);

        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);

        } else {
            appendSummary(buffer, fieldName, array);
        }

        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> the detail of an
     * <code>int</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, int[] array) {
        buffer.append(arrayStart);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(arraySeparator);
            }
            appendDetail(buffer, fieldName, array[i]);
        }
        buffer.append(arrayEnd);
    }

    /**
     * <p>Append to the <code>toString</code> a summary of an
     * <code>int</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendSummary(StringBuffer buffer, String fieldName, int[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>short</code>
     * array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param array  the array to add to the <code>toString</code>
     * @param fullDetail  <code>true</code> for detail, <code>false</code>
     *  for summary info, <code>null</code> for style decides
     */
    public void append(StringBuffer buffer, String fieldName, short[] array, Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);

        if (array == null) {
            appendNullText(buffer, fieldName);

        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);

        } else {
            appendSummary(buffer, fieldName, array);
        }

        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> the detail of a
     * <code>short</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, short[] array) {
        buffer.append(arrayStart);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(arraySeparator);
            }
            appendDetail(buffer, fieldName, array[i]);
        }
        buffer.append(arrayEnd);
    }

    /**
     * <p>Append to the <code>toString</code> a summary of a
     * <code>short</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendSummary(StringBuffer buffer, String fieldName, short[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>byte</code>
     * array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param array  the array to add to the <code>toString</code>
     * @param fullDetail  <code>true</code> for detail, <code>false</code>
     *  for summary info, <code>null</code> for style decides
     */
    public void append(StringBuffer buffer, String fieldName, byte[] array, Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);

        if (array == null) {
            appendNullText(buffer, fieldName);

        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);

        } else {
            appendSummary(buffer, fieldName, array);
        }

        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> the detail of a
     * <code>byte</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, byte[] array) {
        buffer.append(arrayStart);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(arraySeparator);
            }
            appendDetail(buffer, fieldName, array[i]);
        }
        buffer.append(arrayEnd);
    }

    /**
     * <p>Append to the <code>toString</code> a summary of a
     * <code>byte</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendSummary(StringBuffer buffer, String fieldName, byte[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>char</code>
     * array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param array  the array to add to the <code>toString</code>
     * @param fullDetail  <code>true</code> for detail, <code>false</code>
     *  for summary info, <code>null</code> for style decides
     */
    public void append(StringBuffer buffer, String fieldName, char[] array, Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);

        if (array == null) {
            appendNullText(buffer, fieldName);

        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);

        } else {
            appendSummary(buffer, fieldName, array);
        }

        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> the detail of a
     * <code>char</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, char[] array) {
        buffer.append(arrayStart);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(arraySeparator);
            }
            appendDetail(buffer, fieldName, array[i]);
        }
        buffer.append(arrayEnd);
    }

    /**
     * <p>Append to the <code>toString</code> a summary of a
     * <code>char</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendSummary(StringBuffer buffer, String fieldName, char[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>double</code>
     * array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param array  the array to add to the toString
     * @param fullDetail  <code>true</code> for detail, <code>false</code>
     *  for summary info, <code>null</code> for style decides
     */
    public void append(StringBuffer buffer, String fieldName, double[] array, Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);

        if (array == null) {
            appendNullText(buffer, fieldName);

        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);

        } else {
            appendSummary(buffer, fieldName, array);
        }

        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> the detail of a
     * <code>double</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, double[] array) {
        buffer.append(arrayStart);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(arraySeparator);
            }
            appendDetail(buffer, fieldName, array[i]);
        }
        buffer.append(arrayEnd);
    }

    /**
     * <p>Append to the <code>toString</code> a summary of a
     * <code>double</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendSummary(StringBuffer buffer, String fieldName, double[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>float</code>
     * array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param array  the array to add to the toString
     * @param fullDetail  <code>true</code> for detail, <code>false</code>
     *  for summary info, <code>null</code> for style decides
     */
    public void append(StringBuffer buffer, String fieldName, float[] array, Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);

        if (array == null) {
            appendNullText(buffer, fieldName);

        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);

        } else {
            appendSummary(buffer, fieldName, array);
        }

        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> the detail of a
     * <code>float</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, float[] array) {
        buffer.append(arrayStart);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(arraySeparator);
            }
            appendDetail(buffer, fieldName, array[i]);
        }
        buffer.append(arrayEnd);
    }

    /**
     * <p>Append to the <code>toString</code> a summary of a
     * <code>float</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendSummary(StringBuffer buffer, String fieldName, float[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>boolean</code>
     * array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     * @param array  the array to add to the toString
     * @param fullDetail  <code>true</code> for detail, <code>false</code>
     *  for summary info, <code>null</code> for style decides
     */
    public void append(StringBuffer buffer, String fieldName, boolean[] array, Boolean fullDetail) {
        appendFieldStart(buffer, fieldName);

        if (array == null) {
            appendNullText(buffer, fieldName);

        } else if (isFullDetail(fullDetail)) {
            appendDetail(buffer, fieldName, array);

        } else {
            appendSummary(buffer, fieldName, array);
        }

        appendFieldEnd(buffer, fieldName);
    }

    /**
     * <p>Append to the <code>toString</code> the detail of a
     * <code>boolean</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendDetail(StringBuffer buffer, String fieldName, boolean[] array) {
        buffer.append(arrayStart);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(arraySeparator);
            }
            appendDetail(buffer, fieldName, array[i]);
        }
        buffer.append(arrayEnd);
    }

    /**
     * <p>Append to the <code>toString</code> a summary of a
     * <code>boolean</code> array.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param array  the array to add to the <code>toString</code>,
     *  not <code>null</code>
     */
    protected void appendSummary(StringBuffer buffer, String fieldName, boolean[] array) {
        appendSummarySize(buffer, fieldName, array.length);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> the class name.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param object  the <code>Object</code> whose name to output
     */
    protected void appendClassName(StringBuffer buffer, Object object) {
        if (useClassName && object != null) {
            register(object);
            if (useShortClassName) {
                buffer.append(getShortClassName(object.getClass()));
            } else {
                buffer.append(object.getClass().getName());
            }
        }
    }

    /**
     * <p>Append the {@link System#identityHashCode(java.lang.Object)}.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param object  the <code>Object</code> whose id to output
     */
    protected void appendIdentityHashCode(StringBuffer buffer, Object object) {
        if (this.isUseIdentityHashCode() && object!=null) {
            register(object);
            buffer.append('@');
            buffer.append(Integer.toHexString(System.identityHashCode(object)));
        }
    }

    /**
     * <p>Append to the <code>toString</code> the content start.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     */
    protected void appendContentStart(StringBuffer buffer) {
        buffer.append(contentStart);
    }

    /**
     * <p>Append to the <code>toString</code> the content end.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     */
    protected void appendContentEnd(StringBuffer buffer) {
        buffer.append(contentEnd);
    }

    /**
     * <p>Append to the <code>toString</code> an indicator for <code>null</code>.</p>
     *
     * <p>The default indicator is <code>'&lt;null&gt;'</code>.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     */
    protected void appendNullText(StringBuffer buffer, String fieldName) {
        buffer.append(nullText);
    }

    /**
     * <p>Append to the <code>toString</code> the field separator.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     */
    protected void appendFieldSeparator(StringBuffer buffer) {
        buffer.append(fieldSeparator);
    }

    /**
     * <p>Append to the <code>toString</code> the field start.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name
     */
    protected void appendFieldStart(StringBuffer buffer, String fieldName) {
        if (useFieldNames && fieldName != null) {
            buffer.append(fieldName);
            buffer.append(fieldNameValueSeparator);
        }
    }

    /**
     * <p>Append to the <code>toString<code> the field end.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     */
    protected void appendFieldEnd(StringBuffer buffer, String fieldName) {
        appendFieldSeparator(buffer);
    }

    /**
     * <p>Append to the <code>toString</code> a size summary.</p>
     *
     * <p>The size summary is used to summarize the contents of
     * <code>Collections</code>, <code>Maps</code> and arrays.</p>
     *
     * <p>The output consists of a prefix, the passed in size
     * and a suffix.</p>
     *
     * <p>The default format is <code>'&lt;size=n&gt;'<code>.</p>
     *
     * @param buffer  the <code>StringBuffer</code> to populate
     * @param fieldName  the field name, typically not used as already appended
     * @param size  the size to append
     */
    protected void appendSummarySize(StringBuffer buffer, String fieldName, int size) {
        buffer.append(sizeStartText);
        buffer.append(size);
        buffer.append(sizeEndText);
    }

    /**
     * <p>Is this field to be output in full detail.</p>
     *
     * <p>This method converts a detail request into a detail level.
     * The calling code may request full detail (<code>true</code>),
     * but a subclass might ignore that and always return
     * <code>false</code>. The calling code may pass in
     * <code>null</code> indicating that it doesn't care about
     * the detail level. In this case the default detail level is
     * used.</p>
     *
     * @param fullDetailRequest  the detail level requested
     * @return whether full detail is to be shown
     */
    protected boolean isFullDetail(Boolean fullDetailRequest) {
        if (fullDetailRequest == null) {
            return defaultFullDetail;
        }
        return fullDetailRequest.booleanValue();
    }

    /**
     * <p>Gets the short class name for a class.</p>
     *
     * <p>The short class name is the classname excluding
     * the package name.</p>
     *
     * @param cls  the <code>Class</code> to get the short name of
     * @return the short name
     */
    protected String getShortClassName(Class<?> cls) {
        return ClassUtils.getShortClassName(cls);
    }

    // Setters and getters for the customizable parts of the style
    // These methods are not expected to be overridden, except to make public
    // (They are not public so that immutable subclasses can be written)
    //---------------------------------------------------------------------

    /**
     * <p>Gets whether to use the class name.</p>
     *
     * @return the current useClassName flag
     */
    protected boolean isUseClassName() {
        return useClassName;
    }

    /**
     * <p>Sets whether to use the class name.</p>
     *
     * @param useClassName  the new useClassName flag
     */
    protected void setUseClassName(boolean useClassName) {
        this.useClassName = useClassName;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets whether to output short or long class names.</p>
     *
     * @return the current useShortClassName flag
     * @since 1.0
     */
    protected boolean isUseShortClassName() {
        return useShortClassName;
    }

    /**
     * <p>Sets whether to output short or long class names.</p>
     *
     * @param useShortClassName  the new useShortClassName flag
     * @since 1.0
     */
    protected void setUseShortClassName(boolean useShortClassName) {
        this.useShortClassName = useShortClassName;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets whether to use the identity hash code.</p>
     *
     * @return the current useIdentityHashCode flag
     */
    protected boolean isUseIdentityHashCode() {
        return useIdentityHashCode;
    }

    /**
     * <p>Sets whether to use the identity hash code.</p>
     *
     * @param useIdentityHashCode  the new useIdentityHashCode flag
     */
    protected void setUseIdentityHashCode(boolean useIdentityHashCode) {
        this.useIdentityHashCode = useIdentityHashCode;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets whether to use the field names passed in.</p>
     *
     * @return the current useFieldNames flag
     */
    protected boolean isUseFieldNames() {
        return useFieldNames;
    }

    /**
     * <p>Sets whether to use the field names passed in.</p>
     *
     * @param useFieldNames  the new useFieldNames flag
     */
    protected void setUseFieldNames(boolean useFieldNames) {
        this.useFieldNames = useFieldNames;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets whether to use full detail when the caller doesn't
     * specify.</p>
     *
     * @return the current defaultFullDetail flag
     */
    protected boolean isDefaultFullDetail() {
        return defaultFullDetail;
    }

    /**
     * <p>Sets whether to use full detail when the caller doesn't
     * specify.</p>
     *
     * @param defaultFullDetail  the new defaultFullDetail flag
     */
    protected void setDefaultFullDetail(boolean defaultFullDetail) {
        this.defaultFullDetail = defaultFullDetail;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets whether to output array content detail.</p>
     *
     * @return the current array content detail setting
     */
    protected boolean isArrayContentDetail() {
        return arrayContentDetail;
    }

    /**
     * <p>Sets whether to output array content detail.</p>
     *
     * @param arrayContentDetail  the new arrayContentDetail flag
     */
    protected void setArrayContentDetail(boolean arrayContentDetail) {
        this.arrayContentDetail = arrayContentDetail;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the array start text.</p>
     *
     * @return the current array start text
     */
    protected String getArrayStart() {
        return arrayStart;
    }

    /**
     * <p>Sets the array start text.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param arrayStart  the new array start text
     */
    protected void setArrayStart(String arrayStart) {
        if (arrayStart == null) {
            arrayStart = "";
        }
        this.arrayStart = arrayStart;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the array end text.</p>
     *
     * @return the current array end text
     */
    protected String getArrayEnd() {
        return arrayEnd;
    }

    /**
     * <p>Sets the array end text.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param arrayEnd  the new array end text
     */
    protected void setArrayEnd(String arrayEnd) {
        if (arrayEnd == null) {
            arrayEnd = "";
        }
        this.arrayEnd = arrayEnd;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the array separator text.</p>
     *
     * @return the current array separator text
     */
    protected String getArraySeparator() {
        return arraySeparator;
    }

    /**
     * <p>Sets the array separator text.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param arraySeparator  the new array separator text
     */
    protected void setArraySeparator(String arraySeparator) {
        if (arraySeparator == null) {
            arraySeparator = "";
        }
        this.arraySeparator = arraySeparator;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the content start text.</p>
     *
     * @return the current content start text
     */
    protected String getContentStart() {
        return contentStart;
    }

    /**
     * <p>Sets the content start text.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param contentStart  the new content start text
     */
    protected void setContentStart(String contentStart) {
        if (contentStart == null) {
            contentStart = "";
        }
        this.contentStart = contentStart;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the content end text.</p>
     *
     * @return the current content end text
     */
    protected String getContentEnd() {
        return contentEnd;
    }

    /**
     * <p>Sets the content end text.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param contentEnd  the new content end text
     */
    protected void setContentEnd(String contentEnd) {
        if (contentEnd == null) {
            contentEnd = "";
        }
        this.contentEnd = contentEnd;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the field name value separator text.</p>
     *
     * @return the current field name value separator text
     */
    protected String getFieldNameValueSeparator() {
        return fieldNameValueSeparator;
    }

    /**
     * <p>Sets the field name value separator text.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param fieldNameValueSeparator  the new field name value separator text
     */
    protected void setFieldNameValueSeparator(String fieldNameValueSeparator) {
        if (fieldNameValueSeparator == null) {
            fieldNameValueSeparator = "";
        }
        this.fieldNameValueSeparator = fieldNameValueSeparator;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the field separator text.</p>
     *
     * @return the current field separator text
     */
    protected String getFieldSeparator() {
        return fieldSeparator;
    }

    /**
     * <p>Sets the field separator text.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param fieldSeparator  the new field separator text
     */
    protected void setFieldSeparator(String fieldSeparator) {
        if (fieldSeparator == null) {
            fieldSeparator = "";
        }
        this.fieldSeparator = fieldSeparator;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets whether the field separator should be added at the start
     * of each buffer.</p>
     *
     * @return the fieldSeparatorAtStart flag
     * @since 1.0
     */
    protected boolean isFieldSeparatorAtStart() {
        return fieldSeparatorAtStart;
    }

    /**
     * <p>Sets whether the field separator should be added at the start
     * of each buffer.</p>
     *
     * @param fieldSeparatorAtStart  the fieldSeparatorAtStart flag
     * @since 1.0
     */
    protected void setFieldSeparatorAtStart(boolean fieldSeparatorAtStart) {
        this.fieldSeparatorAtStart = fieldSeparatorAtStart;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets whether the field separator should be added at the end
     * of each buffer.</p>
     *
     * @return fieldSeparatorAtEnd flag
     * @since 1.0
     */
    protected boolean isFieldSeparatorAtEnd() {
        return fieldSeparatorAtEnd;
    }

    /**
     * <p>Sets whether the field separator should be added at the end
     * of each buffer.</p>
     *
     * @param fieldSeparatorAtEnd  the fieldSeparatorAtEnd flag
     * @since 1.0
     */
    protected void setFieldSeparatorAtEnd(boolean fieldSeparatorAtEnd) {
        this.fieldSeparatorAtEnd = fieldSeparatorAtEnd;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the text to output when <code>null</code> found.</p>
     *
     * @return the current text to output when null found
     */
    protected String getNullText() {
        return nullText;
    }

    /**
     * <p>Sets the text to output when <code>null</code> found.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param nullText  the new text to output when null found
     */
    protected void setNullText(String nullText) {
        if (nullText == null) {
            nullText = "";
        }
        this.nullText = nullText;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the start text to output when a <code>Collection</code>,
     * <code>Map</code> or array size is output.</p>
     *
     * <p>This is output before the size value.</p>
     *
     * @return the current start of size text
     */
    protected String getSizeStartText() {
        return sizeStartText;
    }

    /**
     * <p>Sets the start text to output when a <code>Collection</code>,
     * <code>Map</code> or array size is output.</p>
     *
     * <p>This is output before the size value.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param sizeStartText  the new start of size text
     */
    protected void setSizeStartText(String sizeStartText) {
        if (sizeStartText == null) {
            sizeStartText = "";
        }
        this.sizeStartText = sizeStartText;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the end text to output when a <code>Collection</code>,
     * <code>Map</code> or array size is output.</p>
     *
     * <p>This is output after the size value.</p>
     *
     * @return the current end of size text
     */
    protected String getSizeEndText() {
        return sizeEndText;
    }

    /**
     * <p>Sets the end text to output when a <code>Collection</code>,
     * <code>Map</code> or array size is output.</p>
     *
     * <p>This is output after the size value.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param sizeEndText  the new end of size text
     */
    protected void setSizeEndText(String sizeEndText) {
        if (sizeEndText == null) {
            sizeEndText = "";
        }
        this.sizeEndText = sizeEndText;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the start text to output when an <code>Object</code> is
     * output in summary mode.</p>
     *
     * <p>This is output before the size value.</p>
     *
     * @return the current start of summary text
     */
    protected String getSummaryObjectStartText() {
        return summaryObjectStartText;
    }

    /**
     * <p>Sets the start text to output when an <code>Object</code> is
     * output in summary mode.</p>
     *
     * <p>This is output before the size value.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param summaryObjectStartText  the new start of summary text
     */
    protected void setSummaryObjectStartText(String summaryObjectStartText) {
        if (summaryObjectStartText == null) {
            summaryObjectStartText = "";
        }
        this.summaryObjectStartText = summaryObjectStartText;
    }

    //---------------------------------------------------------------------

    /**
     * <p>Gets the end text to output when an <code>Object</code> is
     * output in summary mode.</p>
     *
     * <p>This is output after the size value.</p>
     *
     * @return the current end of summary text
     */
    protected String getSummaryObjectEndText() {
        return summaryObjectEndText;
    }

    /**
     * <p>Sets the end text to output when an <code>Object</code> is
     * output in summary mode.</p>
     *
     * <p>This is output after the size value.</p>
     *
     * <p><code>null</code> is accepted, but will be converted to
     * an empty String.</p>
     *
     * @param summaryObjectEndText  the new end of summary text
     */
    protected void setSummaryObjectEndText(String summaryObjectEndText) {
        if (summaryObjectEndText == null) {
            summaryObjectEndText = "";
        }
        this.summaryObjectEndText = summaryObjectEndText;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Default <code>ToStringStyle</code>.</p>
     *
     * <p>This is an inner class rather than using
     * <code>StandardToStringStyle</code> to ensure its immutability.</p>
     */
    private static final class DefaultToStringStyle extends ToStringStyle {

        /**
         * Required for serialization support.
         *
         * @see java.io.Serializable
         */
        private static final long serialVersionUID = 1L;

        /**
         * <p>Constructor.</p>
         *
         * <p>Use the static constant rather than instantiating.</p>
         */
        DefaultToStringStyle() {
            super();
        }

        /**
         * <p>Ensure <code>Singleton</code> after serialization.</p>
         *
         * @return the singleton
         */
        private Object readResolve() {
            return ToStringStyle.DEFAULT_STYLE;
        }

    }

    //----------------------------------------------------------------------------

    /**
     * <p><code>ToStringStyle</code> that does not print out
     * the field names.</p>
     *
     * <p>This is an inner class rather than using
     * <code>StandardToStringStyle</code> to ensure its immutability.
     */
    private static final class NoFieldNameToStringStyle extends ToStringStyle {

        private static final long serialVersionUID = 1L;

        /**
         * <p>Constructor.</p>
         *
         * <p>Use the static constant rather than instantiating.</p>
         */
        NoFieldNameToStringStyle() {
            super();
            this.setUseFieldNames(false);
        }

        /**
         * <p>Ensure <code>Singleton</code> after serialization.</p>
         *
         * @return the singleton
         */
        private Object readResolve() {
            return ToStringStyle.NO_FIELD_NAMES_STYLE;
        }

    }

    //----------------------------------------------------------------------------

    /**
     * <p><code>ToStringStyle</code> that prints out the short
     * class name and no identity hashcode.</p>
     *
     * <p>This is an inner class rather than using
     * <code>StandardToStringStyle</code> to ensure its immutability.</p>
     */
    private static final class ShortPrefixToStringStyle extends ToStringStyle {

        private static final long serialVersionUID = 1L;

        /**
         * <p>Constructor.</p>
         *
         * <p>Use the static constant rather than instantiating.</p>
         */
        ShortPrefixToStringStyle() {
            super();
            this.setUseShortClassName(true);
            this.setUseIdentityHashCode(false);
        }

        /**
         * <p>Ensure <code>Singleton</ode> after serialization.</p>
         * @return the singleton
         */
        private Object readResolve() {
            return ToStringStyle.SHORT_PREFIX_STYLE;
        }

    }

    /**
     * <p><code>ToStringStyle</code> that does not print out the
     * classname, identity hashcode, content start or field name.</p>
     *
     * <p>This is an inner class rather than using
     * <code>StandardToStringStyle</code> to ensure its immutability.</p>
     */
    private static final class SimpleToStringStyle extends ToStringStyle {

        private static final long serialVersionUID = 1L;

        /**
         * <p>Constructor.</p>
         *
         * <p>Use the static constant rather than instantiating.</p>
         */
        SimpleToStringStyle() {
            super();
            this.setUseClassName(false);
            this.setUseIdentityHashCode(false);
            this.setUseFieldNames(false);
            this.setContentStart("");
            this.setContentEnd("");
        }

        /**
         * <p>Ensure <code>Singleton</ode> after serialization.</p>
         * @return the singleton
         */
        private Object readResolve() {
            return ToStringStyle.SIMPLE_STYLE;
        }

    }

    //----------------------------------------------------------------------------

    /**
     * <p><code>ToStringStyle</code> that outputs on multiple lines.</p>
     *
     * <p>This is an inner class rather than using
     * <code>StandardToStringStyle</code> to ensure its immutability.</p>
     */
    private static final class MultiLineToStringStyle extends ToStringStyle {

        private static final long serialVersionUID = 1L;

        /**
         * <p>Constructor.</p>
         *
         * <p>Use the static constant rather than instantiating.</p>
         */
        MultiLineToStringStyle() {
            super();
            this.setContentStart("[");
            this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
            this.setFieldSeparatorAtStart(true);
            this.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
        }

        /**
         * <p>Ensure <code>Singleton</code> after serialization.</p>
         *
         * @return the singleton
         */
        private Object readResolve() {
            return ToStringStyle.MULTI_LINE_STYLE;
        }

    }

}
class HashCodeBuilder {
    /**
     * <p>
     * A registry of objects used by reflection methods to detect cyclical object references and avoid infinite loops.
     * </p>
     *
     * @since 1.0
     */
    private static final ThreadLocal<Set<IDKey>> REGISTRY = new ThreadLocal<Set<IDKey>>();

    /**
     * <p>
     * Returns the registry of objects being traversed by the reflection methods in the current thread.
     * </p>
     *
     * @return Set the registry of objects being traversed
     * @since 1.0
     */
    static Set<IDKey> getRegistry() {
        return REGISTRY.get();
    }
    /**
     * <p>
     * Returns <code>true</code> if the registry contains the given object. Used by the reflection methods to avoid
     * infinite loops.
     * </p>
     *
     * @param value
     *            The object to lookup in the registry.
     * @return boolean <code>true</code> if the registry contains the given object.
     * @since 1.0
     */
    static boolean isRegistered(Object value) {
        Set<IDKey> registry = getRegistry();
        return registry != null && registry.contains(new IDKey(value));
    }

    /**
     * <p>
     * Appends the fields and values defined by the given object of the given <code>Class</code>.
     * </p>
     *
     * @param object
     *            the object to append details of
     * @param clazz
     *            the class to append details of
     * @param builder
     *            the builder to append to
     * @param useTransients
     *            whether to use transient fields
     * @param excludeFields
     *            Collection of String field names to exclude from use in calculation of hash code
     */
    private static void reflectionAppend(Object object, Class<?> clazz, HashCodeBuilder builder, boolean useTransients,
            String[] excludeFields) {
        if (isRegistered(object)) {
            return;
        }
        try {
            register(object);
            Field[] fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (Field field : fields) {
                if (!ArrayUtils.contains(excludeFields, field.getName())
                    && (field.getName().indexOf('$') == -1)
                    && (useTransients || !Modifier.isTransient(field.getModifiers()))
                    && (!Modifier.isStatic(field.getModifiers()))) {
                    try {
                        Object fieldValue = field.get(object);
                        builder.append(fieldValue);
                    } catch (IllegalAccessException e) {
                        // this can't happen. Would get a Security exception instead
                        // throw a runtime exception in case the impossible happens.
                        throw new InternalError("Unexpected IllegalAccessException");
                    }
                }
            }
        } finally {
            unregister(object);
        }
    }

    /**
     * <p>
     * This method uses reflection to build a valid hash code.
     * </p>
     *
     * <p>
     * It uses <code>AccessibleObject.setAccessible</code> to gain access to private fields. This means that it will
     * throw a security exception if run under a security manager, if the permissions are not set up correctly. It is
     * also not as efficient as testing explicitly.
     * </p>
     *
     * <p>
     * Transient members will be not be used, as they are likely derived fields, and not part of the value of the
     * <code>Object</code>.
     * </p>
     *
     * <p>
     * Static fields will not be tested. Superclass fields will be included.
     * </p>
     *
     * <p>
     * Two randomly chosen, non-zero, odd numbers must be passed in. Ideally these should be different for each class,
     * however this is not vital. Prime numbers are preferred, especially for the multiplier.
     * </p>
     *
     * @param initialNonZeroOddNumber
     *            a non-zero, odd number used as the initial value
     * @param multiplierNonZeroOddNumber
     *            a non-zero, odd number used as the multiplier
     * @param object
     *            the Object to create a <code>hashCode</code> for
     * @return int hash code
     * @throws IllegalArgumentException
     *             if the Object is <code>null</code>
     * @throws IllegalArgumentException
     *             if the number is zero or even
     */
    public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object) {
        return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, false, null);
    }

    /**
     * <p>
     * This method uses reflection to build a valid hash code.
     * </p>
     *
     * <p>
     * It uses <code>AccessibleObject.setAccessible</code> to gain access to private fields. This means that it will
     * throw a security exception if run under a security manager, if the permissions are not set up correctly. It is
     * also not as efficient as testing explicitly.
     * </p>
     *
     * <p>
     * If the TestTransients parameter is set to <code>true</code>, transient members will be tested, otherwise they
     * are ignored, as they are likely derived fields, and not part of the value of the <code>Object</code>.
     * </p>
     *
     * <p>
     * Static fields will not be tested. Superclass fields will be included.
     * </p>
     *
     * <p>
     * Two randomly chosen, non-zero, odd numbers must be passed in. Ideally these should be different for each class,
     * however this is not vital. Prime numbers are preferred, especially for the multiplier.
     * </p>
     *
     * @param initialNonZeroOddNumber
     *            a non-zero, odd number used as the initial value
     * @param multiplierNonZeroOddNumber
     *            a non-zero, odd number used as the multiplier
     * @param object
     *            the Object to create a <code>hashCode</code> for
     * @param testTransients
     *            whether to include transient fields
     * @return int hash code
     * @throws IllegalArgumentException
     *             if the Object is <code>null</code>
     * @throws IllegalArgumentException
     *             if the number is zero or even
     */
    public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object,
            boolean testTransients) {
        return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, testTransients, null);
    }

    /**
     * <p>
     * This method uses reflection to build a valid hash code.
     * </p>
     *
     * <p>
     * It uses <code>AccessibleObject.setAccessible</code> to gain access to private fields. This means that it will
     * throw a security exception if run under a security manager, if the permissions are not set up correctly. It is
     * also not as efficient as testing explicitly.
     * </p>
     *
     * <p>
     * If the TestTransients parameter is set to <code>true</code>, transient members will be tested, otherwise they
     * are ignored, as they are likely derived fields, and not part of the value of the <code>Object</code>.
     * </p>
     *
     * <p>
     * Static fields will not be included. Superclass fields will be included up to and including the specified
     * superclass. A null superclass is treated as java.lang.Object.
     * </p>
     *
     * <p>
     * Two randomly chosen, non-zero, odd numbers must be passed in. Ideally these should be different for each class,
     * however this is not vital. Prime numbers are preferred, especially for the multiplier.
     * </p>
     * @param <T>
     *
     * @param initialNonZeroOddNumber
     *            a non-zero, odd number used as the initial value
     * @param multiplierNonZeroOddNumber
     *            a non-zero, odd number used as the multiplier
     * @param object
     *            the Object to create a <code>hashCode</code> for
     * @param testTransients
     *            whether to include transient fields
     * @param reflectUpToClass
     *            the superclass to reflect up to (inclusive), may be <code>null</code>
     * @param excludeFields
     *            array of field names to exclude from use in calculation of hash code
     * @return int hash code
     * @throws IllegalArgumentException
     *             if the Object is <code>null</code>
     * @throws IllegalArgumentException
     *             if the number is zero or even
     * @since 1.0
     */
    public static <T> int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, T object,
            boolean testTransients, Class<? super T> reflectUpToClass, String... excludeFields) {

        if (object == null) {
            throw new IllegalArgumentException("The object to build a hash code for must not be null");
        }
        HashCodeBuilder builder = new HashCodeBuilder(initialNonZeroOddNumber, multiplierNonZeroOddNumber);
        Class<?> clazz = object.getClass();
        reflectionAppend(object, clazz, builder, testTransients, excludeFields);
        while (clazz.getSuperclass() != null && clazz != reflectUpToClass) {
            clazz = clazz.getSuperclass();
            reflectionAppend(object, clazz, builder, testTransients, excludeFields);
        }
        return builder.toHashCode();
    }

    /**
     * <p>
     * This method uses reflection to build a valid hash code.
     * </p>
     *
     * <p>
     * This constructor uses two hard coded choices for the constants needed to build a hash code.
     * </p>
     *
     * <p>
     * It uses <code>AccessibleObject.setAccessible</code> to gain access to private fields. This means that it will
     * throw a security exception if run under a security manager, if the permissions are not set up correctly. It is
     * also not as efficient as testing explicitly.
     * </p>
     *
     * <P>
     * If the TestTransients parameter is set to <code>true</code>, transient members will be tested, otherwise they
     * are ignored, as they are likely derived fields, and not part of the value of the <code>Object</code>.
     * </p>
     *
     * <p>
     * Static fields will not be tested. Superclass fields will be included.
     * </p>
     *
     * @param object
     *            the Object to create a <code>hashCode</code> for
     * @param testTransients
     *            whether to include transient fields
     * @return int hash code
     * @throws IllegalArgumentException
     *             if the object is <code>null</code>
     */
    public static int reflectionHashCode(Object object, boolean testTransients) {
        return reflectionHashCode(17, 37, object, testTransients, null);
    }

    /**
     * <p>
     * This method uses reflection to build a valid hash code.
     * </p>
     *
     * <p>
     * This constructor uses two hard coded choices for the constants needed to build a hash code.
     * </p>
     *
     * <p>
     * It uses <code>AccessibleObject.setAccessible</code> to gain access to private fields. This means that it will
     * throw a security exception if run under a security manager, if the permissions are not set up correctly. It is
     * also not as efficient as testing explicitly.
     * </p>
     *
     * <p>
     * Transient members will be not be used, as they are likely derived fields, and not part of the value of the
     * <code>Object</code>.
     * </p>
     *
     * <p>
     * Static fields will not be tested. Superclass fields will be included.
     * </p>
     *
     * @param object
     *            the Object to create a <code>hashCode</code> for
     * @param excludeFields
     *            Collection of String field names to exclude from use in calculation of hash code
     * @return int hash code
     * @throws IllegalArgumentException
     *             if the object is <code>null</code>
     */
    public static int reflectionHashCode(Object object, Collection<String> excludeFields) {
        return reflectionHashCode(object, ArrayUtils.toNoNullStringArray(excludeFields));
    }

    // -------------------------------------------------------------------------

    /**
     * <p>
     * This method uses reflection to build a valid hash code.
     * </p>
     *
     * <p>
     * This constructor uses two hard coded choices for the constants needed to build a hash code.
     * </p>
     *
     * <p>
     * It uses <code>AccessibleObject.setAccessible</code> to gain access to private fields. This means that it will
     * throw a security exception if run under a security manager, if the permissions are not set up correctly. It is
     * also not as efficient as testing explicitly.
     * </p>
     *
     * <p>
     * Transient members will be not be used, as they are likely derived fields, and not part of the value of the
     * <code>Object</code>.
     * </p>
     *
     * <p>
     * Static fields will not be tested. Superclass fields will be included.
     * </p>
     *
     * @param object
     *            the Object to create a <code>hashCode</code> for
     * @param excludeFields
     *            array of field names to exclude from use in calculation of hash code
     * @return int hash code
     * @throws IllegalArgumentException
     *             if the object is <code>null</code>
     */
    public static int reflectionHashCode(Object object, String... excludeFields) {
        return reflectionHashCode(17, 37, object, false, null, excludeFields);
    }

    /**
     * <p>
     * Registers the given object. Used by the reflection methods to avoid infinite loops.
     * </p>
     *
     * @param value
     *            The object to register.
     */
    static void register(Object value) {
        synchronized (HashCodeBuilder.class) {
            if (getRegistry() == null) {
                REGISTRY.set(new HashSet<IDKey>());
            }
        }
        getRegistry().add(new IDKey(value));
    }

    /**
     * <p>
     * Unregisters the given object.
     * </p>
     *
     * <p>
     * Used by the reflection methods to avoid infinite loops.
     *
     * @param value
     *            The object to unregister.
     * @since 1.0
     */
    static void unregister(Object value) {
        Set<IDKey> registry = getRegistry();
        if (registry != null) {
            registry.remove(new IDKey(value));
            synchronized (HashCodeBuilder.class) {
                //read again
                registry = getRegistry();
                if (registry != null && registry.isEmpty()) {
                    REGISTRY.remove();
                }
            }
        }
    }

    /**
     * Constant to use in building the hashCode.
     */
    private final int iConstant;

    /**
     * Running total of the hashCode.
     */
    private int iTotal = 0;

    /**
     * <p>
     * Uses two hard coded choices for the constants needed to build a <code>hashCode</code>.
     * </p>
     */
    public HashCodeBuilder() {
        iConstant = 37;
        iTotal = 17;
    }

    /**
     * <p>
     * Two randomly chosen, non-zero, odd numbers must be passed in. Ideally these should be different for each class,
     * however this is not vital.
     * </p>
     *
     * <p>
     * Prime numbers are preferred, especially for the multiplier.
     * </p>
     *
     * @param initialNonZeroOddNumber
     *            a non-zero, odd number used as the initial value
     * @param multiplierNonZeroOddNumber
     *            a non-zero, odd number used as the multiplier
     * @throws IllegalArgumentException
     *             if the number is zero or even
     */
    public HashCodeBuilder(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber) {
        if (initialNonZeroOddNumber == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires a non zero initial value");
        }
        if (initialNonZeroOddNumber % 2 == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires an odd initial value");
        }
        if (multiplierNonZeroOddNumber == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires a non zero multiplier");
        }
        if (multiplierNonZeroOddNumber % 2 == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires an odd multiplier");
        }
        iConstant = multiplierNonZeroOddNumber;
        iTotal = initialNonZeroOddNumber;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>boolean</code>.
     * </p>
     * <p>
     * This adds <code>iConstant * 1</code> to the <code>hashCode</code> and not a <code>1231</code> or
     * <code>1237</code> as done in java.lang.Boolean. This is in accordance with the <quote>Effective Java</quote>
     * design.
     * </p>
     *
     * @param value
     *            the boolean to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(boolean value) {
        iTotal = iTotal * iConstant + (value ? 0 : 1);
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>boolean</code> array.
     * </p>
     *
     * @param array
     *            the array to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(boolean[] array) {
        if (array == null) {
            iTotal = iTotal * iConstant;
        } else {
            for (int i = 0; i < array.length; i++) {
                append(array[i]);
            }
        }
        return this;
    }

    // -------------------------------------------------------------------------

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>byte</code>.
     * </p>
     *
     * @param value
     *            the byte to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(byte value) {
        iTotal = iTotal * iConstant + value;
        return this;
    }

    // -------------------------------------------------------------------------

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>byte</code> array.
     * </p>
     *
     * @param array
     *            the array to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(byte[] array) {
        if (array == null) {
            iTotal = iTotal * iConstant;
        } else {
            for (byte element : array) {
                append(element);
            }
        }
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>char</code>.
     * </p>
     *
     * @param value
     *            the char to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(char value) {
        iTotal = iTotal * iConstant + value;
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>char</code> array.
     * </p>
     *
     * @param array
     *            the array to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(char[] array) {
        if (array == null) {
            iTotal = iTotal * iConstant;
        } else {
            for (char element : array) {
                append(element);
            }
        }
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>double</code>.
     * </p>
     *
     * @param value
     *            the double to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(double value) {
        return append(Double.doubleToLongBits(value));
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>double</code> array.
     * </p>
     *
     * @param array
     *            the array to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(double[] array) {
        if (array == null) {
            iTotal = iTotal * iConstant;
        } else {
            for (double element : array) {
                append(element);
            }
        }
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>float</code>.
     * </p>
     *
     * @param value
     *            the float to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(float value) {
        iTotal = iTotal * iConstant + Float.floatToIntBits(value);
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>float</code> array.
     * </p>
     *
     * @param array
     *            the array to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(float[] array) {
        if (array == null) {
            iTotal = iTotal * iConstant;
        } else {
            for (float element : array) {
                append(element);
            }
        }
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for an <code>int</code>.
     * </p>
     *
     * @param value
     *            the int to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(int value) {
        iTotal = iTotal * iConstant + value;
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for an <code>int</code> array.
     * </p>
     *
     * @param array
     *            the array to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(int[] array) {
        if (array == null) {
            iTotal = iTotal * iConstant;
        } else {
            for (int element : array) {
                append(element);
            }
        }
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>long</code>.
     * </p>
     * <p>
     *
     * @param value
     *            the long to add to the <code>hashCode</code>
     * @return this
     */
    // NOTE: This method uses >> and not >>> as Effective Java and
    //       Long.hashCode do. Ideally we should switch to >>> at
    //       some stage. There are backwards compat issues, so
    //       that will have to wait for the time being. cf LANG-342.
    public HashCodeBuilder append(long value) {
        iTotal = iTotal * iConstant + ((int) (value ^ (value >> 32)));
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>long</code> array.
     * </p>
     *
     * @param array
     *            the array to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(long[] array) {
        if (array == null) {
            iTotal = iTotal * iConstant;
        } else {
            for (long element : array) {
                append(element);
            }
        }
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for an <code>Object</code>.
     * </p>
     *
     * @param object
     *            the Object to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(Object object) {
        if (object == null) {
            iTotal = iTotal * iConstant;

        } else {
                // 'Switch' on type of array, to dispatch to the correct handler
                // This handles multi dimensional arrays
                if (object instanceof long[]) {
                    append((long[]) object);
                } else if (object instanceof int[]) {
                    append((int[]) object);
                } else if (object instanceof short[]) {
                    append((short[]) object);
                } else if (object instanceof char[]) {
                    append((char[]) object);
                } else if (object instanceof byte[]) {
                    append((byte[]) object);
                } else if (object instanceof double[]) {
                    append((double[]) object);
                } else if (object instanceof float[]) {
                    append((float[]) object);
                } else if (object instanceof boolean[]) {
                    append((boolean[]) object);
                } else if (object instanceof Object[]) {
                    // Not an array of primitives
                    append((Object[]) object);
                } else {
                    iTotal = iTotal * iConstant + object.hashCode();
                }
        }
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for an <code>Object</code> array.
     * </p>
     *
     * @param array
     *            the array to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(Object[] array) {
        if (array == null) {
            iTotal = iTotal * iConstant;
        } else {
            for (Object element : array) {
                append(element);
            }
        }
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>short</code>.
     * </p>
     *
     * @param value
     *            the short to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(short value) {
        iTotal = iTotal * iConstant + value;
        return this;
    }

    /**
     * <p>
     * Append a <code>hashCode</code> for a <code>short</code> array.
     * </p>
     *
     * @param array
     *            the array to add to the <code>hashCode</code>
     * @return this
     */
    public HashCodeBuilder append(short[] array) {
        if (array == null) {
            iTotal = iTotal * iConstant;
        } else {
            for (short element : array) {
                append(element);
            }
        }
        return this;
    }

    /**
     * <p>
     * Adds the result of super.hashCode() to this builder.
     * </p>
     *
     * @param superHashCode
     *            the result of calling <code>super.hashCode()</code>
     * @return this HashCodeBuilder, used to chain calls.
     * @since 1.0
     */
    public HashCodeBuilder appendSuper(int superHashCode) {
        iTotal = iTotal * iConstant + superHashCode;
        return this;
    }

    /**
     * Returns the computed <code>hashCode</code>.
     *
     * @return <code>hashCode</code> based on the fields appended
     *
     * @since 1.0
     */
    public Integer build() {
        return Integer.valueOf(toHashCode());
    }

    /**
     * <p>
     * Return the computed <code>hashCode</code>.
     * </p>
     *
     * @return <code>hashCode</code> based on the fields appended
     */
    public int toHashCode() {
        return iTotal;
    }

}
class EqualsBuilder {

    /**
     * If the fields tested are equals.
     * The default value is <code>true</code>.
     */
    private boolean isEquals = true;

    /**
     * <p>Constructor for EqualsBuilder.</p>
     *
     * <p>Starts off assuming that equals is <code>true</code>.</p>
     * @see Object#equals(Object)
     */
    public EqualsBuilder() {
        // do nothing for now.
    }

    //-------------------------------------------------------------------------

    /**
     * <p>This method uses reflection to determine if the two <code>Object</code>s
     * are equal.</p>
     *
     * <p>It uses <code>AccessibleObject.setAccessible</code> to gain access to private
     * fields. This means that it will throw a security exception if run under
     * a security manager, if the permissions are not set up correctly. It is also
     * not as efficient as testing explicitly.</p>
     *
     * <p>Transient members will be not be tested, as they are likely derived
     * fields, and not part of the value of the Object.</p>
     *
     * <p>Static fields will not be tested. Superclass fields will be included.</p>
     *
     * @param lhs  <code>this</code> object
     * @param rhs  the other object
     * @param excludeFields  Collection of String field names to exclude from testing
     * @return <code>true</code> if the two Objects have tested equals.
     */
    public static boolean reflectionEquals(Object lhs, Object rhs, Collection<String> excludeFields) {
        return reflectionEquals(lhs, rhs, ArrayUtils.toNoNullStringArray(excludeFields));
    }

    /**
     * <p>This method uses reflection to determine if the two <code>Object</code>s
     * are equal.</p>
     *
     * <p>It uses <code>AccessibleObject.setAccessible</code> to gain access to private
     * fields. This means that it will throw a security exception if run under
     * a security manager, if the permissions are not set up correctly. It is also
     * not as efficient as testing explicitly.</p>
     *
     * <p>Transient members will be not be tested, as they are likely derived
     * fields, and not part of the value of the Object.</p>
     *
     * <p>Static fields will not be tested. Superclass fields will be included.</p>
     *
     * @param lhs  <code>this</code> object
     * @param rhs  the other object
     * @param excludeFields  array of field names to exclude from testing
     * @return <code>true</code> if the two Objects have tested equals.
     */
    public static boolean reflectionEquals(Object lhs, Object rhs, String... excludeFields) {
        return reflectionEquals(lhs, rhs, false, null, excludeFields);
    }

    /**
     * <p>This method uses reflection to determine if the two <code>Object</code>s
     * are equal.</p>
     *
     * <p>It uses <code>AccessibleObject.setAccessible</code> to gain access to private
     * fields. This means that it will throw a security exception if run under
     * a security manager, if the permissions are not set up correctly. It is also
     * not as efficient as testing explicitly.</p>
     *
     * <p>If the TestTransients parameter is set to <code>true</code>, transient
     * members will be tested, otherwise they are ignored, as they are likely
     * derived fields, and not part of the value of the <code>Object</code>.</p>
     *
     * <p>Static fields will not be tested. Superclass fields will be included.</p>
     *
     * @param lhs  <code>this</code> object
     * @param rhs  the other object
     * @param testTransients  whether to include transient fields
     * @return <code>true</code> if the two Objects have tested equals.
     */
    public static boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients) {
        return reflectionEquals(lhs, rhs, testTransients, null);
    }

    /**
     * <p>This method uses reflection to determine if the two <code>Object</code>s
     * are equal.</p>
     *
     * <p>It uses <code>AccessibleObject.setAccessible</code> to gain access to private
     * fields. This means that it will throw a security exception if run under
     * a security manager, if the permissions are not set up correctly. It is also
     * not as efficient as testing explicitly.</p>
     *
     * <p>If the testTransients parameter is set to <code>true</code>, transient
     * members will be tested, otherwise they are ignored, as they are likely
     * derived fields, and not part of the value of the <code>Object</code>.</p>
     *
     * <p>Static fields will not be included. Superclass fields will be appended
     * up to and including the specified superclass. A null superclass is treated
     * as java.lang.Object.</p>
     *
     * @param lhs  <code>this</code> object
     * @param rhs  the other object
     * @param testTransients  whether to include transient fields
     * @param reflectUpToClass  the superclass to reflect up to (inclusive),
     *  may be <code>null</code>
     * @param excludeFields  array of field names to exclude from testing
     * @return <code>true</code> if the two Objects have tested equals.
     * @since 1.0
     */
    public static boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients, Class<?> reflectUpToClass,
            String... excludeFields) {
        if (lhs == rhs) {
            return true;
        }
        if (lhs == null || rhs == null) {
            return false;
        }
        // Find the leaf class since there may be transients in the leaf
        // class or in classes between the leaf and root.
        // If we are not testing transients or a subclass has no ivars,
        // then a subclass can test equals to a superclass.
        Class<?> lhsClass = lhs.getClass();
        Class<?> rhsClass = rhs.getClass();
        Class<?> testClass;
        if (lhsClass.isInstance(rhs)) {
            testClass = lhsClass;
            if (!rhsClass.isInstance(lhs)) {
                // rhsClass is a subclass of lhsClass
                testClass = rhsClass;
            }
        } else if (rhsClass.isInstance(lhs)) {
            testClass = rhsClass;
            if (!lhsClass.isInstance(rhs)) {
                // lhsClass is a subclass of rhsClass
                testClass = lhsClass;
            }
        } else {
            // The two classes are not related.
            return false;
        }
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        try {
            reflectionAppend(lhs, rhs, testClass, equalsBuilder, testTransients, excludeFields);
            while (testClass.getSuperclass() != null && testClass != reflectUpToClass) {
                testClass = testClass.getSuperclass();
                reflectionAppend(lhs, rhs, testClass, equalsBuilder, testTransients, excludeFields);
            }
        } catch (IllegalArgumentException e) {
            // In this case, we tried to test a subclass vs. a superclass and
            // the subclass has ivars or the ivars are transient and
            // we are testing transients.
            // If a subclass has ivars that we are trying to test them, we get an
            // exception and we know that the objects are not equal.
            return false;
        }
        return equalsBuilder.isEquals();
    }

    /**
     * <p>Appends the fields and values defined by the given object of the
     * given Class.</p>
     *
     * @param lhs  the left hand object
     * @param rhs  the right hand object
     * @param clazz  the class to append details of
     * @param builder  the builder to append to
     * @param useTransients  whether to test transient fields
     * @param excludeFields  array of field names to exclude from testing
     */
    private static void reflectionAppend(
        Object lhs,
        Object rhs,
        Class<?> clazz,
        EqualsBuilder builder,
        boolean useTransients,
        String[] excludeFields) {
        Field[] fields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (int i = 0; i < fields.length && builder.isEquals; i++) {
            Field f = fields[i];
            if (!ArrayUtils.contains(excludeFields, f.getName())
                && (f.getName().indexOf('$') == -1)
                && (useTransients || !Modifier.isTransient(f.getModifiers()))
                && (!Modifier.isStatic(f.getModifiers()))) {
                try {
                    builder.append(f.get(lhs), f.get(rhs));
                } catch (IllegalAccessException e) {
                    //this can't happen. Would get a Security exception instead
                    //throw a runtime exception in case the impossible happens.
                    throw new InternalError("Unexpected IllegalAccessException");
                }
            }
        }
    }

    //-------------------------------------------------------------------------

    /**
     * <p>Adds the result of <code>super.equals()</code> to this builder.</p>
     *
     * @param superEquals  the result of calling <code>super.equals()</code>
     * @return EqualsBuilder - used to chain calls.
     * @since 1.0
     */
    public EqualsBuilder appendSuper(boolean superEquals) {
        if (isEquals == false) {
            return this;
        }
        isEquals = superEquals;
        return this;
    }

    //-------------------------------------------------------------------------

    /**
     * <p>Test if two <code>Object</code>s are equal using their
     * <code>equals</code> method.</p>
     *
     * @param lhs  the left hand object
     * @param rhs  the right hand object
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(Object lhs, Object rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            this.setEquals(false);
            return this;
        }
        Class<?> lhsClass = lhs.getClass();
        if (!lhsClass.isArray()) {
            if (lhs instanceof java.math.BigDecimal) {
                isEquals = (((java.math.BigDecimal)lhs).compareTo((BigDecimal) rhs) == 0);
            } else {
                // The simple case, not an array, just test the element
                isEquals = lhs.equals(rhs);
            }
        } else if (lhs.getClass() != rhs.getClass()) {
            // Here when we compare different dimensions, for example: a boolean[][] to a boolean[]
            this.setEquals(false);
        }
        // 'Switch' on type of array, to dispatch to the correct handler
        // This handles multi dimensional arrays of the same depth
        else if (lhs instanceof long[]) {
            append((long[]) lhs, (long[]) rhs);
        } else if (lhs instanceof int[]) {
            append((int[]) lhs, (int[]) rhs);
        } else if (lhs instanceof short[]) {
            append((short[]) lhs, (short[]) rhs);
        } else if (lhs instanceof char[]) {
            append((char[]) lhs, (char[]) rhs);
        } else if (lhs instanceof byte[]) {
            append((byte[]) lhs, (byte[]) rhs);
        } else if (lhs instanceof double[]) {
            append((double[]) lhs, (double[]) rhs);
        } else if (lhs instanceof float[]) {
            append((float[]) lhs, (float[]) rhs);
        } else if (lhs instanceof boolean[]) {
            append((boolean[]) lhs, (boolean[]) rhs);
        } else {
            // Not an array of primitives
            append((Object[]) lhs, (Object[]) rhs);
        }
        return this;
    }

    /**
     * <p>
     * Test if two <code>long</code> s are equal.
     * </p>
     *
     * @param lhs
     *                  the left hand <code>long</code>
     * @param rhs
     *                  the right hand <code>long</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(long lhs, long rhs) {
        if (isEquals == false) {
            return this;
        }
        isEquals = (lhs == rhs);
        return this;
    }

    /**
     * <p>Test if two <code>int</code>s are equal.</p>
     *
     * @param lhs  the left hand <code>int</code>
     * @param rhs  the right hand <code>int</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(int lhs, int rhs) {
        if (isEquals == false) {
            return this;
        }
        isEquals = (lhs == rhs);
        return this;
    }

    /**
     * <p>Test if two <code>short</code>s are equal.</p>
     *
     * @param lhs  the left hand <code>short</code>
     * @param rhs  the right hand <code>short</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(short lhs, short rhs) {
        if (isEquals == false) {
            return this;
        }
        isEquals = (lhs == rhs);
        return this;
    }

    /**
     * <p>Test if two <code>char</code>s are equal.</p>
     *
     * @param lhs  the left hand <code>char</code>
     * @param rhs  the right hand <code>char</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(char lhs, char rhs) {
        if (isEquals == false) {
            return this;
        }
        isEquals = (lhs == rhs);
        return this;
    }

    /**
     * <p>Test if two <code>byte</code>s are equal.</p>
     *
     * @param lhs  the left hand <code>byte</code>
     * @param rhs  the right hand <code>byte</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(byte lhs, byte rhs) {
        if (isEquals == false) {
            return this;
        }
        isEquals = (lhs == rhs);
        return this;
    }

    /**
     * <p>Test if two <code>double</code>s are equal by testing that the
     * pattern of bits returned by <code>doubleToLong</code> are equal.</p>
     *
     * <p>This handles NaNs, Infinities, and <code>-0.0</code>.</p>
     *
     * <p>It is compatible with the hash code generated by
     * <code>HashCodeBuilder</code>.</p>
     *
     * @param lhs  the left hand <code>double</code>
     * @param rhs  the right hand <code>double</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(double lhs, double rhs) {
        if (isEquals == false) {
            return this;
        }
        return append(Double.doubleToLongBits(lhs), Double.doubleToLongBits(rhs));
    }

    /**
     * <p>Test if two <code>float</code>s are equal byt testing that the
     * pattern of bits returned by doubleToLong are equal.</p>
     *
     * <p>This handles NaNs, Infinities, and <code>-0.0</code>.</p>
     *
     * <p>It is compatible with the hash code generated by
     * <code>HashCodeBuilder</code>.</p>
     *
     * @param lhs  the left hand <code>float</code>
     * @param rhs  the right hand <code>float</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(float lhs, float rhs) {
        if (isEquals == false) {
            return this;
        }
        return append(Float.floatToIntBits(lhs), Float.floatToIntBits(rhs));
    }

    /**
     * <p>Test if two <code>booleans</code>s are equal.</p>
     *
     * @param lhs  the left hand <code>boolean</code>
     * @param rhs  the right hand <code>boolean</code>
     * @return EqualsBuilder - used to chain calls.
      */
    public EqualsBuilder append(boolean lhs, boolean rhs) {
        if (isEquals == false) {
            return this;
        }
        isEquals = (lhs == rhs);
        return this;
    }

    /**
     * <p>Performs a deep comparison of two <code>Object</code> arrays.</p>
     *
     * <p>This also will be called for the top level of
     * multi-dimensional, ragged, and multi-typed arrays.</p>
     *
     * @param lhs  the left hand <code>Object[]</code>
     * @param rhs  the right hand <code>Object[]</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(Object[] lhs, Object[] rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            this.setEquals(false);
            return this;
        }
        if (lhs.length != rhs.length) {
            this.setEquals(false);
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    /**
     * <p>Deep comparison of array of <code>long</code>. Length and all
     * values are compared.</p>
     *
     * <p>The method {@link #append(long, long)} is used.</p>
     *
     * @param lhs  the left hand <code>long[]</code>
     * @param rhs  the right hand <code>long[]</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(long[] lhs, long[] rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            this.setEquals(false);
            return this;
        }
        if (lhs.length != rhs.length) {
            this.setEquals(false);
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    /**
     * <p>Deep comparison of array of <code>int</code>. Length and all
     * values are compared.</p>
     *
     * <p>The method {@link #append(int, int)} is used.</p>
     *
     * @param lhs  the left hand <code>int[]</code>
     * @param rhs  the right hand <code>int[]</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(int[] lhs, int[] rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            this.setEquals(false);
            return this;
        }
        if (lhs.length != rhs.length) {
            this.setEquals(false);
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    /**
     * <p>Deep comparison of array of <code>short</code>. Length and all
     * values are compared.</p>
     *
     * <p>The method {@link #append(short, short)} is used.</p>
     *
     * @param lhs  the left hand <code>short[]</code>
     * @param rhs  the right hand <code>short[]</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(short[] lhs, short[] rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            this.setEquals(false);
            return this;
        }
        if (lhs.length != rhs.length) {
            this.setEquals(false);
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    /**
     * <p>Deep comparison of array of <code>char</code>. Length and all
     * values are compared.</p>
     *
     * <p>The method {@link #append(char, char)} is used.</p>
     *
     * @param lhs  the left hand <code>char[]</code>
     * @param rhs  the right hand <code>char[]</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(char[] lhs, char[] rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            this.setEquals(false);
            return this;
        }
        if (lhs.length != rhs.length) {
            this.setEquals(false);
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    /**
     * <p>Deep comparison of array of <code>byte</code>. Length and all
     * values are compared.</p>
     *
     * <p>The method {@link #append(byte, byte)} is used.</p>
     *
     * @param lhs  the left hand <code>byte[]</code>
     * @param rhs  the right hand <code>byte[]</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(byte[] lhs, byte[] rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            this.setEquals(false);
            return this;
        }
        if (lhs.length != rhs.length) {
            this.setEquals(false);
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    /**
     * <p>Deep comparison of array of <code>double</code>. Length and all
     * values are compared.</p>
     *
     * <p>The method {@link #append(double, double)} is used.</p>
     *
     * @param lhs  the left hand <code>double[]</code>
     * @param rhs  the right hand <code>double[]</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(double[] lhs, double[] rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            this.setEquals(false);
            return this;
        }
        if (lhs.length != rhs.length) {
            this.setEquals(false);
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    /**
     * <p>Deep comparison of array of <code>float</code>. Length and all
     * values are compared.</p>
     *
     * <p>The method {@link #append(float, float)} is used.</p>
     *
     * @param lhs  the left hand <code>float[]</code>
     * @param rhs  the right hand <code>float[]</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(float[] lhs, float[] rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            this.setEquals(false);
            return this;
        }
        if (lhs.length != rhs.length) {
            this.setEquals(false);
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    /**
     * <p>Deep comparison of array of <code>boolean</code>. Length and all
     * values are compared.</p>
     *
     * <p>The method {@link #append(boolean, boolean)} is used.</p>
     *
     * @param lhs  the left hand <code>boolean[]</code>
     * @param rhs  the right hand <code>boolean[]</code>
     * @return EqualsBuilder - used to chain calls.
     */
    public EqualsBuilder append(boolean[] lhs, boolean[] rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            this.setEquals(false);
            return this;
        }
        if (lhs.length != rhs.length) {
            this.setEquals(false);
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    /**
     * <p>Returns <code>true</code> if the fields that have been checked
     * are all equal.</p>
     *
     * @return boolean
     */
    public boolean isEquals() {
        return this.isEquals;
    }

    /**
     * <p>Returns <code>true</code> if the fields that have been checked
     * are all equal.</p>
     *
     * @return <code>true</code> if all of the fields that have been checked
     *         are equal, <code>false</code> otherwise.
     *
     * @since 1.0
     */
    public Boolean build() {
        return Boolean.valueOf(isEquals());
    }

    /**
     * Sets the <code>isEquals</code> value.
     *
     * @param isEquals The value to set.
     * @since 1.0
     */
    protected void setEquals(boolean isEquals) {
        this.isEquals = isEquals;
    }
}
final class IDKey {
    private final Object value;
    private final int id;

    /**
     * Constructor for IDKey
     * @param _value The value
     */
    public IDKey(Object _value) {
        // This is the Object hashcode
        id = System.identityHashCode(_value);
        // There have been some cases (LANG-459) that return the
        // same identity hash code for different objects.  So
        // the value is also added to disambiguate these cases.
        value = _value;
    }

    /**
     * returns hashcode - i.e. the system identity hashcode.
     * @return the hashcode
     */
    @Override
    public int hashCode() {
       return id;
    }

    /**
     * checks if instances are equal
     * @param other The other object to compare to
     * @return if the instances are for the same object
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof IDKey)) {
            return false;
        }
        IDKey idKey = (IDKey) other;
        if (id != idKey.id) {
            return false;
        }
        // Note that identity equals is used.
        return value == idKey.value;
     }
}