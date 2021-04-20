package com.example.restfulapi;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DialogMovies extends DialogFragment {
    String key;

    public DialogMovies() {
    }

    public DialogMovies(String key) {
        this.key = key;
    }

    ImageView img_detail;
    TextView tv_title;
    TextView tv_released;
    TextView tv_genre;
    TextView tv_director;
    TextView tv_actors;
    TextView tv_posters;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_item_movies, container, false);
        viewsFinderById(view);
        requestDataDetail();
        return view;
    }


    private void requestDataDetail(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("t", key);
        client.get("http://www.omdbapi.com/?i=tt3896198&apikey=e438ccfa", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Error", "Request Error");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject object = new JSONObject(responseString);
                    String title = object.getString("Title");
                    String released = object.getString("Released");
                    String genre = object.getString("Genre");
                    String director = object.getString("Director");
                    String actors = object.getString("Actors");
                    String poster = object.getString("Poster");

                    Picasso.get().load(poster).into(img_detail);
                    tv_title.setText("Title : " + title);
                    tv_released.setText("Released : " + released);
                    tv_genre.setText("Genre :" + genre);
                    tv_director.setText("Director : " + director);
                    tv_actors.setText("Actors : " + actors);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void viewsFinderById(View view){
        img_detail = view.findViewById(R.id.img_detail);
        tv_title = view.findViewById(R.id.tv_title);
        tv_released = view.findViewById(R.id.tv_released);
        tv_genre = view.findViewById(R.id.tv_genre);
        tv_director = view.findViewById(R.id.tv_director);
        tv_actors = view.findViewById(R.id.tv_actors);
        tv_posters = view.findViewById(R.id.tv_posters);
    }
}
