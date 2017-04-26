package com.jmu.xtime.update.service;

import android.renderscript.ScriptGroup;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

import com.jmu.xtime.update.TaskManager.DBTool;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;
import com.jmu.xtime.update.Tool.HttpTool;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by asus on 2017/3/21.
 */

public class UserService {

    private HttpTool httpTool;
    private static final int CONNECTION_TIME_OUT = 240;
    private static final String REMOTE_HOST = "http://tanshark.com/s/";
    private static final String LOGIN =REMOTE_HOST + "L";
    private static final String REGISTER = REMOTE_HOST + "R";
    private static final String UPLOAD = REMOTE_HOST + "T";
    private static final String DOWNLOAD = REMOTE_HOST + "D";

    public UserService(){
        httpTool = new HttpTool();
        httpTool.setConnectionTimeOut(CONNECTION_TIME_OUT);
    }

    public boolean login(String userName,String password)
            throws IOException {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("un",userName);
        map.put("pw",password);

        InputStream is = httpTool.sendPost(LOGIN,map);
        InputStreamReader isr = new InputStreamReader(is);

        int ch;
        StringBuilder sb = new StringBuilder();

        while( (ch = isr.read()) !=-1){
            sb.append((char) ch);
        }

        String str = sb.toString();

        // -1 参数不全，-2 用户名密码错误
        // -1 错误在本应用一般不会出现
        //  1 执行成功
        if(str.contains("-1") || str.contains("-2")){
            return false;
        }

        String id =  str.substring(0,str.indexOf("/"));
        String token = str.substring(str.indexOf("/")+1);
        TokenHolder.setToken(id,token);
        return true;
    }


    public boolean register(String userName,String password)
            throws IOException {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("un",userName);
        map.put("pw",password);

        InputStream is = httpTool.sendPost(REGISTER,map);
        InputStreamReader isr = new InputStreamReader(is);
        int ch;
        StringBuilder sb = new StringBuilder();

        while( (ch = isr.read()) !=-1){
            sb.append((char) ch);
        }
        String str = sb.toString();
        if(str.contains("-1") || str.contains("-2")){
            return false;
        }

        String id =  str.substring(0,str.indexOf("/"));
        String token = str.substring(str.indexOf("/")+1);
        TokenHolder.setToken(id,token);
        return true;
    }

    //需要任务管理对象
    public boolean upload(TaskInfomationManager taskInfomationManager){
        HashMap<Integer,String> tasks = taskInfomationManager.getTasksStringData();

        Iterator iterator =tasks.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer,String> entry = (Map.Entry<Integer, String>) iterator.next();
            Integer tid = entry.getKey();
            String data = entry.getValue();
            try {
                updateItem(tid.toString(),data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //向服务器提交数据
    public void updateItem(String tid,String taskData) throws IOException {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("token",TokenHolder.getId()+"/"+TokenHolder.getToken());
        Log.d("TOKEN",TokenHolder.getId()+"/"+TokenHolder.getToken());
        map.put("tid",tid);
        map.put("data", URLEncoder.encode(taskData,"utf-8"));
        httpTool.sendPost(UPLOAD,map);
    }

    //在数据库为空，而且用户登录的情况下开启此功能
    //{id$data}{}{}{}
    public void downloadItems(TaskInfomationManager taskInfomationManager) throws IOException {
        HashMap<String,String> map = new HashMap<String,String>();
        //map.put("token",TokenHolder.getId()+"/"+TokenHolder.getToken());
        map.put("uid",TokenHolder.getId());
        InputStream i = httpTool.sendPost(DOWNLOAD,map);
        InputStreamReader isr = new InputStreamReader(i);

        int ch ;
        StringBuilder sb = new StringBuilder();
        while( (ch = isr.read()) != -1){
            sb.append((char)ch);
        }
        String str = sb.toString();
        while(str.contains("{")){
            String temp = str.substring(str.indexOf("{")+1,str.indexOf("}"));
            String id = temp.substring(0,str.indexOf("$")-1);
            String taskData = temp.substring(str.indexOf("$"));
            str = str.substring(str.indexOf("}")+1);
            taskInfomationManager.addTaskToDatabase(DBTool.taskDataToTaskMap(str));
        }
    }
}
