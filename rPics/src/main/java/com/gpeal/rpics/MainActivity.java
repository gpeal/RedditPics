package com.gpeal.rpics;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

import android.widget.SearchView;
import com.gpeal.rpics.adapters.ListItemAdapter;
import com.gpeal.rpics.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        new LoadSubredditTask().execute("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchButton = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchButton.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                new LoadSubredditTask().execute(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
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

    public void reloadItems(ArrayList<ListItem> items) {
        Log.i(Utils.TAG, "Reloading " + items.size() + " items");
        if (items.size() == 0) {
            return;
        }

        leftAdapter.clear();
        rightAdapter.clear();
        for (int i = 0; i < items.size(); i++) {
            ListItem newItem = new ListItem(items.get(i).url, items.get(i).title);
            if (i % 2 == 0) {
                leftAdapter.add(newItem);
            }
            else {
                rightAdapter.add(newItem);
            }
        }
        leftAdapter.notifyDataSetChanged();
        rightAdapter.notifyDataSetChanged();
    }

    private class LoadSubredditTask extends AsyncTask<String, Void, ArrayList<ListItem>> {
        @Override
        protected ArrayList<ListItem> doInBackground(String... subreddits) {
            String subreddit = subreddits[0];
            String url = subreddit.equals("") ? "http://reddit.com/.json?limit=100" : "http://reddit.com/r/" + subreddit + ".json?limit=100";
            String data;

            if (!Utils.isNetworkAvailable(MainActivity.this)) {
                // TODO: handle this
            }
            data = Utils.httpGet(url);
            try {
                JSONObject posts = new JSONObject(data);
                return parseRedditPosts(posts);
            }
            catch(JSONException e) {
                Log.e(Utils.TAG, "Failed to parse data: " + e.getMessage());
                cancel(true);
            }
            return new ArrayList<ListItem>();
        }

        private ArrayList<ListItem> parseRedditPosts(JSONObject subreddit)
            throws JSONException, NullPointerException {
            ArrayList<ListItem> items = new ArrayList<ListItem>();
            JSONArray posts;
            posts = subreddit.getJSONObject("data").getJSONArray("children");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.getJSONObject(i).getJSONObject("data");
                if (Utils.isImage(post.getString("url"))) {
                    items.add(new ListItem(post.getString("url"), post.getString("title")));
                }
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<ListItem> result) {
            reloadItems(result);
        }
    }


}
