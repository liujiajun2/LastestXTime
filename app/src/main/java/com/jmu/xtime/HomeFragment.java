package com.jmu.xtime;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jmu.xtime.functionservers.XTFunctionsList.Controller.XTFunctionsListController;
import com.jmu.xtime.update.TaskManager.TaskInfomationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 倾城一世 on 2017/3/9.
 */

public class HomeFragment extends Fragment {

   LinearLayout LL;

    private TaskInfomationManager taskInfomationManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        LL = (LinearLayout)view.findViewById(R.id.home_show_info);
        showTask();
        return view;
    }
    private void showTask(){
        taskInfomationManager = new TaskInfomationManager(this.getActivity().getBaseContext());
        Map<Integer, HashMap<String, String>> map2 = taskInfomationManager.getTasks();
        if(map2!=null) {
            Iterator iterator = map2.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                Map<String, String> map1 = (HashMap<String, String>) val;
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
               // linearLayout.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK)
                linearLayout.setPadding(30,20,30,20);


                //设置layout_weight 第三个参数是weight的值
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
                TextView textView1 = new TextView(getActivity());
                textView1.setText(map1.get("title"));
                textView1.setTextSize(18);
                textView1.setLayoutParams(params1);

                TextView textView2 = new TextView(getActivity());
                textView2.setText(map1.get("time"));
                textView2.setTextSize(18);
                textView2.setGravity(Gravity.RIGHT);
                textView2.setLayoutParams(params1);

                linearLayout.addView(textView1);
                linearLayout.addView(textView2);
                View view = new View(getActivity());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                view.setBackgroundColor(Color.rgb(77,77,77));

                LL.addView(linearLayout);
                LL.addView(view);

            }
        }else{

        }

    }


}
