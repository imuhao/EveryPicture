package com.imuhao.pictureeveryday.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.GankBean;
import com.imuhao.pictureeveryday.bean.ItemViewBean;
import com.imuhao.pictureeveryday.ui.activity.WebActivity;
import com.imuhao.pictureeveryday.ui.view.RatioImageView;
import com.imuhao.pictureeveryday.utils.DataCategoryUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smile
 * @time 2017/5/3  下午3:33
 * @desc ${TODD}
 */
public class TodayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int TITLE = 0X001;
  private static final int CATEGORY = 0x002;
  private static final int IMAGE = 0x003;
  private List<ItemViewBean> mData = new ArrayList<>();

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    if (viewType == TITLE) {
      View view = inflater.inflate(R.layout.item_gank_title, parent, false);
      return new TitleHolder(view);
    } else if (viewType == CATEGORY) {
      View view = inflater.inflate(R.layout.item_gank_category, parent, false);
      return new CategoryHolder(view);
    } else if (viewType == IMAGE) {
      View view = inflater.inflate(R.layout.item_gank_image, parent, false);
      return new ImageHolder(view);
    }
    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ItemViewBean itemViewBean = mData.get(position);
    if (holder instanceof TitleHolder) {
      ((TitleHolder) holder).bind(itemViewBean);
    } else if (holder instanceof CategoryHolder) {
      ((CategoryHolder) holder).bind(itemViewBean);
    } else if (holder instanceof ImageHolder) {
      ((ImageHolder) holder).bind(itemViewBean);
    }
  }

  public void setData(GankBean gankBean) {
    DataCategoryUtil.category(mData, gankBean);
  }

  @Override public int getItemCount() {
    return mData == null ? 0 : mData.size();
  }

  @Override public int getItemViewType(int position) {
    return mData.get(position).type;
  }

  class CategoryHolder extends RecyclerView.ViewHolder {
    TextView category;

    public CategoryHolder(View itemView) {
      super(itemView);
      category = (TextView) itemView.findViewById(R.id.category);
    }

    public void bind(ItemViewBean itemViewBean) {
      category.setText(itemViewBean.title);
    }
  }

  private class TitleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title;
    int mLastPosition;
    private static final int DELAY = 128;

    private TitleHolder(View itemView) {
      super(itemView);
      title = (TextView) itemView.findViewById(R.id.title);
      itemView.setOnClickListener(this);
    }

    public void bind(ItemViewBean itemViewBean) {
      title.setText(itemViewBean.title);
      showItemAnim(itemView, getAdapterPosition());
    }

    @Override public void onClick(View v) {
      ItemViewBean itemViewBean = mData.get(getAdapterPosition());
      WebActivity.start(itemView.getContext(), itemViewBean.title, itemViewBean.url);
    }

    public void showItemAnim(final View view, final int position) {
      final Context context = view.getContext();
      if (position > mLastPosition) {
        view.setAlpha(0);
        view.postDelayed(new Runnable() {
          @Override public void run() {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
            animation.setAnimationListener(new Animation.AnimationListener() {
              @Override public void onAnimationStart(Animation animation) {
                view.setAlpha(1);
              }

              @Override public void onAnimationEnd(Animation animation) {
              }

              @Override public void onAnimationRepeat(Animation animation) {
              }
            });
            view.startAnimation(animation);
          }
        }, DELAY * position);
        mLastPosition = position;
      }
    }
  }

  class ImageHolder extends RecyclerView.ViewHolder {
    RatioImageView imageView;

    public ImageHolder(View itemView) {
      super(itemView);
      imageView = (RatioImageView) itemView.findViewById(R.id.image);
    }

    public void bind(ItemViewBean itemViewBean) {
      imageView.setOriginalSize(50, 50);
      Glide.with(itemView.getContext()).load(itemViewBean.url).centerCrop().into(imageView);
    }
  }
}
