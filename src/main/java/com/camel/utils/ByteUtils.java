package com.camel.utils;

import java.util.ArrayList;

/**
 * byte相关操作utils
 * 
 * @author dengqb
 * @date 2014年12月17日
 */
public class ByteUtils {
    public static ArrayList addByte(ArrayList arrayList, byte byt) {
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        arrayList.add(Byte.valueOf(byt));
        return arrayList;
    }

    public static byte[] getBytesFromArrayList(ArrayList arrayList) {
        byte[] bytes = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            bytes[i] = ((Byte) arrayList.get(i)).byteValue();
        }
        return bytes;
    }
}
