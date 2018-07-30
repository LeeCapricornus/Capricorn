package org.capricorn.transport;

import org.capricorn.common.util.Maps;
import org.capricorn.common.util.internal.logging.InternalLogger;
import org.capricorn.common.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Capricorn的连接管理器，用于自动管理（按照地址归组）连接
 * @author ljl
 * @date 2018/07/19
 */
public class CConnectionManager {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(CConnectionManager.class);

    private final ConcurrentMap<UnresolvedAddress , CopyOnWriteArrayList<CConnection>> connections = Maps.newConcurrentMap();

    /**
     * 设置Capricorn自动管理连接
     * @param connection
     */
    public void manage(CConnection connection){
        UnresolvedAddress address = connection.getAddress();
        CopyOnWriteArrayList<CConnection> connectionArrayList = connections.get(address);
        if(connectionArrayList == null){
            CopyOnWriteArrayList<CConnection>  newList = new CopyOnWriteArrayList<>();
            connectionArrayList = connections.putIfAbsent(address,newList);
            if(connectionArrayList == null){
                connectionArrayList = newList;
            }
        }
        connectionArrayList.add(connection);

    }

    /**
     * 取消对所有地址的自动重连
     */
    public void cancelAllAutoReconnect(){
        for (UnresolvedAddress address : connections.keySet()){
            cancelAutoReconnectByAddress(address);
        }

    }

    /**
     * 取消指定地址的自动重连
     * @param address
     */
    public void cancelAutoReconnectByAddress(UnresolvedAddress address){
        CopyOnWriteArrayList<CConnection> list = connections.remove(address);
        if (list != null){
            for ( CConnection c: list){
                c.setReconnect(false);
            }
        }
        logger.warn("Cannel reconnect to:{}.",address);
    }
}
