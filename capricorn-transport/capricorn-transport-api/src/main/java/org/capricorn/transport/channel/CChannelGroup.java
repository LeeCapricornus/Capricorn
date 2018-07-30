package org.capricorn.transport.channel;

import org.capricorn.transport.Directory;
import org.capricorn.transport.UnresolvedAddress;

import java.util.List;

/**
 * Based on the same address of the channel group
 *
 * 要注意的是它管理的是相同的地址{@link CChannel}
 */
public interface CChannelGroup {

    /**
     * Returns the remote address of this group.
     * @return
     */
    UnresolvedAddress remoteAddress();

    /**
     * Returns the next {@link CChannel} in the group.
     * @return
     */
    CChannel next();

    /**
     * Returns all {@link CChannel}s in the group.
     * @return
     */
    List<? extends CChannel> channels();

    /**
     * Returns true if this group contains no {@link CChannel}.
     * @return
     */
    boolean isEmpty();

    /**
     * Adds the specified {@link CChannel} to this group
     * @param channel
     * @return
     */
    boolean add(CChannel channel);

    /**
     * Removes the specified {@link CChannel} from this group.
     * @param channel
     * @return
     */
    boolean remove(CChannel channel);

    /**
     * Returns the number of {@link CChannel}s in this group(its cardinality).
     * @return
     */
    int size();

    /**
     * Sets the capacity of this group.
     * @param capacity
     */
    void setCapacity(int capacity);

    /**
     * The capacity of this group.
     * @return
     */
    int getCapacity();

    /**
     * If connecting return true,otherwise return false
     * @return
     */
    boolean isConnecting();

    /**
     * Sets connecting state
     * @param connecting
     */
    void setConnecting(boolean connecting);

    /**
     * If available return true,otherwise return false.
     * @return
     */
    boolean isAvailable();

    /**
     * Wait unit the {@link CChannel}s are available or timeout,
     * if available return true,otherwise return false.
     * @param timeoutMillis
     * @return
     */
    boolean waitForAvailable(long timeoutMillis);

    /**
     * Be called when the {@link CChannel}s are available.
     * @param listener
     */
    void onAvailable(Runnable listener);

    /**
     * Gets weight of service
     * @param directory
     * @return
     */
    int getWeight(Directory directory);

    /**
     * Puts the weight of service
     * @param directory
     * @param weight
     */
    void putWeight(Directory directory,int weight);

    /**
     * Removes the weight of service
     * @param directory
     */
    void removeWeight(Directory directory);

    /**
     * Warm-up time.
     * @return
     */
    int getWarmUp();

    /**
     * Sets warm-up time.
     * @param warmUp
     */
    void setWarmUp(int warmUp);

    /**
     * Returns {@code true} if warm up to complete.
     * @return
     */
    boolean isWarmUpComplete();

    /**
     * Time of birth
     * @return
     */
    long timestamp();

    /**
     * Deadline millis.
     * @return
     */
    long deadlineMillis();


}
