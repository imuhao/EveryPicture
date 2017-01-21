package com.imuhao.pictureeveryday.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.EssayBean;
import com.imuhao.pictureeveryday.ui.listener.onItemClickListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

/**
 * @author Smile
 * @time 2016/6/24  11:34
 * @desc ${TODD}
 */
public class PublicAdapter extends RecyclerView.Adapter<MyHolder> {
    private Context mContext;
    private List<EssayBean> mDatas;
    private onItemClickListener mListener;

    public void setListener(onItemClickListener listener) {
        mListener = listener;
    }

    public PublicAdapter(Context context, List<EssayBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void upListData(List<EssayBean> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.public_item, parent, false);

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        final EssayBean bean = mDatas.get(position);
        holder.title.setText(bean.getDesc());
        holder.collect.setLiked(false);
        holder.collect.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();

            }
        });
        holder.time_tv.setText(bean.getPublishedAt().substring(0, 10));
        holder.who_tv.setText(bean.getWho());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    v.setTag(bean);
                    mListener.onItemClick(v);
                }
            }
        });
    }

    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }
}

class MyHolder extends RecyclerView.ViewHolder {
    TextView title;
    LikeButton collect;
    TextView time_tv;
    TextView who_tv;

    public MyHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        collect = (LikeButton) itemView.findViewById(R.id.collect);
        time_tv = (TextView) itemView.findViewById(R.id.time_tv);
        who_tv = (TextView) itemView.findViewById(R.id.who_tv);
    }
}
