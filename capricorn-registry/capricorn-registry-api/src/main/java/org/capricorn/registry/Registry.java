package org.capricorn.registry;

/**
 *
 *
 * @author  capricorn
 */
public interface Registry {
    /**
     * Establish connections with registry server
     * @param connnectString list of servers to connect to [host1:port1,host2:port2]
            */
    void connectToRegistryServer(String connnectString);

}
