package org.jan.common.utils.lang;

import java.util.Random;

/**
 * <p><code>RandomUtils</code> is a wrapper that supports all possible
 * {@link java.util.Random} methods via the {@link java.lang.Math#random()}
 * method and its system-wide <code>Random</code> object.
 *
 * @author Jan.Wong
 * @since 1.0
 */
public class RandomUtils {

    /**
     * An instance of {@link JVMRandom}.
     */
    public static final Random JVM_RANDOM = new JVMRandom();

// should be possible for JVM_RANDOM?
//    public static void nextBytes(byte[]) {
//    public synchronized double nextGaussian();
//    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed int value
     * from the Math.random() sequence.</p>
     * <b>N.B. All values are >= 0.<b>
     * @return the random int
     */
    public static int nextInt() {
        return nextInt(JVM_RANDOM);
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed int value
     * from the given <code>random</code> sequence.</p>
     *
     * @param random the Random sequence generator.
     * @return the random int
     */
    public static int nextInt(Random random) {
        return random.nextInt();
    }

    /**
     * <p>Returns a pseudorandom, uniformly distributed int value
     * between <code>0</code> (inclusive) and the specified value
     * (exclusive), from the Math.random() sequence.</p>
     *
     * @param n  the specified exclusive max-value
     * @return the random int
     */
    public static int nextInt(int n) {
        return nextInt(JVM_RANDOM, n);
    }

    /**
     * <p>Returns a pseudorandom, uniformly distributed int value
     * between <code>0</code> (inclusive) and the specified value
     * (exclusive), from the given Random sequence.</p>
     *
     * @param random the Random sequence generator.
     * @param n  the specified exclusive max-value
     * @return the random int
     */
    public static int nextInt(Random random, int n) {
        // check this cannot return 'n'
        return random.nextInt(n);
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed long value
     * from the Math.random() sequence.</p>
     * <b>N.B. All values are >= 0.<b>
     * @return the random long
     */
    public static long nextLong() {
        return nextLong(JVM_RANDOM);
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed long value
     * from the given Random sequence.</p>
     *
     * @param random the Random sequence generator.
     * @return the random long
     */
    public static long nextLong(Random random) {
        return random.nextLong();
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed boolean value
     * from the Math.random() sequence.</p>
     *
     * @return the random boolean
     */
    public static boolean nextBoolean() {
        return nextBoolean(JVM_RANDOM);
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed boolean value
     * from the given random sequence.</p>
     *
     * @param random the Random sequence generator.
     * @return the random boolean
     */
    public static boolean nextBoolean(Random random) {
        return random.nextBoolean();
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed float value
     * between <code>0.0</code> and <code>1.0</code> from the Math.random()
     * sequence.</p>
     *
     * @return the random float
     */
    public static float nextFloat() {
        return nextFloat(JVM_RANDOM);
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed float value
     * between <code>0.0</code> and <code>1.0</code> from the given Random
     * sequence.</p>
     *
     * @param random the Random sequence generator.
     * @return the random float
     */
    public static float nextFloat(Random random) {
        return random.nextFloat();
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed float value
     * between <code>0.0</code> and <code>1.0</code> from the Math.random()
     * sequence.</p>
     *
     * @return the random double
     */
    public static double nextDouble() {
        return nextDouble(JVM_RANDOM);
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed float value
     * between <code>0.0</code> and <code>1.0</code> from the given Random
     * sequence.</p>
     *
     * @param random the Random sequence generator.
     * @return the random double
     */
    public static double nextDouble(Random random) {
        return random.nextDouble();
    }

}
final class JVMRandom extends Random {

    /**
     * Required for serialization support.
     *
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 1L;

    private static final Random SHARED_RANDOM = new Random();

    /**
     * Ensures that only the parent constructor can call reseed.
     */
    private boolean constructed = false;

    /**
     * Constructs a new instance.
     */
    public JVMRandom() {
        this.constructed = true;
    }

    /**
     * Unsupported in 2.0.
     *
     * @param seed ignored
     * @throws UnsupportedOperationException
     */
    public synchronized void setSeed(long seed) {
        if (this.constructed) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Unsupported in 2.0.
     *
     * @return Nothing, this method always throws an UnsupportedOperationException.
     * @throws UnsupportedOperationException
     */
    public synchronized double nextGaussian() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported in 2.0.
     *
     * @param byteArray ignored
     * @throws UnsupportedOperationException
     */
    public void nextBytes(byte[] byteArray) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed int value
     * from the Math.random() sequence.</p>
     * Identical to <code>nextInt(Integer.MAX_VALUE)</code>
     * <p>
     * <b>N.B. All values are >= 0.<b>
     * </p>
     * @return the random int
     */
    public int nextInt() {
        return nextInt(Integer.MAX_VALUE);
    }

    /**
     * <p>Returns a pseudorandom, uniformly distributed int value between
     * <code>0</code> (inclusive) and the specified value (exclusive), from
     * the Math.random() sequence.</p>
     *
     * @param n  the specified exclusive max-value
     * @return the random int
     * @throws IllegalArgumentException when <code>n &lt;= 0</code>
     */
    public int nextInt(int n) {
        return SHARED_RANDOM.nextInt(n);
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed long value
     * from the Math.random() sequence.</p>
     * Identical to <code>nextLong(Long.MAX_VALUE)</code>
     * <p>
     * <b>N.B. All values are >= 0.<b>
     * </p>
     * @return the random long
     */
    public long nextLong() {
        return nextLong(Long.MAX_VALUE);
    }


    /**
     * <p>Returns a pseudorandom, uniformly distributed long value between
     * <code>0</code> (inclusive) and the specified value (exclusive), from
     * the Math.random() sequence.</p>
     *
     * @param n  the specified exclusive max-value
     * @return the random long
     * @throws IllegalArgumentException when <code>n &lt;= 0</code>
     */
    public static long nextLong(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                "Upper bound for nextInt must be positive"
            );
        }
        // Code adapted from Harmony Random#nextInt(int)
        if ((n & -n) == n) { // n is power of 2
            // dropping lower order bits improves behaviour for low values of n
            return next63bits() >> 63 // drop all the bits
                 - bitsRequired(n-1); // except the ones we need
        }
        // Not a power of two
        long val;
        long bits;
        do { // reject some values to improve distribution
            bits = next63bits();
            val = bits % n;
        } while (bits - val + (n - 1) < 0);
        return val;
     }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed boolean value
     * from the Math.random() sequence.</p>
     *
     * @return the random boolean
     */
    public boolean nextBoolean() {
        return SHARED_RANDOM.nextBoolean();
    }

    /**
     * <p>Returns the next pseudorandom, uniformly distributed float value
     * between <code>0.0</code> and <code>1.0</code> from the Math.random()
     * sequence.</p>
     *
     * @return the random float
     */
    public float nextFloat() {
        return SHARED_RANDOM.nextFloat();
    }

    /**
     * <p>Synonymous to the Math.random() call.</p>
     *
     * @return the random double
     */
    public double nextDouble() {
        return SHARED_RANDOM.nextDouble();
    }

    /**
     * Get the next unsigned random long
     * @return unsigned random long
     */
    private static long next63bits(){
        // drop the sign bit to leave 63 random bits
        return SHARED_RANDOM.nextLong() & 0x7fffffffffffffffL;
    }

    /**
     * Count the number of bits required to represent a long number.
     *
     * @param num long number
     * @return number of bits required
     */
    private static int bitsRequired(long num){
        // Derived from Hacker's Delight, Figure 5-9
        long y=num; // for checking right bits
        int n=0; // number of leading zeros found
        while(true){
            // 64 = number of bits in a long
            if (num < 0) {
                return 64-n; // no leading zeroes left
            }
            if (y == 0) {
                return n; // no bits left to check
            }
            n++;
            num=num << 1; // check leading bits
            y=y >> 1; // check trailing bits
        }
    }
}