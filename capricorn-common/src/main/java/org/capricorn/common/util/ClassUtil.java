package org.capricorn.common.util;

import org.capricorn.common.util.internal.logging.InternalLogger;
import org.capricorn.common.util.internal.logging.InternalLoggerFactory;

import static org.capricorn.common.util.StackTraceUtil.stackTrace;

/**
 * @author ljl
 * @date 2018/07/18
 */
public final class ClassUtil {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ClassUtil.class);

    public static void initializeClass(String className, long tolerableMillis){
        long startTime = System.currentTimeMillis();
        try{
            Class.forName(className);
        }catch (Throwable t){
            if(logger.isWarnEnabled()){
                logger.warn("Failed to load class [{}] {}.", className, stackTrace(t));
            }
        }
        long duration = System.currentTimeMillis() - startTime;
        if(duration > tolerableMillis){
            logger.warn("{}.<clinit> duration: {} millis.", className, duration);
        }
    }

    public static void checkClass(String className,String message){
        try{
            Class.forName(className);
        }catch (Throwable t){
            throw new RuntimeException(message,t);
        }
    }

    private ClassUtil(){}
}
