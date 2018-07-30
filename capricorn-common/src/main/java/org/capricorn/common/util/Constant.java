package org.capricorn.common.util;

public interface Constant<T extends Constant<T>> extends Comparable<T> {
    /**
     * Returns the unique number assigned to this{@link Constant}
     */
    int id();

    /**
     *
     * Returns the name of this{@link Constant}
     */
    String name();


}
