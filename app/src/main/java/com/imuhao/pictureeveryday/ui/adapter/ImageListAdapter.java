package com.imuhao.pictureeveryday.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.ImageBean;
import com.imuhao.pictureeveryday.ui.activity.ReviewPictureActivity;
import com.imuhao.pictureeveryday.utils.DensityUtil;
import com.like.LikeButton;
import com.like.OnLikeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smile
 * @time 2016/6/22  15:39
 * @desc ${TODD}
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

  private Context mContext;
  private List<ImageBean> mData = new ArrayList<>();
  private List<Integer> mHeight;
  private int ScreenHeight;
  private onItemClickListener mListener;

  public void setData(List<ImageBean> data) {
    mData = data;
    for (int i = 0; i < mData.size() + 1; i++) {
      mHeight.add((int) (ScreenHeight * 0.25 + Math.random() * ScreenHeight * 0.25));
    }
    notifyDataSetChanged();
  }

  public void addData(List<ImageBean> data) {
    if (data != null && data.size() > 0) {
      mData.addAll(data);
      for (int i = 0; i < mData.size(); i++) {
        mHeight.add((int) (ScreenHeight * 0.25 + Math.random() * ScreenHeight * 0.25));
      }
      notifyDataSetChanged();
    }
  }

  public ImageListAdapter(Context context) {
    mContext = context;
    ScreenHeight = DensityUtil.getHeight(mContext);
    mHeight = new ArrayList<>();
  }

  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.navfuil_item, null);
    ViewHolder holer = new ViewHolder(view);
    return holer;
  }

  public void onBindViewHolder(ViewHolder holder, final int position) {
    ImageBean bean = mData.get(position);
    holder.mTime.setText(bean.getPublishedAt().substring(0, 10));

    Glide.with(mContext)
        .load(bean.getUrl())
        .placeholder(R.drawable.pic_gray_bg)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(holder.mImage);
    holder.mImage.getLayoutParams().height = mHeight.get(position);

    holder.mImage.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        ReviewPictureActivity.start(mContext, mData, position);
      }
    });

    holder.collect.setOnLikeListener(new OnLikeListener() {
      public void liked(LikeButton likeButton) {
        //Toast.makeText(mContext, "收藏成功!", Toast.LENGTH_SHORT).show();
      }

      @Override public void unLiked(LikeButton likeButton) {
        //Toast.makeText(mContext, "取消收藏!", Toast.LENGTH_SHORT).show();
      }
    });
  }

  public int getItemCount() {
    return mData != null ? mData.size() : 0;
  }

  public interface onItemClickListener {
    void onItemClick(View view, int position);
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.image) ImageView mImage;
    @Bind(R.id.time) TextView mTime;
    @Bind(R.id.collect) LikeButton collect;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}





