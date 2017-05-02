package com.sdsmdg.kd.trianglify.utilities;

/**
 * <h1>Thread Local Random</h1>
 * <b>Description : Generates Random numbers that are local to a thread thus reducing instructions
 *                  needed to generate a random number.</b>
 * <p>
 *
 * @author suyash
 * @since 2/5/17.
 */

public class ThreadLocalRandom {
    long seed = 0x5DEECE66DL;

    public ThreadLocalRandom(){

    }

    public int nextInt(int bits) {
        seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
        return (int)(seed%bits);
    }

}
