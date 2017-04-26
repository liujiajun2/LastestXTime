package com.jmu.xtime.update.update.Tool;

/*
      cojson@foxmail.com
 */

// 实现HttpPost请求
// Http 请求需要在独立的线程中调用，且需要有访问网络的权限

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

;

public class HttpTool {

    private int connectionTimeOut;

    public InputStream sendGet(String urlString) throws IOException {
        InputStream  result = null;
        int response = -1;
        HttpURLConnection httpURLConnection = initHttpConnection(urlString);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        response = httpURLConnection.getResponseCode();
        if(response == HttpURLConnection.HTTP_OK)
                result = httpURLConnection.getInputStream();
        return result;
    }

    public InputStream sendPost(String urlString, HashMap<String,String> data) throws IOException {
        InputStream  result = null;
        int response = -1;


        HttpURLConnection httpURLConnection = initHttpConnection(urlString);

        httpURLConnection.setDoOutput(true);//向服务器发送数据
        httpURLConnection.setUseCaches(false);//不使用缓存
        httpURLConnection.
                setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置提交类型
        byte[] temp = buildRequestData(data,"UTF-8").getBytes();
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(temp.length));

        //提交数据
        OutputStream submitData = httpURLConnection.getOutputStream();

        submitData.write(temp);
        response = httpURLConnection.getResponseCode();
        if(response == HttpURLConnection.HTTP_OK)
            result  = httpURLConnection.getInputStream();
        return result;
    }

    private String buildRequestData(HashMap<String,String> data,String encode) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean firstParam = true;
        for(Map.Entry<String,String> entry:data.entrySet()){
            if(firstParam){
                firstParam=false;
            }else{
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), encode));
        }
        return sb.toString();
    }

    private HttpURLConnection initHttpConnection(String urlString) throws IOException {
        //get post请求初始化
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
        httpURLConnection.setAllowUserInteraction(false);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setConnectTimeout(connectionTimeOut);
        return httpURLConnection;
    }


    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }
}
