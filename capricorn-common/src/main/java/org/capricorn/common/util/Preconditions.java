package org.capricorn.common.util;

public final class Preconditions {

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null;
     * @param reference
     * @param <T>
     * @throws NullPointerException
     * @return
     */
    public static <T> T checkNotNull(T reference){
        if(reference == null ){
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     *
     * @param reference
     * @param errorMessage the exception message to use if the check fails;
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference ,Object errorMessage){
        if(reference == null){
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * Ensures the truth of an expression involving one or more parameter to the calling method
     * @param expression
     */
    public static void checkArgument(boolean expression){
        if(!expression){
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression ,Object errorMessage){
        if(!expression){
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }

    }

    private Preconditions(){}




}
