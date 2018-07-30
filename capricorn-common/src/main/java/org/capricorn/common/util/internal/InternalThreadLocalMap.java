package org.capricorn.common.util.internal;

import org.capricorn.common.util.SystemPropertyUtil;
import org.capricorn.common.util.internal.unsafe.UnsafeReferenceFieldUpdater;
import org.capricorn.common.util.internal.unsafe.UnsafeUpdater;


import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

class LhsPadding{
    @SuppressWarnings("unused")
    protected  long p01,p02,p03,p04,p05,p06,p07;
}

class Fields extends LhsPadding{
    Object[] indexedVariables;

    //string-related thread-locals
    StringBuilder stringBuilder;
}

class RhsPadding extends Fields{
    @SuppressWarnings("unused")
    protected long p09,p010,p11,p12,p13,p14,p15;
}

public class InternalThreadLocalMap extends RhsPadding{
    private static final UnsafeReferenceFieldUpdater<StringBuilder,char[]> stringBuilderValueUpdater=
            UnsafeUpdater.newReferenceFieldUpdater(StringBuilder.class.getSuperclass(),"value");

    private static final int DEFAULT_STRING_BUILDER_MAX_CAPACITY=
            SystemPropertyUtil.getInt("jupiter.internal.thread.local.string_builder_max_capacity",1024<<6);

    private static final int DEFAULT_STRING_BUILDER_INITIAL_CAPACITY =
            SystemPropertyUtil.getInt("jupiter.internal.thread.local.string_builder_initial_capacity",512);

    //safeThread
    private static final ThreadLocal<InternalThreadLocalMap> slowThreadLocalMap = new ThreadLocal<>();
    //atomic
    private static final AtomicInteger nextIndex = new AtomicInteger();
    //UNSET is never modified
    public static final Object UNSET = new Object();

    //create an instance
    public static InternalThreadLocalMap getIfSet(){
        Thread thread = Thread.currentThread();
        if(thread instanceof  InternalThread){
            return ((InternalThread) thread).threadLocalMap();
        }
        if(thread instanceof InternalForkJoinWorkerThread){
            return ((InternalForkJoinWorkerThread)thread).threadLocalMap();
        }
        return slowThreadLocalMap.get();

    }

    public static InternalThreadLocalMap get(){
        Thread thread = Thread.currentThread();
        if(thread instanceof InternalThread){
            return fastGet((InternalThread)thread);
        }

        if(thread instanceof InternalForkJoinWorkerThread){
            return fastGet((InternalForkJoinWorkerThread)thread);
        }
        return slowGet();
    }

    public static void remove(){
        Thread thread = Thread.currentThread();
        if(thread instanceof InternalThread){
            ((InternalThread)thread).setThreadLocalMap(null);
        }else if(thread instanceof InternalForkJoinWorkerThread){
            ((InternalForkJoinWorkerThread) thread).setThreadLocalMap(null);
        }else{
            slowThreadLocalMap.remove();
        }
    }

    public static void destroy(){
        slowThreadLocalMap.remove();
    }

    public static int nextVariableIndex(){
        int index = nextIndex.getAndIncrement();
        if(index <0){
            nextIndex.decrementAndGet();
            throw new IllegalStateException("Too many thread-local indexed variables");
        }
        return index;
    }

    public static int lastVariableIndex(){
        return nextIndex.get()-1;
    }

    private InternalThreadLocalMap(){
        indexedVariables = newIndexedVariableTable();
    }

    public Object indexedVariable(int index){
        Object[] lookup = indexedVariables;
        return index < lookup.length ? lookup[index] : UNSET;
    }

    public boolean setIndexedVariable(int index ,Object value){
        Object[] lookup = indexedVariables;
        if(index <lookup.length){
            Object oldValue = lookup[index];
            lookup[index] =value;
            return oldValue ==UNSET;
        }else{
            expandIndexedVariableTableAndSet(index,value);
            return true;
        }
    }

    public Object removeIndexedVariable(int index){
        Object[] lookup = indexedVariables;
        if(index < lookup.length){
            Object v = lookup.length;
            lookup[index] = UNSET;
            return v;
        }else{
            return UNSET;
        }
    }

    public int size(){
        int count =0;
        for(Object o : indexedVariables){
            if(o !=UNSET){
                ++count;
            }
        }
        return count;
    }


    public StringBuilder stringBuilder(){
        StringBuilder builder = stringBuilder;
        //Check-Then-Act
        if(builder == null){
            stringBuilder = builder = new StringBuilder(DEFAULT_STRING_BUILDER_INITIAL_CAPACITY);
        }else{
            if(builder.capacity() > DEFAULT_STRING_BUILDER_MAX_CAPACITY){
                stringBuilderValueUpdater.set(builder,new char[DEFAULT_STRING_BUILDER_INITIAL_CAPACITY]);
            }
            builder.setLength(0);
        }
        return builder;
    }



    private static Object[] newIndexedVariableTable(){
        Object[] array = new Object[32];
        Arrays.fill(array,UNSET);
        return array;
    }

    private static InternalThreadLocalMap fastGet(InternalThread thread){
        InternalThreadLocalMap threadLocalMap = thread.threadLocalMap();
        if(threadLocalMap == null){
            thread.setThreadLocalMap(threadLocalMap = new InternalThreadLocalMap());
        }
        return threadLocalMap;
    }

    private static InternalThreadLocalMap fastGet(InternalForkJoinWorkerThread thread){
        InternalThreadLocalMap threadLocalMap = thread.threadLocalMap();
        if(threadLocalMap == null){
            thread.setThreadLocalMap(threadLocalMap = new InternalThreadLocalMap());
        }
        return threadLocalMap;
    }

    private static InternalThreadLocalMap slowGet(){
        ThreadLocal<InternalThreadLocalMap> slowThreadLocalMap = InternalThreadLocalMap.slowThreadLocalMap;
        InternalThreadLocalMap ret = slowThreadLocalMap.get();
        if(ret == null){
            ret = new InternalThreadLocalMap();
            slowThreadLocalMap.set(ret);
        }
        return ret;
    }

    private void expandIndexedVariableTableAndSet(int index ,Object value){
        Object[] oldArray = indexedVariables;
        final int oldCapacity =oldArray.length;
        int newCapacity = index;
        newCapacity |= newCapacity >>> 1;
        newCapacity |= newCapacity >>> 2;
        newCapacity |= newCapacity >>> 4;
        newCapacity |= newCapacity >>> 8;
        newCapacity |= newCapacity >>> 16;
        newCapacity++;

        Object[] newArray = Arrays.copyOf(oldArray,newCapacity);
        Arrays.fill(newArray ,oldCapacity,newArray.length,UNSET);
        newArray[index] = value;
        indexedVariables = newArray;
    }

}
