package org.capricorn.common.util.internal;

/**
 * 基于{@link org.capricorn.common.util.internal.InternalThreadLocalMap}的stringBuidler重复利用
 * 注意：不要在相同的线程中嵌套使用，太大的StringBuilder不要使用这个类，会导致hold超大内存一直不释放
 */
public final class StringBuilderHelper {

    private StringBuilderHelper(){}

    public static StringBuilder get(){
        return InternalThreadLocalMap.get().stringBuilder();
    }
}
