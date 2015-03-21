package com.faizmalkani.sunshine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faiz on 3/21/2015.
 */
public class ListAdapter extends BaseAdapter
{
    List<String> mConditionItems = new ArrayList<String>();
    Context context;

    ListAdapter(List<String> mConditionItemsParam, Context contextParam)
    {
        mConditionItems = mConditionItemsParam;
        context = contextParam;
    }

    public int getCount()
    {
        return mConditionItems.size();
    }

    public Object getItem(int position)
    {
        return mConditionItems.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.condition_list_row, parent, false);
        }
        TextView mConditionText = (TextView) convertView.findViewById(R.id.condition_row_text);
        ImageView mConditionIcon = (ImageView) convertView.findViewById(R.id.condition_row_icon);
        mConditionText.setText(mConditionItems.get(position));
        if(position == 0)
        {
            mConditionIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_condition_humidity));
        }
        else if (position == 1)
        {
            mConditionIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_condition_pressure));
        }
        else if(position == 2)
        {
            mConditionIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_condition_wind));
        }

        return (convertView);
    }
}
