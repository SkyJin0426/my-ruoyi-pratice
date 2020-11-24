package com.ruoyi.system.util;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class GZIPUtil {
    public static int BUFFER = 1024;
    public static String EXT = ".gz";

    /**
     * 对数据压缩，封装了参数为byte[]的方法
     * @param data
     * @return
     * @throws Exception
     */
    public static boolean compress(byte[] data, ByteArrayOutputStream baos) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        //压缩
        try {
            compress(bais, baos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            bais.close();
        }
    }

    /**
     * 数据压缩
     * @param is
     * @param os
     * @throws Exception
     */
    public static void compress(InputStream is, OutputStream os) throws Exception {
        GZIPOutputStream gos = new GZIPOutputStream(os);
        int count;
        byte data[] = new byte[BUFFER];
        while ((count = is.read(data, 0, BUFFER)) != -1) {
            gos.write(data, 0, count);
        }
        gos.finish();
        gos.flush();
        gos.close();
    }

    /**
     * 对数据解压缩，封装了参数为byte[]的方法
     * @param data
     * @return
     * @throws Exception
     */
    public static boolean decompress(byte[] data, ByteArrayOutputStream baos) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        // 解压缩
        try {
            decompress(bais, baos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            bais.close();
        }
    }

    /**
     * 数据解压缩
     * @param is
     * @param os
     * @throws Exception
     */
    public static void decompress(InputStream is, OutputStream os) throws Exception {
        GZIPInputStream gis = new GZIPInputStream(is);
        int count;
        byte data[] = new byte[BUFFER];
        while ((count = gis.read(data, 0, BUFFER)) != -1) {
            os.write(data, 0, count);
        }
        gis.close();
    }

    public static void main(String[] args) throws IOException {

        //压缩示例代码
        String str = "";
        for(int i = 0; i < 100; i++){
            str += "个地方官府官方 compress and decompress test;";
        }
        System.out.println("压缩前长度：" + str.length());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Boolean strZipReturn = compress(str.getBytes(), baos);
        if (false == strZipReturn) {
            System.out.println("压缩失败");
        }
        byte[] output = baos.toByteArray();
        baos.flush();
        baos.close();

        //压缩后的内容用base64编码
        String outputStr = Base64.encodeBase64String(output);
        System.out.println("压缩后的内容为：" + outputStr);
        System.out.println("压缩后的长度为: " + outputStr.length());

        //解压缩示例代码
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        //先用base64解码
        byte[] strZipByte = Base64.decodeBase64(outputStr);
        Boolean strReturn = GZIPUtil.decompress(strZipByte, baos2);
        if (false == strReturn) {
            System.out.println("解压缩失败");
        }
        byte[] data = baos2.toByteArray();
        baos2.flush();
        baos2.close();

        String strMessage = new String(data);
        System.out.println("解压缩后的内容为:" + strMessage);
    }
}

