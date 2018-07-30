package org.capricorn.transport;

import java.util.List;

/**
 * fork from jupiter
 * @author  capricorn
 */
public interface CConfig {
    /**
     * Return all set {@link COption}'s
     * @return
     */
    List<COption<?>> getOptions();

    /**
     * Return the value of the given {@link COption}
     * @param option
     * @param <T>
     * @return
     */
    <T> T getOption(COption<T> option);

    /**
     * Sets a configuration property with the specified name and value
     * Return {@code true} if and only if the property has been set
     * @param option
     * @param value
     * @param <T>
     * @return
     */
    <T> boolean setOption(COption<T> option,T value);
}
