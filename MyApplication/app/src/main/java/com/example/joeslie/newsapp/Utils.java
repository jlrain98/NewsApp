package com.example.joeslie.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.example.joeslie.newsapp.NewsActivity.LOG_TAG;

/**
 * Created by Joes Lie on 19/10/2017.
 */

class Utils {
    public static List<News> fetchNews(String requestURL) {
        //Create URL Object
        URL url = createUrl(requestURL);
        String JSONResponse = "";

        try {
            JSONResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<News> news = extractFeatureFromJson(JSONResponse);
        return news;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    //DON'T MODIFY THIS!! *************************************************************************
    private static List<News> extractFeatureFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<News> news = new ArrayList<>();
        String SendDate;
        //Create JSON Object
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);

            JSONArray articleArr = baseJsonResponse.getJSONArray("articles");

            for (int i = 0; i < articleArr.length(); i++) {

                JSONObject FirstNewsObj = articleArr.getJSONObject(i);

                String NewsTitle = FirstNewsObj.getString("title");

                String DescTitle = FirstNewsObj.getString("description");

                String DateTitle = FirstNewsObj.getString("publishedAt");

                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                parser.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date parsed = null;
                try {
                    parsed = parser.parse(DateTitle);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SendDate = parser.format(parsed);

                String URLImage = FirstNewsObj.getString("urlToImage");

                News news1 = new News(NewsTitle, DescTitle, SendDate, URLImage);

                news.add(news1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return news;
    }
}
