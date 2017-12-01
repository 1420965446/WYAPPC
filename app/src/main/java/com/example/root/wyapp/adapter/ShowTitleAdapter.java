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

public class ShowTitleAdapter extends MyBaseAdapter<String> {

    public ShowTitleAdapter(ArrayList<String> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShowTitleViewHolder addTitleViewHolder;
        if(convertView==null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_change_title, null);
            addTitleViewHolder = new ShowTitleViewHolder();
            addTitleViewHolder.tvChangeTitle = (TextView) convertView.findViewById(R.id.tv_change_title);
            addTitleViewHolder.ivChangeTitle = (ImageView) convertView.findViewById(R.id.iv_change_title);
            convertView.setTag(addTitleViewHolder);
        }else {
            addTitleViewHolder = (ShowTitleViewHolder) convertView.getTag();
        }
        changeUI(addTitleViewHolder,position);
        return convertView;
    }

    private void changeUI(ShowTitleViewHolder addTitleViewHolder,int position) {
        String s = mData.get(position);
        addTitleViewHolder.tvChangeTitle.setText(s);
        if(mIsShowDelete){
            //是否展示
            addTitleViewHolder.ivChangeTitle.setVisibility(position==0?View.GONE:View.VISIBLE);
        }else{
            addTitleViewHolder.ivChangeTitle.setVisibility(View.GONE);
        }
    }

    public boolean isShowDelete(){
        return mIsShowDelete;
    }

    public class ShowTitleViewHolder{
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
    private boolean mIsShowDelete = false;

    public void setShowDelete(boolean b) {
        if(mIsShowDelete==b){
            //避免无聊用户反复长按
            return;
        }
        mIsShowDelete = b;
        notifyDataSetChanged();
    }

}
