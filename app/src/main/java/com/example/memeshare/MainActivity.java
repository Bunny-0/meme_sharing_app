package com.example.memeshare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String TAG;
    ImageView ImageFrame;
    ProgressBar progress;

    String imageurl=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageFrame=findViewById(R.id.ImageFrame);
        progress=findViewById(R.id.Progress);
     loadMeme();
    }

    private void loadMeme()
    {
       progress.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        imageurl ="https://meme-api.herokuapp.com/gimme";

// Request a string response from the provided URL.
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, imageurl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Successful");;
                        try {
                            imageurl=response.getString("url");
                            Glide.with(MainActivity.this).load(imageurl).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progress.setVisibility(View.INVISIBLE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progress.setVisibility(View.INVISIBLE);
                                    return false;
                                }
                            }).into(ImageFrame);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                   
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "error hai");
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void next(View view) {
        loadMeme();
    }

    public void Share(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,imageurl);
        sendIntent.setType("text/plain");
        Intent chooser = Intent.createChooser(sendIntent,"share this meme meterial using");
        startActivity(chooser);

    }
}