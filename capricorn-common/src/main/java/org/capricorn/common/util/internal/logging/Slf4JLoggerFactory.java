package org.capricorn.common.util.internal.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Slf4JLoggerFactory extends InternalLoggerFactory{

    public Slf4JLoggerFactory(){}

    Slf4JLoggerFactory(boolean failIfNOP){
        assert failIfNOP; //should be always called with true.

        /*SFLF4J writes it error messages to System.err.
        Capture them so that the user doesn't see such a message on the
        console during automatic detection*/
        final StringBuffer buf = new StringBuffer();
        final PrintStream err =System.err;
        try{
            System.setErr( new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    buf.append((char) b);
                }
            },true,"US-ASCII"));
        }catch (UnsupportedEncodingException e){
            throw new Error(e);
        }

        try{
            if(LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory){
                throw new NoClassDefFoundError(buf.toString());
            }else{
                err.print(buf);
                err.flush();
            }
        }finally {
            System.setErr(err);
        }
    }
    @Override
    protected InternalLogger newInstance(String name) {
        return new Slf4JLogger(LoggerFactory.getLogger(name));
    }
}
