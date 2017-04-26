package com.jmu.xtime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 倾城一世 on 2017/3/22.
 */

public class MyselfMessageAdapter extends BaseAdapter {
    private List<MyselfMessageInfo> messageInfos ;
    LayoutInflater infater = null;

    public MyselfMessageAdapter(List<MyselfMessageInfo> messageInfos,Context context){
        this.messageInfos = messageInfos;
        this.infater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
    }
    @Override
    public int getCount() {
        return messageInfos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return messageInfos.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder;
        View view = convertView;

        if(view == null) {
            view = infater.inflate(R.layout.myself_function_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mTitleImageView = (ImageView) view.findViewById(R.id.icon_login);
            viewHolder.mTitleTextView = (TextView) view.findViewById(R.id.text_login);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final MyselfMessageInfo titleData = (MyselfMessageInfo) getItem(position);
        viewHolder.mTitleTextView.setText(titleData.getTextLogin());
        viewHolder.mTitleImageView.setImageDrawable(titleData.getIconLogin());

        return view;
    }
    class ViewHolder{
        protected ImageView mTitleImageView;
        protected TextView mTitleTextView;
    }


}
