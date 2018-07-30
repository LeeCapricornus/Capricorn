package org.capricorn.rpc;

import org.capricorn.registry.Registry;

/**
 *
 *
 * @author  Capricorn
 */
public interface JClient extends Registry {

    /**
     * 每个应用都建议设置一个appName
     * @return
     */
    String appName();

}
