package com.example.joeslie.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private static final String API_NEWS = "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=0edceee007914e1eb1b8be9459501cbe";
    private NewsAdapter mAdapter;

    public static final String LOG_TAG = NewsActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ListView newsListView = (ListView) findViewById(R.id.list);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        //Set Adapter
        newsListView.setAdapter(mAdapter);
/*
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                News currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        */

        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(API_NEWS);
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<News>> {

        @Override
        protected List<News> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<News> result = Utils.fetchNews(urls[0]);
            return result;
        }
        protected void onPostExecute(List<News> data) {
            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
