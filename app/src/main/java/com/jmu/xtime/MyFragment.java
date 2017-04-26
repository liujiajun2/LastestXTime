package com.jmu.xtime;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 倾城一世 on 2017/3/8.
 */

public class MyFragment extends Fragment {
    private String context;
    private TextView mTextView;
    private TextView login_newuser;
    private Intent intent;
    private ListView listView;
    private MyselfMessageAdapter functionAdapt;
    private List<MyselfMessageInfo> myDatalist;
    private int incoCommon = R.mipmap.right;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      View view = inflater.inflate(R.layout.my_fragment,container,false);
        listView = (ListView)view.findViewById(R.id.listview);
        myDatalist = getMyDatalist();
        functionAdapt = new MyselfMessageAdapter(myDatalist,getActivity());
        listView.setAdapter(functionAdapt);
        listView.setOnItemClickListener(clickListener);
        return view;
    }
    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0){
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
            if(position == 1){

            }
        }
    };
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //可用来设置点击事件
//        getActivity().findViewById(R.id.icon_login).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    public List<MyselfMessageInfo> getMyDatalist(){
        List<MyselfMessageInfo> listItem = new ArrayList<MyselfMessageInfo>();
        MyselfMessageInfo data1 = new MyselfMessageInfo("登录",getActivity().getResources().getDrawable(R.drawable.login));
        MyselfMessageInfo data2 = new MyselfMessageInfo("设置",getActivity().getResources().getDrawable(R.drawable.setting));
        listItem.add(data1);
        listItem.add(data2);
        return listItem;
    }
}

