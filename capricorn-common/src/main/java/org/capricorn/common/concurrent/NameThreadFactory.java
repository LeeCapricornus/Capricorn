package org.capricorn.common.concurrent;

import org.capricorn.common.util.internal.InternalThread;
import org.capricorn.common.util.internal.logging.InternalLogger;
import org.capricorn.common.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.capricorn.common.util.Preconditions.checkNotNull;

/**
 * @author ljl
 * @date 2018/07/18
 */
public class NameThreadFactory implements ThreadFactory {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NameThreadFactory.class);

    private final AtomicInteger id = new AtomicInteger();
    private final String name;
    private final boolean daemon;
    private final int priority;
    private final ThreadGroup group;


    public NameThreadFactory(String name){
        this(name, false ,Thread.NORM_PRIORITY);
    }

    public NameThreadFactory(String name ,boolean daemon){
        this(name,daemon,Thread.NORM_PRIORITY);
    }

    public NameThreadFactory(String name , boolean daemon , int priority){
        this.name = name +"#";
        this.daemon = daemon;
        this.priority = priority;
        SecurityManager s = System.getSecurityManager();
        group = (s==null)? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }
    @Override
    public Thread newThread(Runnable r) {
        checkNotNull(r,"runnable");
        String name2 = name +id.getAndIncrement();
        Runnable r2 = wrapRunnable(r);
        Thread t = warpThread(group,r2,name2);
        try{
            if( t.isDaemon() != daemon){
                t.setDaemon(daemon);
            }
            if( t.getPriority() != priority){
                t.setPriority(priority);
            }
        }catch (Exception ignored){}
        logger.info("Creates new {}.", t);
        return t;
    }

    public ThreadGroup getThreadGroup(){
        return group;
    }

    protected Runnable wrapRunnable(Runnable r){
        return r;
    }

    protected Thread warpThread(ThreadGroup group,Runnable r, String name){
        return new InternalThread(group,r,name);
    }
}
