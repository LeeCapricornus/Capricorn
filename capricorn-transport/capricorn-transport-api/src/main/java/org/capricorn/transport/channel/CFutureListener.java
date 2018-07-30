package org.capricorn.transport.channel;

import java.util.EventListener;

public interface CFutureListener<C> extends EventListener {

    void operationSuccess(C c) throws Exception;

    void operationFailure(C c, Throwable cause) throws Exception;
}
