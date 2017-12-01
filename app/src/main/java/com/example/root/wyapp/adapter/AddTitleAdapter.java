package com.example.root.wyapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.wyapp.R;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/27.
 */

public class AddTitleAdapter extends MyBaseAdapter<String> {

    public AddTitleAdapter(ArrayList<String> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddTitleViewHolder addTitleViewHolder;
        if(convertView==null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_change_title, null);
            addTitleViewHolder = new AddTitleViewHolder();
            addTitleViewHolder.tvChangeTitle = (TextView) convertView.findViewById(R.id.tv_change_title);
            addTitleViewHolder.ivChangeTitle = (ImageView) convertView.findViewById(R.id.iv_change_title);
            convertView.setTag(addTitleViewHolder);
        }else {
            addTitleViewHolder = (AddTitleViewHolder) convertView.getTag();
        }
        changeUI(addTitleViewHolder,mData.get(position));
        return convertView;
    }

    private void changeUI(AddTitleViewHolder addTitleViewHolder, String s) {
        addTitleViewHolder.tvChangeTitle.setText(s);
    }

    public class AddTitleViewHolder{
        TextView tvChangeTitle;
        ImageView ivChangeTitle;
    }

    public String deleteItem(int position){
        String remove = mData.remove(position);
        notifyDataSetChanged();
        return remove;
    }

    public void addItem(String item){
        mData.add(item);
        notifyDataSetChanged();
    }

}
