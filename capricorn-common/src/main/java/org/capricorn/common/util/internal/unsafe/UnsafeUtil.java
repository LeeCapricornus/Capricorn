package org.capricorn.common.util.internal.unsafe;

import org.capricorn.common.util.internal.logging.InternalLogger;
import org.capricorn.common.util.internal.logging.InternalLoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import static org.capricorn.common.util.StackTraceUtil.stackTrace;

public final class UnsafeUtil {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(UnsafeUtil.class);

    private static final Unsafe unsafe;

    static{
        Unsafe _unsafe;
        try{
            //Creates  {@code Unsafe} instance by the Reflection
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            _unsafe = (Unsafe) unsafeField.get(null);
        }catch (Throwable t){
            if(logger.isWarnEnabled()){
                logger.warn("sun.misc.Unsafe.theUnsafe:unavailable,{}",stackTrace(t));
            }
            _unsafe = null;
        }
        unsafe = _unsafe;
    }

    private static final MemoryAccessor memoryAccessor = new MemoryAccessor(unsafe);

    private static long BYTE_ARRAY_BASE_OFFSET = arrayBaseOffset(byte[].class);
    private static final long BOOLEAN_ARRAY_BASE_OFFSET = arrayBaseOffset(boolean[].class);
    private static final long BOOLEAN_ARRAY_INDEX_SCALE = arrayIndexScale(boolean[].class);

    private static final long INT_ARRAY_BASE_OFFSET = arrayBaseOffset(int[].class);
    private static final long INT_ARRAY_INDEX_SCALE = arrayIndexScale(int[].class);

    private static final long LONG_ARRAY_BASE_OFFSET = arrayBaseOffset(long[].class);
    private static final long LONG_ARRAY_INDEX_SCALE = arrayIndexScale(long[].class);

    private static final long FLOAT_ARRAY_BASE_OFFSET = arrayBaseOffset(float[].class);
    private static final long FLOAT_ARRAY_INDEX_SCALE = arrayIndexScale(float[].class);

    private static final long DOUBLE_ARRAY_BASE_OFFSET = arrayBaseOffset(double[].class);
    private static final long DOUBLE_ARRAY_INDEX_SCALE = arrayIndexScale(double[].class);

    private static final long OBJECT_ARRAY_BASE_OFFSET = arrayBaseOffset(Object[].class);
    private static final long OBJECT_ARRAY_INDEX_SCALE = arrayIndexScale(Object[].class);

    private static final long BUFFER_ADDRESS_OFFSET = objectFieldOffset(bufferAddressField());

    private static final long STRING_VALUE_OFFSET = objectFieldOffset(stringValueField());

    /**
     * Returns the {@link sun.misc.Unsafe} instance.
     * @return
     */
    public static Unsafe getUnsafe(){
        return unsafe;
    }

    /**
     * Returns the {@link MemoryAccessor}
     * @return
     */
    public static MemoryAccessor getMemoryAccessor(){
        return memoryAccessor;
    }

    public static byte getByte(Object target ,long offset){
        return memoryAccessor.getByte(target,offset);
    }

    public static void putByte(Object target,long offset ,byte value){
        memoryAccessor.putByte(target,offset,value);
    }


    public static int getInt(Object target, long offset) {
        return memoryAccessor.getInt(target, offset);
    }

    public static void putInt(Object target, long offset, int value) {
        memoryAccessor.putInt(target, offset, value);
    }

    public static long getLong(Object target, long offset) {
        return memoryAccessor.getLong(target, offset);
    }

    public static void putLong(Object target, long offset, long value) {
        memoryAccessor.putLong(target, offset, value);
    }

    public static boolean getBoolean(Object target, long offset) {
        return memoryAccessor.getBoolean(target, offset);
    }

    public static void putBoolean(Object target, long offset, boolean value) {
        memoryAccessor.putBoolean(target, offset, value);
    }

    public static float getFloat(Object target, long offset) {
        return memoryAccessor.getFloat(target, offset);
    }

    public static void putFloat(Object target, long offset, float value) {
        memoryAccessor.putFloat(target, offset, value);
    }

    public static double getDouble(Object target, long offset) {
        return memoryAccessor.getDouble(target, offset);
    }

    public static void putDouble(Object target, long offset, double value) {
        memoryAccessor.putDouble(target, offset, value);
    }

    public static Object getObject(Object target, long offset) {
        return memoryAccessor.getObject(target, offset);
    }

    public static void putObject(Object target, long offset, Object value) {
        memoryAccessor.putObject(target, offset, value);
    }

    public static byte getByteVolatile(byte[] target, long index) {
        return memoryAccessor.getByteVolatile(target, BYTE_ARRAY_BASE_OFFSET + index);
    }

    public static void putByteVolatile(byte[] target, long index, byte value) {
        memoryAccessor.putByteVolatile(target, BYTE_ARRAY_BASE_OFFSET + index, value);
    }

    public static int getIntVolatile(int[] target, long index) {
        return memoryAccessor.getIntVolatile(target, INT_ARRAY_BASE_OFFSET + (index * INT_ARRAY_INDEX_SCALE));
    }

    public static void putIntVolatile(int[] target, long index, int value) {
        memoryAccessor.putIntVolatile(target, INT_ARRAY_BASE_OFFSET + (index * INT_ARRAY_INDEX_SCALE), value);
    }

    public static long getLongVolatile(long[] target, long index) {
        return memoryAccessor.getLongVolatile(
                target, LONG_ARRAY_BASE_OFFSET + (index * LONG_ARRAY_INDEX_SCALE));
    }

    public static void putLongVolatile(long[] target, long index, long value) {
        memoryAccessor.putLongVolatile(
                target, LONG_ARRAY_BASE_OFFSET + (index * LONG_ARRAY_INDEX_SCALE), value);
    }

    public static boolean getBooleanVolatile(boolean[] target, long index) {
        return memoryAccessor.getBooleanVolatile(
                target, BOOLEAN_ARRAY_BASE_OFFSET + (index * BOOLEAN_ARRAY_INDEX_SCALE));
    }

    public static void putBooleanVolatile(boolean[] target, long index, boolean value) {
        memoryAccessor.putBooleanVolatile(
                target, BOOLEAN_ARRAY_BASE_OFFSET + (index * BOOLEAN_ARRAY_INDEX_SCALE), value);
    }

    public static float getFloatVolatile(float[] target, long index) {
        return memoryAccessor.getFloatVolatile(
                target, FLOAT_ARRAY_BASE_OFFSET + (index * FLOAT_ARRAY_INDEX_SCALE));
    }

    public static void putFloatVolatile(float[] target, long index, float value) {
        memoryAccessor.putFloatVolatile(
                target, FLOAT_ARRAY_BASE_OFFSET + (index * FLOAT_ARRAY_INDEX_SCALE), value);
    }

    public static double getDoubleVolatile(double[] target, long index) {
        return memoryAccessor.getDoubleVolatile(
                target, DOUBLE_ARRAY_BASE_OFFSET + (index * DOUBLE_ARRAY_INDEX_SCALE));
    }

    public static void putDoubleVolatile(double[] target, long index, double value) {
        memoryAccessor.putDoubleVolatile(
                target, DOUBLE_ARRAY_BASE_OFFSET + (index * DOUBLE_ARRAY_INDEX_SCALE), value);
    }

    public static Object getObjectVolatile(Object[] target, long index) {
        return memoryAccessor.getObjectVolatile(
                target, OBJECT_ARRAY_BASE_OFFSET + (index * OBJECT_ARRAY_INDEX_SCALE));
    }

    public static void putObjectVolatile(Object[] target, long index, Object value) {
        memoryAccessor.putObjectVolatile(
                target, OBJECT_ARRAY_BASE_OFFSET + (index * OBJECT_ARRAY_INDEX_SCALE), value);
    }


    /**
     *
     * @param clazz
     * @return the length of header
     */
    public static int arrayBaseOffset(Class<?> clazz){
        return unsafe !=null ? unsafe.arrayBaseOffset(clazz):-1;
    }

    /**
     *
     * Reports the scale factor for addressing elements in the storage allocation of  a given array class.
     * @param clazz
     * @return the size of array
     */
    public static int arrayIndexScale(Class<?> clazz){
        return unsafe != null ? unsafe.arrayIndexScale(clazz) :-1;
    }

    public static long objectFieldOffset(Field field){
        return field == null || unsafe == null ? -1 :unsafe.objectFieldOffset(field);
    }

    /**
     * Gets the offset of the {@code address} field of the given direct {@link ByteBuffer}
     * @param buffer
     * @return
     */
    public static long addressOffset(ByteBuffer buffer){
        return unsafe.getLong(buffer,BUFFER_ADDRESS_OFFSET);
    }

    private static Field bufferAddressField(){
        return field(Buffer.class,"address",long.class);
    }

    private static Field stringValueField(){
        return field(String.class ,"value",char[].class);
    }

    /**
     * Gets the field with the given name within the class ,or {@code  null} if not found.If found ,the field is made accessible.
     * @param clazz
     * @param fieldName
     * @param expectedType
     * @return
     */
    private static Field field(Class<?> clazz,String fieldName,Class<?> expectedType){
       Field field;
       try{
           field =clazz.getDeclaredField(fieldName);
           field.setAccessible(true);
           if(!field.getType().equals(expectedType)){
               return null;
           }
       }catch(Throwable t){
           field = null;
       }
       return field;
    }

    public static class MemoryAccessor{

        final Unsafe unsafe;

        MemoryAccessor(Unsafe unsafe){
            this.unsafe = unsafe;
        }

        public byte getByte(Object target ,long offset){
            return unsafe.getByte(target , offset);
        }

        public void putByte(Object target , long offset , byte value){
            unsafe.putByte(target , offset , value);
        }

        public short getShort(Object target, long offset) {
            return unsafe.getShort(target, offset);
        }

        public void putShort(Object target, long offset, short value) {
            unsafe.putShort(target, offset, value);
        }

        public int getInt(Object target, long offset) {
            return unsafe.getInt(target, offset);
        }

        public void putInt(Object target, long offset, int value) {
            unsafe.putInt(target, offset, value);
        }

        public long getLong(Object target, long offset) {
            return unsafe.getLong(target, offset);
        }

        public void putLong(Object target, long offset, long value) {
            unsafe.putLong(target, offset, value);
        }

        public boolean getBoolean(Object target, long offset) {
            return unsafe.getBoolean(target, offset);
        }

        public void putBoolean(Object target, long offset, boolean value) {
            unsafe.putBoolean(target, offset, value);
        }

        public float getFloat(Object target, long offset) {
            return unsafe.getFloat(target, offset);
        }

        public void putFloat(Object target, long offset, float value) {
            unsafe.putFloat(target, offset, value);
        }

        public double getDouble(Object target, long offset) {
            return unsafe.getDouble(target, offset);
        }

        public void putDouble(Object target, long offset, double value) {
            unsafe.putDouble(target, offset, value);
        }

        public Object getObject(Object target, long offset) {
            return unsafe.getObject(target, offset);
        }

        public void putObject(Object target, long offset, Object value) {
            unsafe.putObject(target, offset, value);
        }

        public byte getByte(long address) {
            return unsafe.getByte(address);
        }

        public void putByte(long address, byte value) {
            unsafe.putByte(address, value);
        }

        public short getShort(long address) {
            return unsafe.getShort(address);
        }

        public void putShort(long address, short value) {
            unsafe.putShort(address, value);
        }

        public int getInt(long address) {
            return unsafe.getInt(address);
        }

        public void putInt(long address, int value) {
            unsafe.putInt(address, value);
        }

        public long getLong(long address) {
            return unsafe.getLong(address);
        }

        public void putLong(long address, long value) {
            unsafe.putLong(address, value);
        }

        public void copyMemory(Object srcBase ,long srcOffset ,Object dstBase ,long dstOffset ,long bytes){
            unsafe.copyMemory(srcBase,srcOffset,dstBase,dstOffset,bytes);
        }

        public void copyMemory(long srcAddress, long dstAddress, long bytes) {
            unsafe.copyMemory(srcAddress, dstAddress, bytes);
        }

        public byte getByteVolatile(Object target, long offset) {
            return unsafe.getByteVolatile(target, offset);
        }

        public void putByteVolatile(Object target, long offset, byte value) {
            unsafe.putByteVolatile(target, offset, value);
        }

        public short getShortVolatile(Object target, long offset) {
            return unsafe.getShortVolatile(target, offset);
        }

        public void putShortVolatile(Object target, long offset, short value) {
            unsafe.putShortVolatile(target, offset, value);
        }

        public int getIntVolatile(Object target, long offset) {
            return unsafe.getIntVolatile(target, offset);
        }

        public void putIntVolatile(Object target, long offset, int value) {
            unsafe.putIntVolatile(target, offset, value);
        }

        public long getLongVolatile(Object target, long offset) {
            return unsafe.getLongVolatile(target, offset);
        }

        public void putLongVolatile(Object target, long offset, long value) {
            unsafe.putLongVolatile(target, offset, value);
        }

        public boolean getBooleanVolatile(Object target, long offset) {
            return unsafe.getBooleanVolatile(target, offset);
        }

        public void putBooleanVolatile(Object target, long offset, boolean value) {
            unsafe.putBooleanVolatile(target, offset, value);
        }

        public float getFloatVolatile(Object target, long offset) {
            return unsafe.getFloatVolatile(target, offset);
        }

        public void putFloatVolatile(Object target, long offset, float value) {
            unsafe.putFloatVolatile(target, offset, value);
        }

        public double getDoubleVolatile(Object target, long offset) {
            return unsafe.getDoubleVolatile(target, offset);
        }

        public void putDoubleVolatile(Object target, long offset, double value) {
            unsafe.putDoubleVolatile(target, offset, value);
        }

        public Object getObjectVolatile(Object target, long offset) {
            return unsafe.getObjectVolatile(target, offset);
        }

        public void putObjectVolatile(Object target, long offset, Object value) {
            unsafe.putObjectVolatile(target, offset, value);
        }

    }
}
