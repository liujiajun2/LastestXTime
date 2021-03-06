package com.jmu.xtime.update.TaskManager;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by cojson on 2017/3/20.
 */

public class TaskInfomationManager {
    private static  HashMap<Long,HashMap<String,String>> map; //只保存当前正在运行的线程
    private static  HashMap<Long,HashMap<String,String>> databaseMap; //数据库缓存


    private DBTool dbTool;

    static{
        map = new HashMap<Long, HashMap<String, String>>();
        databaseMap = new HashMap<Long, HashMap<String, String>> ();
    }

    public TaskInfomationManager(Context context){
        dbTool = new DBTool(context);
        dbTool.open();
    }


    public HashMap<String,String> getTaskInformationByThreadId(Integer threadId){
        return map.get(threadId);
    }

    //从数据库中检索
    public  HashMap<String,String> getTaskInformationByTaskId(Long taskId){
        if(databaseMap.get(taskId)!=null)
             return  databaseMap.get(taskId);

         return dbTool.getTask(taskId);
    }

    //获取所有任务
    public HashMap<Integer,HashMap<String,String>>  getTasks(){
        return  dbTool.getTasks();
    }



    //添加任务到数据库返回taskId
    public long addTaskToDatabase(HashMap<String,String> map){
        long id =  dbTool.insertTask(map);
        databaseMap.put(id,map);
        return id;
    }

    //添加正在运行的任务到Map中同时也会添加到数据库中去,返回taskId
    public long addTask(long threadId,HashMap<String,String> taskMap){
        long id =  dbTool.insertTask(taskMap);
        databaseMap.put(id,taskMap);
        map.put(threadId,taskMap);
        return id;
    }

    //插入反馈
    public long insertFeedBack(String content){
        return  dbTool.insertFeedBack(content);
    }

    public long insertTaskStatus(long taskId){
        return  dbTool.insertTaskStatus(taskId,"no");
    }

    public String  getTaskStatus(long taskId){
        return  dbTool.getTaskStatus(taskId);
    }

    //如果当前数据库没有数据则返回 -1
    //如果有则返回 0-n （数据库主键从0开始自增长)
    public long getMaxTaskId(){
        return  dbTool.getMaxTaskId();
    }

    public boolean updateTaskStatus(long taskId,String status){
        return  dbTool.updateStatus(taskId,status);
    }

    public boolean deleteTaskStatus(long taskId){
        return  dbTool.deleteStatus(taskId);
    }

    public boolean deleteTask(long taskId){
        databaseMap.put(taskId,null);
        return  dbTool.deleteTask(taskId);
    }

    public boolean updateTask(long taskId,HashMap<String,String> taskMap){
        databaseMap.put(taskId,taskMap);
        return dbTool.updateTask(taskId,taskMap);
    }

    //创建用户 成功返回 > 1
    public long createUser(String userName,String password){
        return  dbTool.createUser(userName,password);
    }

    //用户名是否存在
    public boolean isUserNameExists(String userName){
        return dbTool.isUserNameExist(userName);
    }

    //登录 成功返回真 失败返回假
    public  boolean login(String  userName,String password){
        return  dbTool.login(userName,password);
    }

    public HashMap<Integer,String> getTasksStringData(){
        return dbTool.getTasksData();
    }

    public int getTheme(){
        return  dbTool.getTheme();
    }

    public boolean updateTheme(int tid){
        return  dbTool.updateTheme(tid);
    }
}
