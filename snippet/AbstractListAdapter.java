package com.tiehu.test.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A ListView adapter which applying the ViewHolder pattern.
 * Template for reuse old item view of ListView.
 *
 * Created by tieh on 2016-7-5.
 */
public abstract class AbstractListAdapter<T, H> extends ArrayAdapter<T> {
    private final LayoutInflater mInflater;
    private final int mResource;

    public AbstractListAdapter(Context context, int resource) {
        this(context, resource, new ArrayList<T>());
    }

    public AbstractListAdapter(Context context, int resource, T[] objects) {
        this(context, resource, Arrays.asList(objects));
    }

    public AbstractListAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder;
        if (convertView == null) {
            convertView = mInflater.inflate(mResource, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (H) convertView.getTag();
        }

        updateView(position, holder);
        return convertView;
    }

    /**
     * Create a ViewHolder which will be attached to the item view
     *
     * @param itemView the item view
     * @return
     */
    protected abstract H createViewHolder(View itemView);

    /**
     *  Update the View that displays the data at the specified position in the data set.
     *
     * @param position the position of the item whose view we want.
     * @param holder the ViewHolder
     */
    protected abstract void updateView(int position, H holder);
}
