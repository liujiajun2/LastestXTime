package com.jmu.xtime.functionservers.XTFunctionsList.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmu.xtime.R;
import com.jmu.xtime.functionservers.XTFunctionsList.Model.XTFunctionModel;

import java.util.List;

/**
 * Created by 沈启金 on 2017/3/12.
 */

public class XTFunctionsListAdapter extends BaseAdapter {

    private List<XTFunctionModel> functionsListArray = null;
    LayoutInflater infater = null;

    public XTFunctionsListAdapter(Context context, List<XTFunctionModel> functions) {
        infater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        functionsListArray = functions;
    }

    @Override
    public int getCount() {
        return functionsListArray.size();
    }

    @Override
    public Object getItem(int position) {
        return functionsListArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            view = infater.inflate(R.layout.xt_functions_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            view = convertView ;
            holder = (ViewHolder) convertView.getTag() ;
        }

        XTFunctionModel functionModel = (XTFunctionModel) getItem(position);
        holder.functionIcon.setImageDrawable(functionModel.getFunctionIcon());
        holder.functionLabel.setText(functionModel.getFunctionName());
        return view;
    }

    class ViewHolder {
        ImageView functionIcon;
        TextView functionLabel;

        public ViewHolder(View view) {
            this.functionIcon = (ImageView) view.findViewById(R.id.xtFunctionIcon);
            this.functionLabel = (TextView) view.findViewById(R.id.xtFunctionLabel);
        }
    }
}
