package com.vento.newsfeedsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.vento.newsfeedsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String imgNew,author , title , url ,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        init();
        initView();

    }

    public void init(){
        imgNew = getIntent().getExtras().getString("getUrlToImage");
        author = getIntent().getExtras().getString("author");
        title = getIntent().getExtras().getString("title");
        url = getIntent().getExtras().getString("getUrl");
        description = getIntent().getExtras().getString("description");
    }

    public void initView(){
        binding.btnMainOpenWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        binding.tvAuthorMainNews.setText(author);
        binding.tvDescMainNews.setText(description);
        binding.tvTitleMainNews.setText(title);

        if (imgNew.isEmpty()) {
            binding.ivImageMainNews.setImageResource(R.drawable.no_photo);
        } else{
            Picasso.get()
                    .load(imgNew)
                    .placeholder(R.drawable.no_photo)
                    .error(android.R.drawable.stat_notify_error)
                    .into(binding.ivImageMainNews);
        }
    }
}