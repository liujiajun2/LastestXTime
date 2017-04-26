package com.jmu.xtime;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 倾城一世 on 2017/3/8.
 */

public class MyFragment extends Fragment {
    private String context;
    private TextView mTextView;
    private TextView login_newuser;
    private Intent intent;

//    public MyFragment(String context){
//        this.context=context;
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      View view = inflater.inflate(R.layout.my_fragment,container,false);
//        mTextView.setText(context);
//        intent = new Intent();
//        intent.setClass(MainActivity.class,RegisterActivity.class);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.icon_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        getActivity().findViewById(R.id.text_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        getActivity().findViewById(R.id.text_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),SettingActivity.class);
                startActivity(intent);
            }
        });
        getActivity().findViewById(R.id.icon_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
