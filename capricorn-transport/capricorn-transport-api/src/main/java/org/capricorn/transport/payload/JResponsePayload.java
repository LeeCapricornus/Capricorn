package org.capricorn.transport.payload;

/**
 * 响应消息体 bytes/stream 载体，避免在IO线程中序列化/反序列化，jupiter-transport 这层不关注消息体的对象结构。
 *
 * @author  ljl
 */
public class JResponsePayload extends PayloadHolder {
    //用于映射<id,request,response> 三元组
    private final long id;

    private byte status;

    public JResponsePayload(long id){
        this.id =id;
    }

    public long id(){
        return id;
    }

    public byte status(){
        return status;
    }

    public void status(byte status){
        this.status =status;
    }

}
