package org.capricorn.transport.netty;

/**
 * capricorn tcp connector based on netty.
 *
 * <pre>
 * ************************************************************************
 *                      ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
 *
 *                 ─ ─ ─│        Server         │─ ─▷
 *                 │                                 │
 *                      └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
 *                 │                                 ▽
 *                                              I/O Response
 *                 │                                 │
 *
 *                 │                                 │
 * ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
 * │               │                                 │                │
 *
 * │               │                                 │                │
 *   ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐      ┌ ─ ─ ─ ─ ─ ─▽─ ─ ─ ─ ─ ─ ─
 * │  ConnectionWatchdog#outbound        ConnectionWatchdog#inbound│  │
 *   └ ─ ─ ─ ─ ─ ─ △ ─ ─ ─ ─ ─ ─ ┘      └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
 * │                                                 │                │
 *                 │
 * │  ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐       ┌ ─ ─ ─ ─ ─ ─▽─ ─ ─ ─ ─ ─ ┐   │
 *     IdleStateChecker#outBound         IdleStateChecker#inBound
 * │  └ ─ ─ ─ ─ ─ ─△─ ─ ─ ─ ─ ─ ┘       └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘   │
 *                                                   │
 * │               │                                                  │
 *                                      ┌ ─ ─ ─ ─ ─ ─▽─ ─ ─ ─ ─ ─ ┐
 * │               │                     ConnectorIdleStateTrigger    │
 *                                      └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
 * │               │                                 │                │
 *
 * │               │                    ┌ ─ ─ ─ ─ ─ ─▽─ ─ ─ ─ ─ ─ ┐   │
 *                                            ProtocolDecoder
 * │               │                    └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘   │
 *                                                   │
 * │               │                                                  │
 *                                      ┌ ─ ─ ─ ─ ─ ─▽─ ─ ─ ─ ─ ─ ┐
 * │               │                         ConnectorHandler         │
 *    ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐       └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
 * │        ProtocolEncoder                          │                │
 *    └ ─ ─ ─ ─ ─ ─△─ ─ ─ ─ ─ ─ ┘
 * │                                                 │                │
 * ─ ─ ─ ─ ─ ─ ─ ─ ┼ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─
 *                                       ┌ ─ ─ ─ ─ ─ ▽ ─ ─ ─ ─ ─ ┐
 *                 │
 *                                       │       Processor       │
 *                 │
 *            I/O Request                └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
 *
 * </pre>
 *
 * capricorn
 * org.capricorn.transport.netty
 *
 * @author
 */
public class CNettyTcpConnection {
}
