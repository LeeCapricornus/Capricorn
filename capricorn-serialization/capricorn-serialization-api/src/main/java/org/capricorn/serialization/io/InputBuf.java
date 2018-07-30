package org.capricorn.serialization.io;

import java.io.InputStream;
import java.nio.ByteBuffer;

public interface InputBuf {
    /**
     * Exposes this backing data's readable bytes as an {@link InputStream}.
     * @return
     */
    InputStream inputStream();

    /**
     * Exposes this backing data's readable bytes as a NIO {@link java.nio.ByteBuffer}.
     * @return
     */
    ByteBuffer nioByteBuffer();

    /**
     * Returns the number of readable bytes.
     * @return
     */
    int size();

    /**
     * Returns {@code true} if and only if this buf has a reference to the low-level memory address that points
     * to the backing data.
     * @return
     */
    boolean hasMemoryAddress();

    /**
     * Decreases the reference count by {@code 1} and deallocates this Object if the reference count reaches at {@code 0}.
     * @return
     */
    boolean release();




}
