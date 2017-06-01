package com.zhenhappy.ems.manager.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by Administrator on 2017/5/19.
 */
public class PureNetUtil {
    /**
     * get方法直接调用post方法
     *
     * @param url 网络地址
     * @return 返回网络数据
     */
    public static String get(String url) {
        return post(url, null);
    }

    public static String get(String url, String charset) {
        return post(url, null, charset);
    }

    /**
     * 设定post方法获取网络资源,如果参数为null,实际上设定为get方法
     *
     * @param url   网络地址
     * @param param 请求参数键值对
     * @return 返回读取数据
     */
    public static String post(String url, Map param) {
        return post(url, param, null);
    }

    public static String post(String url, Map<String, Object> param, String outCharset) {
        if (outCharset == null) {
            outCharset = "UTF-8";
        }
        HttpURLConnection conn = null;
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
            StringBuffer sb = null;
            if (param != null) {//如果请求参数不为空
                sb = new StringBuffer();
                /*A URL connection can be used for input and/or output.  Set the DoOutput
                 * flag to true if you intend to use the URL connection for output,
                 * false if not.  The default is false.*/
                //默认为false,post方法需要写入参数,设定true
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                //设定post方法,默认get
                //获得输出流
                OutputStream out = conn.getOutputStream();
                //对输出流封装成高级输出流
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                //将参数封装成键值对的形式
                for (Map.Entry s : param.entrySet()) {
                    sb.append(s.getKey()).append("=").append(s.getValue()).append("&");
                }
                writer.write(sb.deleteCharAt(sb.toString().length() - 1).toString());
                writer.close();//如果忘记关闭输出流将造成参数未完全写入的情况
                sb = null;
            }
            conn.connect();//建立连接
            sb = new StringBuffer();
            //获取连接状态码
            int recode = conn.getResponseCode();
            BufferedReader reader = null;
            if (recode == 404) {
                System.out.println("404===>" + url);
            }
            if (recode == 200) {
                //Returns an input stream that reads from this open connection
                //从连接中获取输入流
                InputStream in = conn.getInputStream();
                String encoding = conn.getContentEncoding();
                if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
                    GZIPInputStream gis = new GZIPInputStream(in);
                    reader = new BufferedReader(new InputStreamReader(gis, outCharset));
                    for (String str = reader.readLine(); str != null; str = reader.readLine()) {
                        sb.append(str).append(System.getProperty("line.separator"));//原网页的换行加上
                    }
                } else {
                    reader = new BufferedReader(new InputStreamReader(in, outCharset));
                    for (String str = reader.readLine(); str != null; str = reader.readLine()) {
                        sb.append(str).append(System.getProperty("line.separator"));//原网页的换行加上
                    }
                }
                //关闭输入流
                reader.close();
                if (sb.toString().length() == 0) {
                    return null;
                }
                return sb.toString().substring(0,
                        sb.toString().length() - System.getProperty("line.separator").length());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null)//关闭连接
                conn.disconnect();
        }
        return null;
    }
}
