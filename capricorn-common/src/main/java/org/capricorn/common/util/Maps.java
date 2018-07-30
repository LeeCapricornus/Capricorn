package org.capricorn.common.util;

import org.jctools.maps.NonBlockingHashMap;
import org.jctools.maps.NonBlockingHashMapLong;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.capricorn.common.util.Preconditions.*;

public final class Maps {

    private static final boolean USE_NON_BLOCKING_HASH = SystemPropertyUtil.getBoolean("capricorn.use.non_blocking_hash",false);

    /**
     * Creates  a mutable,empty {@code HashMap} instance
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> HashMap<K,V> newHashMap(){ return new HashMap<>();}

    public static <K,V> HashMap<K,V> newHashMapWithExceptedSize(int expectedSize){
        return new HashMap<>(capacity(expectedSize));
    }

    /**
     * Creates an {@code IdentityHashMap} instance
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V>IdentityHashMap<K,V> newIdentityHashMap(){
        return new IdentityHashMap<>();
    }

    public static <K,V> IdentityHashMap<K,V> newIdentityHashMapWithExceptedSize(int expectedSize){
        return new IdentityHashMap<>(capacity(expectedSize));
    }

    /**
     * Creates a mutable,empty,insertion-ordered {@code LinkedHashMap} instance.
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> LinkedHashMap<K,V> newLinkedHashMap(){
        return new LinkedHashMap<>();
    }

    /**
     * Creates a mutable,empty{@code TreeMap} instance using the natural ordering of its elements
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K extends Comparable,V> TreeMap<K,V> newTreeMap(){
        return new TreeMap<>();
    }

    public static <K,V> ConcurrentMap<K,V> newConcurrentMap(){
        if(USE_NON_BLOCKING_HASH){
            return new NonBlockingHashMap<>();
        }
        return new ConcurrentHashMap<>();
    }

    public static <K,V> ConcurrentMap<K,V> newConcurrentMap(int initialCapacity){
        if(USE_NON_BLOCKING_HASH){
            return new NonBlockingHashMap<>(initialCapacity);
        }
        return new ConcurrentHashMap<>(initialCapacity);
    }

    /**
     * Creates a mutable, empty {@code NonBlockingHashMapLong} instance.
     */
    public static <V> ConcurrentMap<Long, V> newConcurrentMapLong() {
        return new NonBlockingHashMapLong<>();
    }

    /**
     * Creates a {@code NonBlockingHashMapLong} instance, with a high enough "initial capacity"
     * that it should hold {@code expectedSize} elements without growth.
     */
    public static <V> ConcurrentMap<Long, V> newConcurrentMapLong(int initialCapacity) {
        return new NonBlockingHashMapLong<>(initialCapacity);
    }

    /**
     * Returns a capacity that is sufficient to keep the map from being resized as
     * long as it grows no larger than expectedSize and the load factor is >= its default(0.75)
     * @param expectedSize
     * @return
     */
    private static int capacity(int expectedSize){
        if(expectedSize < 3){
            checkArgument(expectedSize >= 0,"expectedSize cannot be negative but was:"+expectedSize);
            return expectedSize+1;
        }
        if(expectedSize < Ints.MAX_POWER_OF_TWO){
            return expectedSize + expectedSize/3;
        }
        return Integer.MAX_VALUE;
    }




}
