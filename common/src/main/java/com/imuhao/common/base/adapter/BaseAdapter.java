package com.imuhao.common.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.imuhao.common.interfaces.IListDataOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dafan on 2015/11/14 0014.
 * 主要是对数据源接口的封装
 * setLoadFinished
 * setFooterText
 * 以上方法是刷新布局中需要实现的，这里空实现
 */
public class BaseAdapter<T, VH extends RecyclerView.ViewHolder>
		extends RecyclerView.Adapter<VH>
		implements IListDataOperation<T> {

	public List<T> listData = new ArrayList<>();
	private OnItemClickListener mOnItemClickLitener;
	private OnItemLongClickListener mOnItemLongClickLitener;

	@Override
	public void addData(List<T> data) {
		if (data == null) return;
		if (data.isEmpty()) return;

		this.listData.addAll(data);
		this.notifyItemRangeInserted(this.getItemCount(), data.size());
	}

	@Override
	public void addData(int index, T bean) {
		if (bean == null) return;
		if (index < 0 || index > this.getItemCount()) return;

		this.listData.add(index, bean);
		this.notifyItemInserted(index);
	}

	@Override
	public void addData(T data) {
		if (data == null) return;
		this.listData.add(data);
		this.notifyItemInserted(this.listData.size());
	}

	@Override
	public void changeData(T bean, int index) {
		if (bean == null) return;
		if (index < 0 || index > this.getItemCount()) return;

		this.listData.set(index, bean);
		    /*this.notifyItemChanged(index);*/
		this.notifyDataSetChanged();
	}

	@Override
	public void removeData(int index) {
		if (index < 0 || index > this.listData.size()) return;
		this.listData.remove(index);
        /*this.notifyItemRemoved(index);*/
		this.notifyDataSetChanged();
	}

	@Override
	public List<T> getData() {
		return this.listData;
	}

	@Override
	public void setData(List<T> data) {
		if (data == null) return;

		this.listData = data;
		this.notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return this.listData == null ? 0 : this.listData.size();
	}

	@Override
	public VH onCreateViewHolder(ViewGroup parent, int viewType) {
		return null;
	}

	@Override
	public void onBindViewHolder(final VH holder, final int position) {
		holder.itemView.setTag(holder);
		if (mOnItemClickLitener != null)
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnItemClickLitener.onItemClick(holder.itemView, position);
				}
			});

		if (mOnItemLongClickLitener != null) {
			holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					mOnItemLongClickLitener.onItemLongClick(holder.itemView, position);
					return true;
				}
			});
		}
	}

	public void onDestoryView() {
	}

	/**
	 * 点击事件
	 */
	public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}

	public void setOnItemClickListener(OnItemClickListener litener) {
		mOnItemClickLitener = litener;
	}

	/**
	 * 长按事件
	 */
	public interface OnItemLongClickListener {
		void onItemLongClick(View view, int position);
	}

	public void setOnItemLongClickLitener(OnItemLongClickListener litener) {
		mOnItemLongClickLitener = litener;
	}
}
