package com.ylq.library.common;

/**
 * Created by apple on 16/7/20.
 */
public class Copy {
    public static byte[] copyByteArray(byte[] bytes){
        if(bytes==null)
            return null;
        byte[] n = new byte[bytes.length];
        for(int i=0;i<bytes.length;i++)
            n[i] = bytes[i];
        return n;
    }

    public static int[] copyIntArray(int[] data) {
        if(data==null)
            return null;
        int[] n = new int[data.length];
        for(int i=0;i<data.length;i++)
            n[i] = data[i];
        return n;
    }
}
