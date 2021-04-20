package com.example.restfulapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText editText;
    private Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
    private String keyParams = String.valueOf(year);
    private RecyclerMovieAdapter adapter;
    private ArrayList<DataMovies> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewsFindById();
        keyParams = editText.getText().toString();
        requestClient(keyParams);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyParams = editText.getText().toString();
                requestClient(keyParams);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void requestClient(String keParam){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("s", keParam);
        client.get("http://www.omdbapi.com/?i=tt3896198&apikey=e438ccfa", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Request has been failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    JSONArray jsonSearch = jsonObject.getJSONArray("Search");
                    list.clear();

                    for (int i = 0 ; i< jsonSearch.length(); i++){
                        JSONObject object = jsonSearch.getJSONObject(i);
                        String title = object.getString("Title");
                        String year = object.getString("Year");
                        String poster = object.getString("Poster");

                        DataMovies dataMovies = new DataMovies(title, year, poster);
                        list.add(dataMovies);
                    }
                    adapter = new RecyclerMovieAdapter(list, MainActivity.this);
                    recyclerView.setAdapter(adapter);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }









    private void viewsFindById(){
        editText = findViewById(R.id.search);
        recyclerView = findViewById(R.id.rv_movies);
    }
}