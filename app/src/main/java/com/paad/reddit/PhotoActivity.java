package com.paad.reddit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class PhotoActivity extends AppCompatActivity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo);

        Intent intent = getIntent();

        String thumbnail = intent.getStringExtra("url");

        image = (ImageView) findViewById(R.id.photo);


        if (thumbnail.contains("self") || thumbnail.contains("default")) {

            Picasso.with(this).load(R.drawable.noimage).fit().centerCrop().into(image);
        } else {


            Picasso.with(this).load(thumbnail).fit().centerCrop().into(image);

        }


    }
}
