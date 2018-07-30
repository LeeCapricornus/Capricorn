package org.capricorn.common.util;

import org.capricorn.common.util.internal.logging.InternalLogger;
import org.capricorn.common.util.internal.logging.InternalLoggerFactory;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.regex.Pattern;

import static org.capricorn.common.util.StackTraceUtil.stackTrace;

/**
 *
 * A collection of utility methods to retrieve and parse the values of the Java system properties.
 *
 * @author  capricorn
 *
 * Fork jupiter
 */
public class SystemPropertyUtil {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SystemPropertyUtil.class);

    /**
     * Returns {@code true} if and only if the system property with the specified {@code key} exists.
     * @param key
     * @return
     */
    public static boolean contains(String key){
        return get(key)!=null;
    }

    public static String get(String key){
        return get(key,null);
    }

    /**
     * Returns the value of the Java system property with the specified
     *{@param key}  while falling back to the specified  {@param def} if the property access fails.
     * @return
     */
    public static String get(final String key ,String def){
        if(key == null){
            throw new NullPointerException("key");
        }
        if(key.isEmpty()){
            throw new IllegalArgumentException("key must not be empty");
        }
        String value = null;
        try{
            if(System.getSecurityManager() == null){
                value = System.getProperty(key);
            }else{
                value = AccessController.doPrivileged(new PrivilegedAction<String>() {
                    @Override
                    public String run() {
                        return System.getProperty(key);
                    }
                });
            }
        }catch (Exception e){
            if(logger.isWarnEnabled()){
                logger.warn("Unable to retrieve a system property'{}';default values will be userd,{}.",key,stackTrace(e));
            }
        }
        if (value == null){
            return def;
        }
        return value;
    }


    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?[0-9]+");


    public static int getInt(String key , int def){
        String value =get(key);
        if (value == null){
            return def;
        }

        value = value.trim().toLowerCase();
        if(INTEGER_PATTERN.matcher(value).matches()){
            try{
                return Integer.parseInt(value);
            }catch (Exception ignored){

            }
        }
        logger.warn("Unable to parse the Integer value'{}':{}-using the defalut value:{}",key,value,def);
        return def;
    }

    public static long getLong(String key , long def){
        String value = get(key);
        if(value == null){
            return def;
        }

        value = value.trim().toLowerCase();
        if(INTEGER_PATTERN.matcher(value).matches()){
            try{
                return Long.parseLong(value);
            }catch (Exception ignored){

            }
        }
        logger.warn("Unable to parse the Long value'{}':{}-using the defalut value:{}",key,value,def);
        return def;
    }

    /**
     * Returns the value of the Java system property with the specified {@param key},while falling back to
     * the specified default value if the property access fails.
     *
     * @param key
     * @param def
     * @return
     */
    public static boolean getBoolean(String key ,boolean def){
        String value = get(key);
        if(value == null){
            return def;
        }
        value = value.trim().toLowerCase();
        if(value.isEmpty()){
            return true;
        }
        if("true".equals(value) || "yes".equals(value) || "1".equals(value)){
            return true;
        }
        if("false" .equals(value) || "no".equals(value) || "0".equals(value)){
            return false;
        }
        logger.warn("Unable to parse the boolean value'{}':{}-using the defalut value:{}",key,value,def);
        return def;
    }

    /**
     * sets the {@code value}  of the Java system property with the specified {@code key}
     * @param key
     * @param value
     * @return
     */
    public static Object setProperty(String key ,String value){
        return System.getProperties().setProperty(key,value);
    }


    private SystemPropertyUtil(){}

}
