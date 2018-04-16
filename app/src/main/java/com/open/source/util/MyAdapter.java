package com.open.source.util;

import android.support.annotation.NonNull;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by 56890 turn_on 2017/2/28.
 */
public class MyAdapter extends MultiTypeAdapter {
    public MyAdapter() {
    }

    public MyAdapter(@NonNull List<?> items) {
        super(items);
    }

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public List<Object> getItems() {
        return (List<Object>) super.getItems();
    }

    public void addList(List<Object> list) {
        getItems().addAll(list);
        notifyDataSetChanged();
    }

    public void setDataClear(List<Object> list) {
        getItems().clear();
        getItems().addAll(list);
        notifyDataSetChanged();
    }

    public void setData(List<Object> list) {
        getItems().addAll(list);
        notifyDataSetChanged();
    }

    public void addContent(Object liveMessage) {
        getItems().add(liveMessage);
        notifyDataSetChanged();
    }
}
