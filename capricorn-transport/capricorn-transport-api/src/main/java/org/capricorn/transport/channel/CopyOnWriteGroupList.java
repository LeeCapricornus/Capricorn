package org.capricorn.transport.channel;

import org.capricorn.common.util.internal.unsafe.UnsafeUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 相同服务，不同服务节点的Channel group 容器
 * 线程安全（写时复制），实现原理类似{@link java.util.concurrent.CopyOnWriteArrayList}
 * update 操作支持addIfAbsent/remove,update操作会同时更新对应服务节点(group)的引用计数
 */
public class CopyOnWriteGroupList {

    private static final CChannelGroup[] EMPTY_GROUP = new CChannelGroup[0];
    private static final Object[] EMPTY_ARRAY =new Object[]{EMPTY_GROUP,null};

    private transient  final ReentrantLock lock =new ReentrantLock();

    private final DirectoryCChanelGroup parent;
    private transient  volatile Object[] array;

    public CopyOnWriteGroupList(DirectoryCChanelGroup parent){
        this.parent = parent;
        setArray(EMPTY_ARRAY);
    }

    public final CChannelGroup[] getSnapshot(){
        return tabAt0(array);
    }

    public final Object getWeightArray(CChannelGroup[] snapshot,String directory){
        Object[] array =this.array;
        return tabAt0(array)!=snapshot
                ? null
                :(tabAt1(array) == null ? null :tabAt1(array).get(directory));

    }

    public final boolean setWeightArray(CChannelGroup[] snapshot,String directory,Object weightArray){
        if(weightArray == null || snapshot != tabAt0(array)){
            return false;
        }
        final ReentrantLock lock =this.lock;
        boolean locked =lock.tryLock();
        if(locked){// give up if there is cometiton
            try{
                if(snapshot != tabAt0(array)){
                    return false;
                }
                setWeightArray(directory,weightArray);
                return true;
            }finally {
                lock.unlock();
            }
        }
        return false;
    }


    private void setArray(Object[] array){this.array= array;}

    private void setArray(CChannelGroup[] groups,Object weightArray){
        array= new Object[]{groups,weightArray};
    }

    private void setWeightArray(String directory,Object weightArray){
        Map<String,Object> weightsMap = tabAt1(array);
        if(weightsMap == null){
            weightsMap = new HashMap<>();
            setTabAt(array,1,weightArray);
        }
        weightsMap.put(directory,weightArray);
    }

    public int size(){
        return tabAt0(array).length;
    }

    public boolean isEmpty(){
        return size()== 0;
    }

    public boolean contains(CChannelGroup group){
        CChannelGroup[] elements =tabAt0(array);
        return indexOf(group,elements,0,elements.length)>=0;
    }

    public int indexOf(CChannelGroup o){
        CChannelGroup[] elements =tabAt0(array);
        return indexOf(o,elements,0,elements.length);
    }

    public int indexOf(CChannelGroup o ,int index){
        CChannelGroup[] elements =tabAt0(array);
        return indexOf(o,elements,index,elements.length);
    }

    public CChannelGroup[] toArray(){
        CChannelGroup[] elements =tabAt0(array);
        return Arrays.copyOf(elements,elements.length);
    }

    public CChannelGroup get(int index){
        return get(tabAt0(array),index);
    }
    private CChannelGroup get(CChannelGroup[] array,int index){
        return array[index];
    }

    public boolean remove(CChannelGroup o){
        CChannelGroup[] snapshot = tabAt0(array);
        int index = indexOf(o,snapshot,0,snapshot.length);
        return (index>=0) &&remove(o,snapshot,index);
    }

    private boolean remove(CChannelGroup o,CChannelGroup[] snapshot,int index){
        final ReentrantLock lock = this.lock;
        lock.lock();
        try{
            CChannelGroup[] current = tabAt0(array);
            int len = current.length;
            if(snapshot !=current) findIndex:{
                int prefix = Math.min(index,len);
                for(int i=0;i<prefix;i++){
                    if(current[i] == snapshot[i] && eq(o,current[i])){
                        index = i;
                        break findIndex;
                    }
                }
                if(index >=len){
                    return false;
                }
                if(current[index] == o){
                    break findIndex;
                }
                index = indexOf(o ,current,index,len);
                if(index <0){
                    return false;
                }
            }
            CChannelGroup[] newElements = new CChannelGroup[len-1];
            System.arraycopy(current,0,newElements,0,index);
            System.arraycopy(current,index+1,newElements,index,len-index-1);
            setArray(newElements,null);
            //parent.decrementRefCount(0);
            return true;

        }finally {
            lock.unlock();
        }
    }

    public boolean addIfAbsent(CChannelGroup o){
        CChannelGroup[] snapshot =tabAt0(array);
        return indexOf(o,snapshot,0,snapshot.length)<0 && addIfAbsent(o,snapshot);
    }

    private boolean addIfAbsent(CChannelGroup o,CChannelGroup[] snapshot){
        final ReentrantLock lock = this.lock;
        lock.lock();
        try{
            CChannelGroup[] current = tabAt0(array);
            int len = current.length;
            if(snapshot != current){
                int common = Math.min(snapshot.length,len);
                for(int i =0;i<common;i++){
                    if(current[i] != snapshot[i] && eq(o,current[i])){
                        return false;
                    }
                }
                if(indexOf(o,current,common,len)>=0){
                    return false;
                }
            }
            CChannelGroup[] newElements = Arrays.copyOf(current,len+1);
            newElements[len]=o;
            setArray(newElements,null);
            //parent.decrementRefCount(0);
            return true;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public String toString(){
        return Arrays.toString(tabAt0(array));
    }

    public boolean equals(Object o){
        if(o== this){
            return true;
        }
        if(!(o instanceof CopyOnWriteGroupList)){
            return false;
        }
        CopyOnWriteGroupList other =(CopyOnWriteGroupList)(o);
        CChannelGroup[] elements = tabAt0(array);
        CChannelGroup[] otherElements = tabAt0(other.array);
        int len = elements.length;
        int otherLen = otherElements.length;
        if(len != otherLen){
            return false;
        }
        for(int i= 0;i<len;i++){
            if(!eq(elements[i],otherElements[i])){
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("all")
    @Override
    public int hashCode() {
        int hashCode = 1;
        CChannelGroup[] elements = tabAt0(array);
        for (int i = 0, len = elements.length; i < len; i++) {
            CChannelGroup o = elements[i];
            hashCode = 31 * hashCode + (o == null ? 0 : o.hashCode());
        }
        return hashCode;
    }

    private boolean eq(CChannelGroup o1,CChannelGroup o2){
        return (o1==null ? o2==null :o1.equals(o2));
    }



    private int indexOf(CChannelGroup o, CChannelGroup[] elements,int index,int fence){
        if(o== null){
            for(int i=index;i<fence;i++){
                if(elements[i]==null){
                    return i;
                }
            }
        }else{
            for(int i= index;i<fence;i++){
                if(o.equals(elements[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    private static CChannelGroup[] tabAt0(Object[] array){
        return (CChannelGroup[]) tabAt(array,0);
    }
    private static Map<String,Object> tabAt1(Object[] array){
        return (Map<String,Object>)tabAt(array,1);
    }

    private static Object tabAt(Object[] array,int index){
        //Mark
        return UnsafeUtil.getObjectVolatile(array,index);
    }

    private static void setTabAt(Object[] array,int index,Object value){
        UnsafeUtil.putObjectVolatile(array,index,value);
    }


}
