package com.happycode.qrlink;

//activity que toca vídeos no youtube através da API deles

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;



public class YoutubePlayback extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private String VIDEO_ID;
    private Listener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        setContentView(R.layout.activity_youtube_playback);

        Intent i = getIntent();
        VIDEO_ID = i.getStringExtra("ID");                      //buscando ID do vídeo passado pelo intent

        listener = new Listener();

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);

        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);


    }



    @Override
    public void onInitializationFailure(Provider arg0,
                                        YouTubeInitializationResult error) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Oh no!"+error.toString(),Toast.LENGTH_LONG).show();


    }

    @Override
    public void onInitializationSuccess(Provider arg0, YouTubePlayer player,
                                        boolean arg2) {
        // TODO Auto-generated method stub
        player.setPlayerStateChangeListener(listener);
        player.setFullscreen(true);
        player.setShowFullscreenButton(false);
        player.loadVideo(VIDEO_ID);



    }

    private final class Listener implements YouTubePlayer.PlayerStateChangeListener {


        @Override
        public void onAdStarted() {

        }

        @Override
        public void onError(com.google.android.youtube.player.YouTubePlayer.ErrorReason arg0) {


        }

        @Override
        public void onLoaded(String arg0) {

        }

        @Override
        public void onLoading() {

        }

        @Override
        public void onVideoEnded() {
            finish();
        }   //voltar à tela inicial após término do vídeo

        @Override
        public void onVideoStarted() {

        }


    }
}

