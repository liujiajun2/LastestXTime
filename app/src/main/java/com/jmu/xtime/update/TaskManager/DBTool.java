package com.jmu.xtime.update.TaskManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.Log;

import com.jmu.xtime.update.Task.BasicTaskInfomation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/3/21.
 */

public class DBTool {
        static final String KEY_ROWID = "_id";
        static final String KEY_TASKDATA = "taskdata";
        static final String KEY_OID = "oid";
        static final String TAG = "DBTool";
        static final String KEY_UID ="uid";
        static final String KEY_NAME ="name";
        static final String KEY_PWD ="pwd";

        static final String DATABASE_NAME = "db";
        static final String DATABASE_TABLE = "tasks";
        static final String USER_TABLE = "users";
        static final int DATABASE_VERSION = 1;

        static final String SEPARATOR = "=[e_,+e]=";
        static final String NEXT_DATA = "{]za_ak[}";

        static final String DATABASE_CREATE = "CREATE TABLE tasks" +
                "(_id integer primary key autoincrement,oid integer,taskdata text not null);" ;
        static final  String DATABASE_CREATE_USER_TABLE = "CREATE TABLE users"+
                "(uid integer primary key autoincrement,name varchar(36) not null,pwd varchar(36) not null)";
        static  int nowUser =  -1;

        static final String DATABASE_THEME="CREATE TABLE theme(id integer primary key autoincrement ,tid integer not null)";

        static final String DATABASE_FEEDBACK ="CREATE TABLE feedback(id integer primary key autoincrement,content text not null)";

        static final String DATABASE_TASKSTATUS = "CREATE TABLE status(id integer not null,status varchar(10) not null)";

        final Context context;
        DatabaseHelper databaseHelper;
        SQLiteDatabase  db;

        public  DBTool(Context context){
                this.context = context;
                databaseHelper = new DatabaseHelper(context);
        }

        private static class DatabaseHelper extends SQLiteOpenHelper{

            DatabaseHelper(Context context){
                super(context,DATABASE_NAME,null,DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                try{
                    db.execSQL(DATABASE_CREATE);
                    db.execSQL(DATABASE_CREATE_USER_TABLE);
                    db.execSQL(DATABASE_THEME);
                    db.execSQL(DATABASE_FEEDBACK);
                    db.execSQL(DATABASE_TASKSTATUS);
                    db.execSQL("insert into  theme(tid) values(1)");
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //do nothing
            }
        }

        public DBTool open(){
            db = databaseHelper.getWritableDatabase();
            return this;
        }

        public void close(){
            databaseHelper.close();
        }

        public long insertFeedBack(String content){
            ContentValues cv = new ContentValues();
            cv.put("content",content);
            return db.insert("feedback",null,cv);
        }

        public long insertTaskStatus(long taskid,String status){
            ContentValues cv = new ContentValues();
            cv.put("id",taskid);
            cv.put("status",status);
            return db.insert("status",null,cv);
        }

        public  long getMaxTaskId(){
            Cursor cursor = db.query(true,DATABASE_TABLE,new String[]{"max(_id)"},null,null,null,null,null,null);
            if(cursor.moveToFirst()){
                return  cursor.getInt(0);
            }else {
                return -1;
            }
        }

        public String getTaskStatus(long taskid){
            Cursor cursor = db.query(true,"status",new String[]{"status"},"id="+taskid,null,null,null,null,null);
            if(cursor.moveToFirst()){
                return  cursor.getString(0);
            }else {
                return  null;
            }
        }

        public boolean updateStatus(long id,String status){
            ContentValues cv = new ContentValues();
            cv.put("status",status);
            return db.update("status",cv,"id="+id,null) > 0;
        }

        public boolean deleteStatus(long id){
            return  db.delete("status","id="+id,null) > 0;
        }


        public long insertTask(HashMap<String,String> map){
            ContentValues cv = new ContentValues();
            cv.put(KEY_TASKDATA,taskMapToTaskData(map));
            cv.put(KEY_OID,nowUser);
            return db.insert(DATABASE_TABLE,null,cv);
        }

        public long createUser(String userName,String pwd){
            ContentValues  cv = new ContentValues();
            cv.put(KEY_NAME,userName);
            cv.put(KEY_PWD,pwd);
            return db.insert(USER_TABLE,null,cv);
        }

        public  int getTheme(){
            Cursor cursor = db.query(true,"THEME",new String[]{"tid"},"id=1",null,null,null,null,null);
            if(cursor.moveToFirst()){
                return  cursor.getInt(0);
            }else{
                return  -1;
            }
        }

        public boolean updateTheme(int tid){
           ContentValues cv = new ContentValues();
            cv.put("tid",tid);
            return  db.update("THEME",cv,"id=1",null) > 0;
        }

        public boolean isUserNameExist(String userName){
            Cursor cursor = db.query(true,USER_TABLE,new String[]{KEY_UID},KEY_NAME+"=?",new String[]{userName},null,null,null,null);
            if(cursor != null){
                if( cursor.moveToFirst()){
                    Integer uid = cursor.getInt(0);
                    if(uid != null) {
                        return true;
                    }else{
                        return  false;
                    }
                }else{
                    return  false;
                }
            }else{
                return  false;
            }
        }

        public boolean login(String userName,String pwd){
            Cursor cursor  = db.
                    query(true,USER_TABLE,new String[]{KEY_UID},KEY_NAME+"=? AND " +KEY_PWD +"=?",new String[]{userName,pwd},null,null,null,null);
            if(cursor == null){
                return false;
            }
            if( cursor.moveToFirst()){
                Integer uid = cursor.getInt(0);
                if(uid != null) {
                    nowUser = uid;
                    return  true;
                }else{
                    return  false;
                }
            }else {
                return false;
            }
        }

        public boolean deleteTask(long rowId){
            return db.delete(DATABASE_TABLE,KEY_ROWID+"="+rowId,null) > 0;
        }

        public boolean updateTask(long rowId,HashMap<String,String> map){
            ContentValues cv = new ContentValues();
            cv.put(KEY_TASKDATA,taskMapToTaskData(map));

            return db.update(DATABASE_TABLE,cv,KEY_ROWID +"="+ rowId,null) > 0;
        }

        public HashMap<String,String> getTask(long rowId){
            Cursor cursor =
                    db.query(true,DATABASE_TABLE,
                            new String[]{KEY_ROWID,KEY_TASKDATA},
                            KEY_ROWID+"="+rowId,null,null,null,null,null);
            if(cursor != null){
                cursor.moveToFirst();
            }
            HashMap<String,String> map = taskDataToTaskMap(cursor.getString(1));
            map.put(BasicTaskInfomation.TASK_ID,cursor.getString(0));
            return map;
        }

        public HashMap<Integer,HashMap<String,String>> getTasks(){
            Cursor cursor = db.query(DATABASE_TABLE,
                    new String[]{KEY_ROWID,KEY_OID,KEY_TASKDATA},null,null,null,null,null);

            HashMap<Integer,HashMap<String,String>>  tasks = new  HashMap<Integer,HashMap<String,String>>();

            while(cursor.moveToNext()){
                if(cursor.getInt(1) == nowUser) {
                    HashMap<String, String> map = taskDataToTaskMap(cursor.getString(2));
                    map.put(BasicTaskInfomation.TASK_ID, cursor.getString(0));
                    tasks.put(cursor.getInt(0), map);
                }
            }
            return tasks;
        }

    public HashMap<Integer,String> getTasksData(){
        HashMap<Integer,String> map = new HashMap<Integer, String>();
        Cursor cursor = db.query(DATABASE_TABLE,
                new String[]{KEY_ROWID,KEY_OID,KEY_TASKDATA},null,null,null,null,null);
        while (cursor.moveToNext()){
            if(cursor.getInt(1) == nowUser) {
                map.put(cursor.getInt(0), cursor.getString(2));
                Log.d("H:", cursor.getInt(0) + "->" + cursor.getString(1));
            }
        }
        return map;
    }

        //将哈希Map转化为TaskData
        public static String taskMapToTaskData(HashMap<String,String> map){
            StringBuilder sb  = new StringBuilder();
            Iterator iterator = map.entrySet().iterator();

            while (iterator.hasNext()){
                Map.Entry<String,String> entry = (Map.Entry<String, String>) iterator.next();
                String key  = entry.getKey();
                String val = entry.getValue();
                sb.append(key);
                sb.append(SEPARATOR);
                sb.append(val);
                sb.append(NEXT_DATA);
            }
            Log.d(TAG, "taskMapToTaskData: "+sb.toString());
            return sb.toString();
        }

       public static HashMap<String,String> taskDataToTaskMap(String data){
            HashMap<String,String> map = new HashMap<String,String>();

            int index = -1;
            try{
                while(data.indexOf(NEXT_DATA)!=-1 && data.length() != NEXT_DATA.length()){
                    index = data.indexOf(SEPARATOR);
                    String key  = data.substring(0,index);
                    String val  = data.substring(index+SEPARATOR.length(),data.indexOf(NEXT_DATA));
                    Log.d(TAG, key + "->"+ val);

                    map.put(key,val);
                    data = data.substring(data.indexOf(NEXT_DATA)+NEXT_DATA.length());
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return map;
        }




}
