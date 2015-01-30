package com.camel.utils;

import java.util.ArrayList;

/**
 * byte相关操作utils
 * 
 * @author dengqb
 * @date 2014年12月17日
 */
public class ByteUtils {
    /**
     * 将byte字节存储在arrayList中，解决动态byte[]数组长度问题
     * @param arrayList
     * @param byt
     * @return
     */
    public static ArrayList addByte(ArrayList arrayList, byte byt) {
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        arrayList.add(Byte.valueOf(byt));
        return arrayList;
    }

    /**
     * 从list中获取byte[]数组
     * @param arrayList
     * @return
     */
    public static byte[] getBytesFromArrayList(ArrayList arrayList) {
        byte[] bytes = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            bytes[i] = ((Byte) arrayList.get(i)).byteValue();
        }
        return bytes;
    }
}
