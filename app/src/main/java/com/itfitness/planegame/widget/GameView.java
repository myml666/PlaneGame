package com.itfitness.planegame.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.itfitness.planegame.gameimg.BgImg;
import com.itfitness.planegame.gameimg.BulletImg;
import com.itfitness.planegame.gameimg.EnemyImg;
import com.itfitness.planegame.gameimg.GameImg;
import com.itfitness.planegame.gameimg.MineImg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ProjectName: PlaneGame
 * @Package: com.itfitness.planegame.widget
 * @ClassName: GameView
 * @Description: java类作用描述 ：
 * @Author: 作者名：lml
 * @CreateDate: 2019/5/29 17:28
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/5/29 17:28
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable,View.OnTouchListener{
    private int mWidth,mHeight;
    private boolean mGameStatus = true;
    public static ArrayList<GameImg> mGameImgs;
    public static ArrayList<BulletImg> mBullets;
    private Context mContext;
    private MineImg mMineImg;
    public GameView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        if(mGameImgs == null){
            mGameImgs = new ArrayList<>();
        }
        if(mBullets == null){
            mBullets = new ArrayList<>();
        }
        setOnTouchListener(this);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mGameStatus = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mWidth = width;
        mHeight = height;
        mGameImgs.clear();
        mGameImgs.add(new BgImg(mContext,mWidth,mHeight));
        mGameImgs.add(new MineImg(mContext,mWidth,mHeight));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mGameStatus = false;
    }

    @Override
    public void run() {
        int enemyCreateFlag = 0;
        int bulletCreateFlag = 0;
        while (mGameStatus){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//清屏
            if(enemyCreateFlag == 50){
                mGameImgs.add(new EnemyImg(mContext,mWidth,mHeight));
                enemyCreateFlag = 0;
            }
            if(bulletCreateFlag == 5){
                if(mMineImg!=null){
                    mBullets.add(new BulletImg(mContext,mMineImg));
                }
                bulletCreateFlag = 0;
            }
            bulletCreateFlag++;
            enemyCreateFlag++;
            ArrayList<GameImg> clone = (ArrayList<GameImg>) mGameImgs.clone();
            for (GameImg gameImg : clone){
                canvas.drawBitmap(gameImg.getImg(),gameImg.getX(),gameImg.getY(),null);
            }
            for(BulletImg bulletImg:(ArrayList<BulletImg>) mBullets.clone()){
                canvas.drawBitmap(bulletImg.getImg(),bulletImg.getX(),bulletImg.getY(),null);
            }
            getHolder().unlockCanvasAndPost(canvas);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                for (GameImg gameImg : mGameImgs){
                    if(gameImg instanceof MineImg){
                        MineImg mine = (MineImg) gameImg;
                        if(mine.isSelect(event.getX(), event.getY())){
                            mMineImg = mine;
                        }
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(mMineImg !=null){
                    mMineImg.setX((int) event.getX());
                    mMineImg.setY((int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                mMineImg = null;
                break;
        }
        return true;
    }
}
