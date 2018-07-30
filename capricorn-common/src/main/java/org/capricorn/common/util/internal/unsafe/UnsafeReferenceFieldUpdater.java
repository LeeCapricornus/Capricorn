package org.capricorn.common.util.internal.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public final class UnsafeReferenceFieldUpdater<U,W> {
    private final long offset;
    private final Unsafe unsafe;

    UnsafeReferenceFieldUpdater(Unsafe unsafe,Class<? super U> tClass, String fieldName) throws NoSuchFieldException{
        Field field =tClass.getDeclaredField(fieldName);
        if(unsafe == null){
            throw new NullPointerException("unsafe");
        }
        this.unsafe = unsafe;
        offset = unsafe.objectFieldOffset(field);
    }

    public void set(U object ,W newValue){
        unsafe.putObject(object,offset,newValue);
    }

    public W get(U obj){
        return (W) unsafe.getObject(obj,offset);
    }


}
