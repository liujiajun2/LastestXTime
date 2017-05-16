package com.jmu.xtime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qing on 2017/5/13.
 */

public class FunctionMessageAdapter extends BaseAdapter {
    private List<FunctionMessageInfo> messageInfos ;
    LayoutInflater infater = null;

    public FunctionMessageAdapter(List<FunctionMessageInfo> messageInfos,Context context){
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
        FunctionMessageAdapter.ViewHolder viewHolder;
        View view = convertView;

        if(view == null) {
            view = infater.inflate(R.layout.function_item, null);
            viewHolder = new FunctionMessageAdapter.ViewHolder();
            viewHolder.mTitleImageView = (ImageView) view.findViewById(R.id.img_0);
            viewHolder.mTitleTextView = (TextView) view.findViewById(R.id.title_0);
            view.setTag(viewHolder);
        } else {
            viewHolder = (FunctionMessageAdapter.ViewHolder) view.getTag();
        }

        final FunctionMessageInfo titleData = (FunctionMessageInfo) getItem(position);
        viewHolder.mTitleTextView.setText(titleData.getTextLogin());
        viewHolder.mTitleImageView.setImageDrawable(titleData.getIconLogin());

        return view;
    }
    class ViewHolder{
        protected ImageView mTitleImageView;
        protected TextView mTitleTextView;
    }

}
