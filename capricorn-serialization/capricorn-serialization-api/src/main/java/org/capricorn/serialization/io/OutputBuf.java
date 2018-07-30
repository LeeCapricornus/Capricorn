package org.capricorn.serialization.io;

import java.io.OutputStream;
import java.nio.ByteBuffer;

public interface OutputBuf {

    /**
     * Exposes this backing data as an {@link OutputStream}
     * @return
     */
    OutputStream outputStream();

    /**
     * Exposes this backing data as a NIO {@link ByteBuffer}
     *
     * @param minWritableBytes
     * @return
     */
    ByteBuffer nioByteBuffer(int minWritableBytes);

    /**
     * Returns the number of readable bytes
     * @return
     */
    int size();

    /**
     * Returns {@code true} if and only if this buf has a reference to
     * the low-level memory address that point to the backing data.
     * @return
     */
    boolean hasMemoryAddress();

    /**
     * Returns the backing object.
     * @return
     */
    Object backingObject();
}
