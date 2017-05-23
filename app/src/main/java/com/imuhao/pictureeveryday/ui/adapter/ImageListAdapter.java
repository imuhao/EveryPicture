package com.imuhao.pictureeveryday.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.ItemBean;
import com.imuhao.pictureeveryday.utils.DensityUtil;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Smile
 * @time 2016/6/22  15:39
 * @desc ${TODD}
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private Context mContext;
    private List<ItemBean> mData = new ArrayList<>();
    private int ScreenHeight;   //屏幕高度
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setData(List<ItemBean> data) {
        mData = data;
    }

    public void addData(List<ItemBean> data) {
        mData.addAll(data);
    }

    public ImageListAdapter(Context context) {
        mContext = context;
        ScreenHeight = DensityUtil.getHeight(mContext);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.navfuil_item, null);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        ItemBean bean = mData.get(holder.getAdapterPosition());
        holder.tvTime.setText(bean.getPublishedAt().substring(0, 10));

        Glide.with(mContext).load(bean.getUrl()).placeholder(R.drawable.pic_gray_bg).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivImage);

        //设置自定义高度
        double height = (Math.random() + 1) * ScreenHeight * 0.25;

        holder.ivImage.getLayoutParams().height = (int) height;

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, mData, holder.getAdapterPosition());
                }
            }
        });

        holder.lbCollect.setOnLikeListener(new OnLikeListener() {
            public void liked(LikeButton likeButton) {
                //Toast.makeText(mContext, "收藏成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                //Toast.makeText(mContext, "取消收藏!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, List<ItemBean> mData, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView ivImage;
        @Bind(R.id.time)
        TextView tvTime;
        @Bind(R.id.collect)
        LikeButton lbCollect;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}





