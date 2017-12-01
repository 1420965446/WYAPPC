package com.example.root.wyapp.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.wyapp.R;
import com.example.root.wyapp.Utils.ImageUtil;
import com.example.root.wyapp.bean.newsBean.NewsCommentBean;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/26.
 */

public class NewsCommentAdapter extends MyBaseAdapter<NewsCommentBean> {

    public NewsCommentAdapter(ArrayList<NewsCommentBean> data) {
        super(data);
        mData.add(0, new NewsCommentBean());
        Log.e("TAG", "mData"+mData);
    }

    public static final int TYPE_HOT = 0;//热门
    public static final int TYPE_COMMENT = 1;//一般

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HOT;
        } else {
            return TYPE_COMMENT;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == TYPE_HOT) {
            //当前view应该要展示成热门跟帖的效果
            convertView = View.inflate(parent.getContext(), R.layout.item_hot_title, null);
        } else {
            NewsCommentViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.news_comment_item, null);
                viewHolder = new NewsCommentViewHolder();
                viewHolder.ivUserIcon = (ImageView) convertView.findViewById(R.id.iv_user_icon);
                viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
                viewHolder.tvUserInfo = (TextView) convertView.findViewById(R.id.tv_user_info);
                viewHolder.ivSupport = (ImageView) convertView.findViewById(R.id.iv_support);
                viewHolder.tvVoteCount = (TextView) convertView.findViewById(R.id.tv_vote_count);
                viewHolder.flSubFloor = (FrameLayout) convertView.findViewById(R.id.fl_sub_floor);
                viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (NewsCommentViewHolder) convertView.getTag();
            }
            NewsCommentBean commentBean = mData.get(position);
            changeUI(viewHolder, commentBean);
        }
        return convertView;
    }
    private void changeUI(NewsCommentViewHolder viewHolder, NewsCommentBean commentBean) {
        Log.e("TAG", "commentBean:"+commentBean.toString());
        viewHolder.tvComment.setText(commentBean.getContent());
        viewHolder.tvUserName.setText(commentBean.getUser().getNickname());
        viewHolder.tvVoteCount.setText(commentBean.getVote()+"");
        String info = commentBean.getUser().getLocation()+" "
                +commentBean.getDeviceInfo().getDeviceName()+" "
                +commentBean.getCreateTime();
        viewHolder.tvUserInfo.setText(info);

        String url = commentBean.getUser().getAvatar();
        if(TextUtils.isEmpty(url)){
            viewHolder.ivUserIcon.setImageResource(R.drawable.icon_user_default);
        }else{
            ImageUtil.getSingleton().showImage(url,viewHolder.ivUserIcon);
        }
    }

    public class NewsCommentViewHolder{
        ImageView ivUserIcon;
        TextView tvUserName;
        TextView tvUserInfo;
        ImageView ivSupport;
        TextView tvVoteCount;
        FrameLayout flSubFloor;
        TextView tvComment;
    }

}
