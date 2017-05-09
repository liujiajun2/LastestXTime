package com.jmu.xtime;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jmu.xtime.functionservers.XTFunctionsList.Controller.XTFunctionsListController;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.String;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 倾城一世 on 2017/3/9.
 */

public class TaskFragment extends Fragment {

    private String context;
    private TextView mTextView;
    private TextView login_newuser;
    private Intent intent;
    private ListView listView;
    private MyselfMessageAdapter functionAdapt;
    private List<MyselfMessageInfo> myDatalist;


    private TaskInfomationManager taskInfomationManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment,container,false);

        listView = (ListView)view.findViewById(R.id.listview_task);
        myDatalist = getMyDatalist();
        functionAdapt = new MyselfMessageAdapter(myDatalist,getActivity());
        listView.setAdapter(functionAdapt);
        listView.setOnItemLongClickListener(onItemLongClick);
        listView.setOnItemClickListener(onItemClick);
        initEmpty();
        return view;
    }
    AdapterView.OnItemLongClickListener onItemLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, final long id) {
            Log.i("kkk","长按");
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setTitle("温馨提示");
            builder.setMessage("确定删除?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                      Log.i("kkk",String.valueOf(position));
//                    Log.i("kkk",String.valueOf(id));
//                     String info= (String)view.getTag();
//                    Log.i("kkk",info);
                     //获取taskId在删除数据库中的值
                    TextView  text=(TextView)view.findViewById(R.id.text_login);
                    String textContent = text.getText().toString();
                    String result=textContent.substring(0,2);
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(result);
                    m.find();
                    String taskId = m.group();
                    int tid = Integer.parseInt(taskId);
                    taskInfomationManager.deleteTask((long)tid);
                    taskInfomationManager.updateTaskStatus((long)tid,"yes");
                    //移除item
                    myDatalist.remove(position);
                    functionAdapt.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("kkk","取消了删除按钮");
                }
            });
            builder.create().show();
            return true;
        }
    };
   AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
       @Override
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           TextView  text=(TextView)view.findViewById(R.id.text_login);
           String textContent = text.getText().toString();
           String result=textContent.substring(0,2);
           Pattern p = Pattern.compile("\\d+");
           Matcher m = p.matcher(result);
           m.find();
           String taskId = m.group();
           //int tid = Integer.parseInt(taskId);
           Long tid = Long.parseLong(taskId);
           System.out.println("tid---->OnItemClickListener-->"+tid);
           Intent intent = new Intent();
           intent.putExtra("taskId",tid);
           intent.setClass(getActivity(),TaskDetail.class);
           startActivity(intent);
       }
   };
    public List<MyselfMessageInfo> getMyDatalist(){
        List<MyselfMessageInfo> listItem = new ArrayList<MyselfMessageInfo>();

        taskInfomationManager = new TaskInfomationManager(this.getActivity().getBaseContext());
        Map<Integer, HashMap<String, String>> map2 = taskInfomationManager.getTasks();
        if(map2!=null) {
            Iterator iterator = map2.entrySet().iterator();
            for(int i = 0 ; i <=taskInfomationManager.getMaxTaskId() ; i++){
                HashMap<String,String> map1 = map2.get(i);
                if (map1 != null){
                    if(map1.get("title").equals("发送短信")){
                        int id = Integer.parseInt(map1.get("taskId"));
                        String message = id+map1.get("title")+"     "+map1.get("time");
                        MyselfMessageInfo dataMessage = new MyselfMessageInfo(message,getActivity().getResources().getDrawable(R.drawable.xt_functions_send_message));
                        listItem.add(dataMessage);

//                    listItem.set(id,dataMessage);

                    } else if(map1.get("title").equals("发送GPS")){
                        int id = Integer.parseInt(map1.get("taskId"));
                        String message = id+map1.get("title")+"     "+map1.get("time");
                        MyselfMessageInfo dataMessage = new MyselfMessageInfo(message,getActivity().getResources().getDrawable(R.drawable.xt_functinos_gps));
                        listItem.add(dataMessage);
                    }else if(map1.get("title").equals("定时摄像")){
                        int id = Integer.parseInt(map1.get("taskId"));
                        String message = id+map1.get("title")+"     "+map1.get("time");
                        MyselfMessageInfo dataMessage = new MyselfMessageInfo(message,getActivity().getResources().getDrawable(R.drawable.timg1));
                        listItem.add(dataMessage);
                    }else{
                    }
                }
            }
//            while (iterator.hasNext()) {
//                Map.Entry entry = (Map.Entry) iterator.next();
//                Object key = entry.getKey();
//                Object val = entry.getValue();
//                //   System.out.println(((Integer) key + " "));
//                Map<String, String> map1 = (HashMap<String, String>) val;
//                if(map1.get("title").equals("发送短信")){
//                    int id = Integer.parseInt(map1.get("taskId"));
//                    String message = id+map1.get("title")+"     "+map1.get("time");
//                    MyselfMessageInfo dataMessage = new MyselfMessageInfo(message,getActivity().getResources().getDrawable(R.drawable.xt_functions_send_message));
//                    listItem.add(dataMessage);
//
////                    listItem.set(id,dataMessage);
//
//                } else if(map1.get("title").equals("发送GPS")){
//                    int id = Integer.parseInt(map1.get("taskId"));
//                    String message = id+map1.get("title")+"     "+map1.get("time");
//                    MyselfMessageInfo dataMessage = new MyselfMessageInfo(message,getActivity().getResources().getDrawable(R.drawable.xt_functinos_gps));
//                    listItem.add(dataMessage);
//                }else if(map1.get("title").equals("定时摄像")){
//                    int id = Integer.parseInt(map1.get("taskId"));
//                    String message = id+map1.get("title")+"     "+map1.get("time");
//                    MyselfMessageInfo dataMessage = new MyselfMessageInfo(message,getActivity().getResources().getDrawable(R.drawable.timg1));
//                    listItem.add(dataMessage);
//                }
////                Iterator iterator1 = map1.entrySet().iterator();
////                System.out.println(map1.get("title"));
////                System.out.println(map1.get("time"));
////                ArrayList arr = new ArrayList();
////                Set s = map1.keySet();
////                Iterator it = s.iterator();
////                arr.add((Integer)key);
////                while (iterator1.hasNext()) {
////                    Map.Entry entry1 = (Map.Entry) iterator1.next();
////                    Object object = entry1.getKey();
////                    Object val2 = entry1.getValue();
////                    System.out.println(object.toString() + "--->" + val2.toString());
////                    arr.add(val2.toString());
////
////                }
//
//
////                MyselfMessageInfo dataMessage2 = new MyselfMessageInfo("发送短信",getActivity().getResources().getDrawable(R.drawable.xt_functions_send_message));
////                listItem.add(dataMessage2);
//
//            }
        }else{
        }
        return listItem;
    }
    private void initEmpty(){
        if(listView.getCount()==0) {
            TextView emptyView = new TextView(getActivity());
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            emptyView.setText("您还还没有添加任务！点击添加");
            emptyView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            emptyView.setVisibility(View.GONE);
            ((ViewGroup) listView.getParent()).addView(emptyView);
            listView.setEmptyView(emptyView);
            emptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent  = new Intent();
                    intent.setClass(getActivity(), XTFunctionsListController.class);
                    startActivity(intent);
                }
            });
        }
    }
}
