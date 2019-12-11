package com.itfitness.planegame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.itfitness.planegame.widget.GameView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView mImgStart;
    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgStart = findViewById(R.id.img_start);
        mImgStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });
        initPlayer();
    }

    private void initPlayer() {
       player = MediaPlayer.create(this,R.raw.game_bgm);
       player.setLooping(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(player!=null){
            player.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(player!=null){
            player.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player!=null){
            player.stop();
        }
    }
}
