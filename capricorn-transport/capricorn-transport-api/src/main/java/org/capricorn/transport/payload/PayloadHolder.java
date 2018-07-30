package org.capricorn.transport.payload;

import org.capricorn.serialization.io.InputBuf;
import org.capricorn.serialization.io.OutputBuf;

/**
 * 消息体 bytes/stream 载体，避免在IO线程中序列化/反序列化，jupiter-transport这层不关注消息体的对象结构
 * 在jupiter框架内只有一种情况下回导致不同线程对{@code PayloadHolder}读写，
 * 就是transport层decoder会将数据写进{@code PayloadHolder}封装到 {@code Runnable} 中提交到{@code Executor},
 * 但这并不会导致内存一致性相关问题，因为线程将{@code Runnable} 对象提交到{@code Executor}之前的操作 happen-before其执行开始；
 */
public abstract class PayloadHolder {

    private byte serializerCode;

    private byte[] bytes;

    private InputBuf inputBuf;
    private OutputBuf outputBuf;

    public byte serializerCode(){
        return serializerCode;
    }

    public byte[] bytes(){
        return bytes;
    }

    public void bytes(byte serializerCode,byte[] bytes){
        this.serializerCode = serializerCode;
        this.bytes = bytes;
    }

    public InputBuf inputBuf(){
        return inputBuf;
    }

    public void inputBuf(byte serializerCode , InputBuf inputBuf){
        this.serializerCode = serializerCode;
        this.inputBuf = inputBuf;
    }

    public OutputBuf outputBuf(){
        return outputBuf;
    }

    public void outputBuf(byte serializerCode,OutputBuf outputBuf){
        this.serializerCode = serializerCode;
        this.outputBuf = outputBuf;
    }

    //help gc
    public void clear(){
        bytes = null;
        inputBuf =null;
        outputBuf =null;
    }

    public int size(){
        return (bytes==null ?0 :bytes.length)
                +(inputBuf==null? 0:inputBuf.size())
                +(outputBuf==null ? 0:outputBuf.size());
    }


}
