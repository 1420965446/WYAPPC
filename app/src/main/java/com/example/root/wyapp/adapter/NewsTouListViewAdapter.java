package com.example.root.wyapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.wyapp.R;
import com.example.root.wyapp.Utils.ImageUtil;
import com.example.root.wyapp.bean.newsBean.NewsListsBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by root on 2017/7/20.
 */

public class NewsTouListViewAdapter extends MyBaseAdapter<NewsListsBean>{




    public NewsTouListViewAdapter(ArrayList data) {
        super(data);
    }

    public void addData(ArrayList<NewsListsBean> t1348647909107) {

        mData.addAll(t1348647909107);
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<NewsListsBean> t1348647909107) {
        mData.clear();
        mData.addAll(t1348647909107);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotNewsViewHolder viewHolder;
        if(convertView==null) {
            viewHolder = new HotNewsViewHolder();

            convertView = View.inflate(parent.getContext(), R.layout.newstout_listview_item,null);

            viewHolder.mIvHot = (ImageView) convertView.findViewById(R.id.iv_hot);
            viewHolder.mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mTvSource = (TextView) convertView.findViewById(R.id.tv_source);
            viewHolder.mTvReply = (TextView) convertView.findViewById(R.id.tv_reply);
            viewHolder.mTvTop = (TextView) convertView.findViewById(R.id.tv_top);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (HotNewsViewHolder) convertView.getTag();
        }
        changeUI(viewHolder, mData.get(position));

       // ImageUtil.getSingleton().showImage(mData.get(position).getImg(),viewHolder.mIvHot);
        String picUrl = mData.get(position).getImg();
        Picasso.with(parent.getContext())
                         .load(picUrl)
                      //   .centerCrop()
                         .into(viewHolder.mIvHot);

       // Log.i("asas",""+mlistsArrayBean.getT1348647909107().get(position).getImg());
        //ImageUtil.getSingleton().showImage(mlistsArrayBean.getT1348647909107().get(position).getImg(),viewHolder.mIvHot);
        //String picUrl = mlistsArrayBean.getT1348647909107().get(0).getAds().get(position).getImgsrc();

        //ImageUtil.getSingleton().showImage(picUrl,host.imageView);
        //        host.imageView.displa
//        Bitmap bitmap = BitmapFactory.decodeFile(mfileName.get(position));
//        host.imageView.setImageBitmap(bitmap);
//        host.imageView.setImageBitmap(bitmap);
       // Picasso.with(parent.getContext())
       ///         .load(picUrl)
//                .centerCrop()
     //           .into(host.imageView);
       // Log.e("TAGa", ""+picUrl);

       // host.textView.setText();
        return convertView;
    }

    private void changeUI(HotNewsViewHolder viewHolder, NewsListsBean o) {

        viewHolder.mTvTitle.setText(o.getTitle());
        viewHolder.mTvSource.setText(o.getSource());
        if("S".equals(o.getInterest())){
            viewHolder.mTvTop.setVisibility(View.VISIBLE);
            viewHolder.mTvReply.setVisibility(View.GONE);
        }else{
            viewHolder.mTvTop.setVisibility(View.GONE);
            int replyCount = Integer.valueOf(o.getReplyCount());
            if(replyCount==0){
                viewHolder.mTvReply.setVisibility(View.GONE);
            }else{
                viewHolder.mTvReply.setVisibility(View.VISIBLE);
                viewHolder.mTvReply.setText(replyCount+"跟帖");
            }
        }
//         Picasso.with(parent.getContext())
//                 .load(picUrl)
//                        .centerCrop()
//                   .into(host.imageView);
        ImageUtil.getSingleton().showImage(o.getImg(),viewHolder.mIvHot);
    }


    public class HotNewsViewHolder{
        ImageView mIvHot;
        TextView mTvTitle;
        TextView mTvSource;
        TextView mTvReply;
        TextView mTvTop;
    }
}
