package com.gpeal.rpics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gpeal.rpics.ListItem;
import com.gpeal.rpics.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ListItemAdapter extends ArrayAdapter<ListItem>{

    Context context;
    LayoutInflater inflater;
    int layoutResourceId;
    float imageWidth;

    public ListItemAdapter(Context context, int layoutResourceId, List<ListItem> items) {
        super(context, layoutResourceId, items);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

     @Override
    public View getView(int position, View view, ViewGroup parentGroup) {
        ListItem item = getItem(position);
        // if there is a new view to reuse, use it instead of making a new one
        LinearLayout newView = (LinearLayout)view;

        if (newView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            newView = (LinearLayout)inflater.inflate(layoutResourceId, parentGroup, false);
        }

        ImageView imageView = (ImageView)newView.findViewById(R.id.item_image);
        TextView textView = (TextView)newView.findViewById(R.id.item_title);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(item.url, imageView);

        textView.setText(item.title);


        return newView;
    }
}