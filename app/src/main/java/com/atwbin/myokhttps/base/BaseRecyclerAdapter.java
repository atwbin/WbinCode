package com.atwbin.myokhttps.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Wbin
 * @title xxxxx.java
 * @project 千品猫商城
 * @date 2017/1/6 0006 13:53
 * @ copyright  Copyright © 2016 qpmall.com Inc. All Rights Reserved.
 * @description 系统名称：
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public BaseRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        this.mDatas = new ArrayList<>();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public BaseRecyclerAdapter(Context mContext, List<T> datas) {
        if (datas == null)
            mDatas = new ArrayList<>();
        this.mDatas = datas;
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public BaseRecyclerAdapter(Context mContext, T[] datas) {
        this.mContext = mContext;
        this.mDatas = new ArrayList<T>();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Collections.addAll(mDatas, datas);
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    /**
     * 更新数据，替换原有数据
     */
    public void updateItems(List<T> items) {
        mDatas = items;
        notifyDataSetChanged();
    }

    /**
     * 插入一条数据
     */
    public void addItem(T item) {
        mDatas.add(0, item);
        notifyItemInserted(0);
    }

    /**
     * 插入一条数据
     */
    public void addItem(T item, int position) {
        position = Math.min(position, mDatas.size());
        mDatas.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 在列表尾添加一串数据
     */
    public void addItems(List<T> items) {
        int start = mDatas.size();
        mDatas.addAll(items);
        notifyItemRangeChanged(start, items.size());
    }

    /**
     * 移除一条数据
     */
    public void removeItem(int position) {
        if (position > mDatas.size() - 1) {
            return;
        }
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 移除一条数据
     */
    public void removeItem(T item) {
        int position = 0;
        ListIterator<T> iterator = mDatas.listIterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next == item) {
                iterator.remove();
                notifyItemRemoved(position);
            }
            position++;
        }
    }

    /**
     * 清除所有数据
     */
    public void removeAllItems() {
        mDatas.clear();
        notifyDataSetChanged();
    }

}
