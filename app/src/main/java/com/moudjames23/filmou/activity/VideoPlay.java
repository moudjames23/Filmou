package com.moudjames23.filmou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.moudjames23.filmou.R;
import com.moudjames23.filmou.app.Constant;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Moudjames23 on 6/11/2017.
 */
public class VideoPlay extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    @Bind(R.id.youtube_view)
    YouTubePlayerView youTubePlayerView;

    private int RECOVERY_REQUEST = 23;

    String VIDEO_ID = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);

        ButterKnife.bind(this);

        VIDEO_ID = getIntent().getStringExtra(Constant.VIDEO_ID);

        youTubePlayerView.initialize(Constant.YOUTUBE_TOKEN, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.loadVideo(VIDEO_ID);
        youTubePlayer.setFullscreen(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if(youTubeInitializationResult.isUserRecoverableError())
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        else
        {
            Toast.makeText(this, "Erreur video", Toast.LENGTH_SHORT).show();
            Log.d("Youtube: ", "" +youTubeInitializationResult.toString());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Constant.YOUTUBE_TOKEN, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerView;
    }
}
