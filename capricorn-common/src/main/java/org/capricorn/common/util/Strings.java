package org.capricorn.common.util;

import java.util.ArrayList;
import java.util.List;

public final class Strings {

    /**
     * Return s the given string if it is non-null;the empty string otherwise.
     * @param string
     * @return
     */
    public static String nullToEmpty(String string){
        return (string == null ) ? "":string;
    }

    /**
     * Returns the given string if it is nonempty; {@code null} otherwise.
     * @param string
     * @return
     */
    public static String emptyToNull(String string){
        return isNullOrEmpty(string) ? null : string ;
    }

    /**
     * Returns true if the given string is null or is empty
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str){
        return str == null || str.length()==0;
    }

    /**
     * Checks if a string is whitespace ,empty("") or null;
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        int strLen;
        if(str !=null && (strLen = str.length())!=0){
            for(int i=0;i<strLen;i++){
                if(!Character.isWhitespace(str.charAt(i))){
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Checks if a string is not empty(""),not null and not whitespace only.
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }

    /**
     * Splits the provided text into an array ,separator specified.
     *
     * A null input String returns null.
     *
     * Strings.split("a..b.c",'.') = ["a","b","c"]
     * @param str
     * @param separator
     * @return
     */
    public static String[] split(String str ,char separator){
        return split(str,separator,false);
    }

    /**
     * Splits the provided text into an array,separator specified.
     * Strings.split("a..b.c", '.', true)   = ["a", "", "b", "c"]
     * @param str
     * @param separator
     * @param preserveAllTokens
     * @return
     */
    public static String[] split(String str ,char separator ,boolean preserveAllTokens){
        if(str == null){
            return null;
        }
        int len = str.length();
        if(len == 0){
            return EMPTY_STRING_ARRAY;
        }
        List<String> list = new ArrayList<>();
        int i=0,start =0;
        boolean match =false;
        while(i< len){
            if(str.charAt(i) == separator){
                if(match || preserveAllTokens){
                    list.add(str.substring(start,i));
                    match =false;
                }
                start = ++i;
                continue;
            }
            match =true;
            i++;
        }
        if(match || preserveAllTokens){
            list.add(str.substring(start,i));
        }
        return list.toArray(new String[list.size()]);
    }

    private static final String[] EMPTY_STRING_ARRAY=new String[0];

    private Strings(){}


}
