package org.capricorn.transport.channel;

import org.capricorn.serialization.io.OutputBuf;

import java.net.SocketAddress;

/**
 *
 * A nexus to a network socket or a component which is capable of IO
 * operations such as read,write.
 * @author ljl
 */
public interface CChannel {
    /**
     *
     */
    CFutureListener<CChannel> CLOSE = new CFutureListener<CChannel>() {
        @Override
        public void operationSuccess(CChannel cChannel) throws Exception {
            cChannel.close();
        }

        @Override
        public void operationFailure(CChannel cChannel, Throwable cause) throws Exception {
            cChannel.close();
        }
    };

    /**
     * Returns the identifier of this {@link CChannel}
     * @return
     */
    String id();

    /**
     * Return {@code true} if the {@link CChannel} is active and so connected
     * @return
     */
    boolean isActive();

    /**
     * Return {@code true} if the current {@code Thread} is executed in the IO thread ,{@code false} otherwise
     * @return
     */
    boolean inIOThread();

    /**
     * Returns the local address where this channel is bound to.
     * @return
     */
    SocketAddress localAddress();

    /**
     * Returns the remote address where this channel is connected to.
     * @return
     */
    SocketAddress remoteAddress();

    /**
     * Returns {@code true} if and only if the IO thread will perform the
     * requested write operation immediately.
     * Any write requests made when this method returns {@code  false} are
     * queued until the IO thread is ready to process the queued write requests.
     * @return
     */
    boolean isWritable();

    /**
     * Is set up automatic reconnection.
     * @return
     */
    boolean isMarkedReconnect();

    /**
     * Returns {@code true} if and only if read(socket) will be invoked
     * automatically so that a user application does't need to call it
     * at all.The default value is {@code true}.
     * @return
     */
    boolean isAutoRead();

    /**
     * Sets if read(socket) will be invoked automatically so that a user application
     * does't need to call it at all.The default value is {@code true}.
     * @param autoRead
     */
    void setAutoRead(boolean autoRead);

    /**
     * Requests to close this {@link CChannel}
     * @return
     */
    CChannel close();

    /**
     * Requests to close this {@link CChannel}
     * @param listener
     * @return
     */
    CChannel close(CFutureListener<CChannel> listener);

    /**
     * Requests to write a message on the channel.
     * @param msg
     * @return
     */
    CChannel write(Object msg);

    /**
     * Requests to write a message on the channel.
     * @param msg
     * @param listener
     * @return
     */
    CChannel write(Object msg ,CFutureListener<CChannel> listener);

    /**
     * Allocate a {@link OutputBuf}
     * @return
     */
    OutputBuf allocOutputBuf();


}
