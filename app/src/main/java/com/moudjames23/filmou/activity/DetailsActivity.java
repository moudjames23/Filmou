package com.moudjames23.filmou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moudjames23.filmou.R;
import com.moudjames23.filmou.app.Constant;
import com.moudjames23.filmou.model.Film;
import com.moudjames23.filmou.networkapi.Utils;
import com.squareup.picasso.Picasso;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.cover)
    ImageView imgCover;


    @Bind(R.id.tv_resume)
    TextView tvResume;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.detail_youtube)
    ImageView imgDetailsYoutube;

    private String VIDEO_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Film film = (Film)getIntent().getSerializableExtra("DATA");

        Log.d("YOUTUBE", "onCreate: " +film.toString());

        this.setTitle(film.getTitre());

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

        ButterKnife.bind(this);

        collapsingToolbarLayout.setExpandedTitleColor(getColor(android.R.color.transparent));

        tvResume.setText(film.getResume());

        Picasso.with(this)
                .load(Constant.IMAGE_URL+ film.getPoster())
                .into(imgCover);


        VIDEO_ID = Utils.getYoutubeVideoId(film.getTrailer());
        String URL_THUMBNAIL = "http://img.youtube.com/vi/"+VIDEO_ID+ "/0.jpg";

        Picasso.with(this)
                .load(URL_THUMBNAIL)
                .into(imgDetailsYoutube);

        imgDetailsYoutube.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, VideoPlay.class);
        intent.putExtra(Constant.VIDEO_ID, VIDEO_ID);

        startActivity(intent);
    }
}
