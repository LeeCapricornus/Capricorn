package org.capricorn.transport;

public interface Transporter {

    /**
     * Returns the transport protocol
     * @return
     */
    Protocol protocol();

    /**
     * 传输层协议
     */
    enum Protocol{
        TCP
    }
}
