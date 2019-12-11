package com.itfitness.planegame;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.itfitness.planegame.widget.GameView;

/**
 * Created by Administrator on 2019/12/10.
 */

public class GameActivity extends AppCompatActivity {
    private GameView mGameView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameView = new GameView(this);
        setContentView(mGameView);
    }
}
