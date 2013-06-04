package com.gpeal.rpics;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import java.util.ArrayList;
import com.gpeal.rpics.adapters.ListItemAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends Activity {

    ArrayList<ListItem> leftItems = new ArrayList<ListItem>();
    ArrayList<ListItem> rightItems = new ArrayList<ListItem>();
    ListView leftListView;
    ListView rightListView;
    ListItemAdapter leftAdapter;
    ListItemAdapter rightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(config);

        leftListView = (ListView)findViewById(R.id.dual_list_view_left);
        rightListView = (ListView)findViewById(R.id.dual_list_view_right);
        loadItems();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void loadItems() {
        leftItems.add(new ListItem("http://i.imgur.com/b3ufpMN.jpg", "Reverse Graffiti"));
        leftItems.add(new ListItem("http://i.imgur.com/b3ufpMN.jpg", "Reverse Graffiti"));
        leftItems.add(new ListItem("http://i.imgur.com/b3ufpMN.jpg", "Reverse Graffiti"));
        rightItems.add(new ListItem("http://i.imgur.com/b3ufpMN.jpg", "Reverse Graffiti"));
        rightItems.add(new ListItem("http://i.imgur.com/b3ufpMN.jpg", "Reverse Graffiti"));
        rightItems.add(new ListItem("http://i.imgur.com/b3ufpMN.jpg", "Reverse Graffiti"));
        rightItems.add(new ListItem("http://i.imgur.com/b3ufpMN.jpg", "Reverse Graffiti"));

        leftAdapter = new ListItemAdapter(this, R.layout.list_item, leftItems);
        rightAdapter = new ListItemAdapter(this, R.layout.list_item, rightItems);

        leftListView.setAdapter(leftAdapter);
        rightListView.setAdapter(rightAdapter);
    }


}
