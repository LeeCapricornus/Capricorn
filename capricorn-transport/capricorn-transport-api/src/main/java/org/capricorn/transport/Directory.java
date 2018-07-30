package org.capricorn.transport;

import org.capricorn.common.util.internal.StringBuilderHelper;

public abstract class Directory {


    private String directoryCache;

    /**
     * 服务所属组别
     * @return
     */
    public abstract String getGroup();

    /**
     * 服务名称
     * @return
     */
    public abstract String getServiceProviderName();

    /**
     * 服务版本号
     * @return
     */
    public abstract String getVersion();

    public String directoryString(){

        if(directoryCache !=null){
            return directoryCache;
        }
        StringBuilder buf = StringBuilderHelper.get();
        buf.append(getGroup())
                .append('_')
                .append(getServiceProviderName())
                .append('_')
                .append(getVersion());
        directoryCache=buf.toString();
        return directoryCache;
    }

    public void clear(){
        directoryCache = null;
    }
}
