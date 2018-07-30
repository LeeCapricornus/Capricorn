package org.capricorn.transport.processor;

import org.capricorn.transport.channel.CChannel;
import org.capricorn.transport.payload.JResponsePayload;

/**
 * @author capricorn
 */
public interface ConsumerProcessor {

    void handlerResponse(CChannel channel, JResponsePayload response)throws Exception;

    void shutdown();
}
