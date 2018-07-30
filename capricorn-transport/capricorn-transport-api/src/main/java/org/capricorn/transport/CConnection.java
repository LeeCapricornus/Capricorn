package org.capricorn.transport;

/**
 * @author ljl
 * @date 2018/07/18
 */
public abstract class CConnection {

    private final UnresolvedAddress address;

    public CConnection(UnresolvedAddress address){
        this.address =address;
    }

    public UnresolvedAddress getAddress(){
        return address;
    }

    public void operationComplete(OperationListener listener){
        // the default implementation does nothing
    }

    public abstract void setReconnect(boolean reconnect);

    public interface  OperationListener{
        void complete(boolean isSuccess);
    }


}
