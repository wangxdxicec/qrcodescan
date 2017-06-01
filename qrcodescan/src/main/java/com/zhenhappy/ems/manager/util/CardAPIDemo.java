package com.zhenhappy.ems.manager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by Administrator on 2017/5/19.
 */
public class CardAPIDemo {
    private static final String KEY = "c9d64ef6f4643699b7be5afd50f25661";
    private static final String URL = "http://op.juhe.cn/hanvon/bcard/query";
    private static final String PATH = "D:\\1.png";

    public static void main(String[] args) {
        System.out.println(invoke());
    }

    public static String invoke() {
        Map params = new HashMap();
        File file = new File(PATH);
        InputStream in;
        try {
            in = new FileInputStream(file);
            int i = in.available(); // 得到文件大小
            byte data[] = new byte[i];
            in.read(data); // 读数据
            in.close();
            params.put("key", KEY);
            Base64 base64 = new Base64(true);
            params.put("image", base64.encodeToString(data));
            return PureNetUtil.post(URL, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
