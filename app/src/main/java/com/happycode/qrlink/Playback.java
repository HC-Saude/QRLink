package com.happycode.qrlink;

//activity que toca vídeos locais através de um VideoView

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class Playback extends Activity {

    private VideoView vid;
    private String cod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        setContentView(R.layout.activity_playback);

        vid = (VideoView) findViewById(R.id.videoView);

        Intent i = getIntent();
                                                                //buscando caminho do vídeo passado pelo intent
        cod = i.getStringExtra("src");

        //Toast.makeText(this, "Contents = " + cod, Toast.LENGTH_SHORT).show();


        Uri src = Uri.parse(cod);

        vid.setVideoURI(src);

        vid.setMediaController(new MediaController(this));

        vid.start();

        vid.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {        //voltar à tela inicial após término do vídeo
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                finish();
            }
        });



    }


    @Override
    public void onBackPressed() {

        finish();

    }

}
