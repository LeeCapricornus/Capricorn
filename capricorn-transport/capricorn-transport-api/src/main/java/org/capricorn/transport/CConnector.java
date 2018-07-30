package org.capricorn.transport;

import org.capricorn.transport.channel.CChannelGroup;
import org.capricorn.transport.processor.ConsumerProcessor;

import java.util.Collection;
/**
 * @author ljl
 * @date 2018/07/18
 */
public interface CConnector<C> extends Transporter {
    /**
     * Contains connector options
     * @return
     */
    CConfig config();

    /**
     * Returns the rpc processor.
     */
    ConsumerProcessor processor();

    /**
     * Binds the rpc processor.
     * @param processor
     */
    void withProcessor(ConsumerProcessor processor);

    /**
     * Connects to the remote peer.
     * @param address
     * @return
     */
    C connect(UnresolvedAddress address);

    /**
     * Connects to the remote peer.
     * @param address
     * @param async
     * @return
     */
    C connect(UnresolvedAddress address , boolean async);

    /**
     * Returns or new a {@link CChannelGroup}
     * @param address
     * @param async
     * @return
     */
    CChannelGroup group(UnresolvedAddress address ,boolean async);

    /**
     * Returns all {@link CChannelGroup}s
     * @return
     */
    Collection<CChannelGroup> groups();

    /**
     * Add a {@link CChannelGroup} by {@link Directory}
     * @param directory
     * @param group
     * @return
     */
    boolean addChannelGroup(Directory directory,CChannelGroup group);

    /**
     * Removes a {@link CChannelGroup} by {@link  Directory}
     * @param directory
     * @param group
     * @return
     */
    boolean removeChannelGroup(Directory directory,CChannelGroup group);









}
