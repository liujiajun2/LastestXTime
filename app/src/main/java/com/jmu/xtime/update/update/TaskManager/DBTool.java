package com.jmu.xtime.update.update.TaskManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jmu.xtime.update.Task.BasicTaskInfomation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by asus on 2017/3/21.
 */

public class DBTool {
        static final String KEY_ROWID = "_id";
        static final String KEY_TASKDATA = "taskdata";
        static final String TAG = "DBTool";

        static final String DATABASE_NAME = "db";
        static final String DATABASE_TABLE = "tasks";
        static final int DATABASE_VERSION = 1;

        static final String SEPARATOR = "=[e_,+e]=";
        static final String NEXT_DATA = "{]za_ak[}";

        static final String DATABASE_CREATE = "CREATE TABLE tasks" +
                "(_id integer primary key autoincrement,taskdata text not null);" ;

        static int id;

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
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //do nothing
            }
        }

        public com.jmu.xtime.update.update.TaskManager.DBTool open(){
            db = databaseHelper.getWritableDatabase();
            return this;
        }

        public void close(){
            databaseHelper.close();
        }

        public long insertTask(HashMap<String,String> map){
            ContentValues cv = new ContentValues();
            cv.put(KEY_TASKDATA,taskMapToTaskData(map));
            return db.insert(DATABASE_TABLE,null,cv);
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
                    new String[]{KEY_ROWID,KEY_TASKDATA},null,null,null,null,null);

            HashMap<Integer,HashMap<String,String>>  tasks = new  HashMap<Integer,HashMap<String,String>>();

            while(cursor.moveToNext()){
                HashMap<String,String> map = taskDataToTaskMap(cursor.getString(1));
                map.put(BasicTaskInfomation.TASK_ID,cursor.getString(0));
                tasks.put(cursor.getInt(0),map);
            }
            return tasks;
        }

    public HashMap<Integer,String> getTasksData(){
        HashMap<Integer,String> map = new HashMap<Integer, String>();
        Cursor cursor = db.query(DATABASE_TABLE,
                new String[]{KEY_ROWID,KEY_TASKDATA},null,null,null,null,null);
        while (cursor.moveToNext()){
            map.put(cursor.getInt(0),cursor.getString(1));
            Log.d("H:",cursor.getInt(0)+"->"+cursor.getString(1));
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
