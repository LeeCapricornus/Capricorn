package org.capricorn.transport.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.capricorn.transport.CConnection;
import org.capricorn.transport.UnresolvedAddress;

public abstract  class CNettyConnection extends CConnection {
    private final ChannelFuture future;

    public CNettyConnection(UnresolvedAddress address, ChannelFuture future) {
        super(address);
        this.future =future;
    }

    public ChannelFuture getFuture() {
        return future;
    }

    @Override
    public void operationComplete(final OperationListener listener){
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                listener.complete(future.isSuccess());
            }
        });
    }


}
