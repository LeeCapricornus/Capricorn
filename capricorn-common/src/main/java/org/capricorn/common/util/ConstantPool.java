package org.capricorn.common.util;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import static org.capricorn.common.util.Preconditions.checkArgument;
import static org.capricorn.common.util.Preconditions.checkNotNull;

public abstract class ConstantPool<T extends Constant<T>> {

    private final ConcurrentMap<String,T> constants =Maps.newConcurrentMap();

    private final AtomicInteger nextId =new AtomicInteger(1);

    public T valueOf(Class<?> firstNameComponent ,String secondNameComponent){
        checkNotNull(firstNameComponent,"firstNameComponent");
        checkNotNull(secondNameComponent ,"secondNameComponent");
        return valueOf(firstNameComponent.getName()+'#'+secondNameComponent);
    }


    public T valueOf(String name){
        checkArgument(!Strings.isNullOrEmpty(name),"empty name");
        return getOrCreate(name);
    }

    /**
     * Get existing constant by name or creates new one if not exists.Threadsafe.
     * @param name
     * @return
     */
    private T getOrCreate(String name){
        T constant = constants.get(name);
        if(constant ==null){
            final T newConstant = newConstant(nextId.getAndIncrement(),name);
            //If the specified key is not already associated with a value,associate it with the given value.
            //This is equivalent to
            // <pre> {@code
            // if (map.containsKey(key))
            //  return map.put(key,value);
            // else
            //  return map.get(key);
            //}
            // except that the action is performed atomically.
            constant = constants.putIfAbsent(name ,newConstant);
            if(constant == null){
                constant =newConstant;
            }
        }
        return constant;
    }

    /**
     * Returns {@code true} if exits for the given {@param name}
     * @param name
     * @return
     */
    public boolean exists(String name){
        checkArgument(!Strings.isNullOrEmpty(name),"empty name");
        return constants.containsKey(name);
    }

    public T newInstance(String name){
        checkArgument(!Strings.isNullOrEmpty(name),"empty name");
        return createOrThrow(name);
    }

    /**
     * Creates constant by name or throws exception.Threadsafe
     * @param name
     * @return
     */
    private T createOrThrow(String name){
        T constant = constants.get(name);
        if(constant ==null){
            final  T newConstant =newConstant(nextId.getAndIncrement(),name);
            constant =constants.putIfAbsent(name,newConstant);
            if( constant == null){
                return newConstant;
            }
        }
        throw new IllegalArgumentException(String .format("'%s' is already in use",name));
    }

    protected abstract T newConstant(int id ,String name);
}
