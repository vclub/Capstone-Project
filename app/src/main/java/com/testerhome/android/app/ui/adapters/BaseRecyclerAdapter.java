package com.testerhome.android.app.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bin Li on 2016/6/14.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mItems;
    protected Context mContext;

    public BaseRecyclerAdapter(Context context) {
        mContext = context;
        mItems = new ArrayList<>();
    }

    public List<T> getItems() {
        return mItems;
    }

    public void setItems(List<T> items){
        if (items == null) return;
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<T> items){
        if (items == null) return;
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected RecyclerAdapterListener<T> mListener;

    public void setListener(RecyclerAdapterListener listener) {
        mListener = listener;
    }

    public interface RecyclerAdapterListener<T>{
        void onEndOfList();
        void onListItemClick(T item);
    }
}
