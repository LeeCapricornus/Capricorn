package org.capricorn.common.util.internal.logging;

import java.util.logging.Logger;

public class JdkLoggerFactory extends InternalLoggerFactory{
    @Override
    protected InternalLogger newInstance(String name) {
        return new JdkLogger(Logger.getLogger(name));
    }
}
