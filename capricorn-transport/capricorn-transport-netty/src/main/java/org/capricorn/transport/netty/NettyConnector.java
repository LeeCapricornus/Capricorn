package org.capricorn.transport.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.util.HashedWheelTimer;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.capricorn.common.concurrent.NameThreadFactory;
import org.capricorn.common.util.CConstants;
import org.capricorn.common.util.ClassUtil;
import org.capricorn.common.util.Maps;
import org.capricorn.common.util.internal.logging.InternalLogger;
import org.capricorn.common.util.internal.logging.InternalLoggerFactory;
import org.capricorn.transport.CConnection;
import org.capricorn.transport.CConnectionManager;
import org.capricorn.transport.CConnector;
import org.capricorn.transport.UnresolvedAddress;
import org.capricorn.transport.channel.CChannelGroup;
import org.capricorn.transport.channel.DirectoryCChanelGroup;
import org.capricorn.transport.processor.ConsumerProcessor;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadFactory;

/**
 * @author ljl
 * @date 2018/07/18
 */
public abstract class NettyConnector implements CConnector<CConnection> {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NettyConnector.class);

    static{
        //touch off DefaultChannelId.<clinit>
        //because getProcessId() sometimes too slow
        ClassUtil.initializeClass("io.netty.channel.DefaultChannelId",500);
    }

    protected final Protocol protocol;
    protected final HashedWheelTimer timer = new HashedWheelTimer(new NameThreadFactory("connector.timer",true));

    private final ConcurrentMap<UnresolvedAddress ,CChannelGroup> addressGroups = Maps.newConcurrentMap();
    private final DirectoryCChanelGroup directoryGroup = new DirectoryCChanelGroup();
    private final CConnectionManager connectionManager = new CConnectionManager();

    private Bootstrap bootstrap;
    private EventLoopGroup worker;
    private int  nWorkers;

    private ConsumerProcessor processor;

    public NettyConnector(Protocol protocol){
        this(protocol, CConstants.AVAILABLE_PROCESSORS <<1);
    }

    public NettyConnector(Protocol protocol , int nWorkers){
        this.protocol = protocol;
        this.nWorkers = nWorkers;
    }

    protected void init(){
        ThreadFactory workFactory = workerThreadFactory("capricorn.connector");
    }

    protected ThreadFactory workerThreadFactory(String name){
        return new DefaultThreadFactory(name, Thread.MAX_PRIORITY);
    }


    /**
     * Create a new instance of {@link ThreadFactory} using the specified number of threads
     * @param nThreads
     * @param threadFactory
     * @return
     */
    protected abstract EventLoopGroup initEventLoopGroup(int nThreads , ThreadFactory threadFactory);

}
