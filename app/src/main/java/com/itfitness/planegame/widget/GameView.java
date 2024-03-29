package com.itfitness.planegame.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.itfitness.planegame.GameActivity;
import com.itfitness.planegame.MainActivity;
import com.itfitness.planegame.R;
import com.itfitness.planegame.common.Common;
import com.itfitness.planegame.gameimg.BgImg;
import com.itfitness.planegame.gameimg.BulletImg;
import com.itfitness.planegame.gameimg.EnemyImgOne;
import com.itfitness.planegame.gameimg.EnemyImgTwo;
import com.itfitness.planegame.gameimg.GameImg;
import com.itfitness.planegame.gameimg.GameOver;
import com.itfitness.planegame.gameimg.MineImg;
import com.itfitness.planegame.utils.NumberUtil;
import com.itfitness.planegame.utils.SoundPlayUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {
    private int mWidth, mHeight;
    private boolean mGameStatus = true;
    public static ArrayList<GameImg> mGameImgs;
    public static ArrayList<BulletImg> mBullets;
    private ArrayList<Bitmap> mExplodeImgs;
    private Context mContext;
    private MineImg mMineImg;
    private Random mRandom;
    public static int score = 0;//分数
    private Paint mPaint;//文字画笔
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("提示");
            builder.setMessage("游戏结束，总分数为：" + score);
            builder.setNegativeButton("返回首页", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
//                    ((GameActivity)mContext).finish();
                }
            });
            builder.setPositiveButton("在来一局", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Common.BOSS_CREATETIME = 100.00;
                    Common.BOSS_SPEED = 5;
                    mGameStatus = true;
                    mGameImgs.clear();
                    mBullets.clear();
                    mGameImgs.add(new BgImg(mContext, mWidth, mHeight));
                    mGameImgs.add(new MineImg(mContext, mWidth, mHeight, mExplodeImgs, new GameOver() {
                        @Override
                        public void onGameOver() {
                            mGameStatus = false;
                            mHandler.sendEmptyMessage(0);
                        }
                    }));
                    score = 0;
                    new Thread(GameView.this).start();
                }
            });
            builder.create().show();
        }
    };
    private int mTextHeight;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    /**
     * 销毁数据
     */
    public void destory() {
        if (mGameImgs != null) {
            mGameImgs.clear();
            mGameImgs = null;
        }
        if (mBullets != null) {
            mBullets.clear();
            mBullets = null;
        }
        if (mExplodeImgs != null) {
            mExplodeImgs.clear();
            mExplodeImgs = null;
        }
        score = 0;
        mGameStatus = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        if (mGameImgs == null) {
            mGameImgs = new ArrayList<>();
        }
        if (mBullets == null) {
            mBullets = new ArrayList<>();
        }
        if (mExplodeImgs == null) {
            mExplodeImgs = new ArrayList<>();
        }
        Common.BOSS_CREATETIME = 100.00;
        Common.BOSS_SPEED = 5;
        SoundPlayUtil.INSTANCE.init(mContext);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setStrokeWidth(3);
        mRandom = new Random();
        Bitmap explodeRes = BitmapFactory.decodeResource(getResources(), R.drawable.baozha);
        mExplodeImgs.add(Bitmap.createBitmap(explodeRes, 0, 0, explodeRes.getWidth() / 4, explodeRes.getHeight() / 2));
        mExplodeImgs.add(Bitmap.createBitmap(explodeRes, explodeRes.getWidth() / 4, 0, explodeRes.getWidth() / 4, explodeRes.getHeight() / 2));
        mExplodeImgs.add(Bitmap.createBitmap(explodeRes, 2 * explodeRes.getWidth() / 4, 0, explodeRes.getWidth() / 4, explodeRes.getHeight() / 2));
        mExplodeImgs.add(Bitmap.createBitmap(explodeRes, 3 * explodeRes.getWidth() / 4, 0, explodeRes.getWidth() / 4, explodeRes.getHeight() / 2));
        mExplodeImgs.add(Bitmap.createBitmap(explodeRes, 0, explodeRes.getHeight() / 2, explodeRes.getWidth() / 4, explodeRes.getHeight() / 2));
        mExplodeImgs.add(Bitmap.createBitmap(explodeRes, explodeRes.getWidth() / 4, explodeRes.getHeight() / 2, explodeRes.getWidth() / 4, explodeRes.getHeight() / 2));
        mExplodeImgs.add(Bitmap.createBitmap(explodeRes, 2 * explodeRes.getWidth() / 4, explodeRes.getHeight() / 2, explodeRes.getWidth() / 4, explodeRes.getHeight() / 2));
        mExplodeImgs.add(Bitmap.createBitmap(explodeRes, 3 * explodeRes.getWidth() / 4, explodeRes.getHeight() / 2, explodeRes.getWidth() / 4, explodeRes.getHeight() / 2));
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
        mPaint.setTextSize(mWidth / 14);//设置字体大小
        Rect rect = new Rect();
        mPaint.getTextBounds("分数", 0, 1, rect);
        mTextHeight = rect.height();
        mGameImgs.clear();
        mGameImgs.add(new BgImg(mContext, mWidth, mHeight));
        mGameImgs.add(new MineImg(mContext, mWidth, mHeight, mExplodeImgs, new GameOver() {
            @Override
            public void onGameOver() {
                mGameStatus = false;
                mHandler.sendEmptyMessage(0);
            }
        }));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        destory();
    }

    @Override
    public void run() {
        double enemyCreateFlag = 0.00;
        int bulletCreateFlag = 0;
        while (mGameStatus) {
            Canvas canvas = getHolder().lockCanvas();
            if (canvas == null) {
                break;
            }
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//清屏
            if (enemyCreateFlag == NumberUtil.INSTANCE.getIntNum(Common.BOSS_CREATETIME)) {
                Log.e("测试",Common.BOSS_CREATETIME+"==="+Common.BOSS_SPEED);
                int random = mRandom.nextInt(10);
                if (random < 7) {
                    mGameImgs.add(new EnemyImgTwo(mContext, mWidth, mHeight, mExplodeImgs));
                } else {
                    mGameImgs.add(new EnemyImgOne(mContext, mWidth, mHeight, mExplodeImgs));
                }
                //到5就不再加快了
                if (Common.BOSS_CREATETIME > 5) {
                    Common.BOSS_CREATETIME -= 0.8;
                    Common.BOSS_SPEED += 0.2;
                }
                enemyCreateFlag = 0;
            }
            //子弹发射的速度
            if (bulletCreateFlag == 20) {
                if (mMineImg != null) {
                    SoundPlayUtil.INSTANCE.play(SoundPlayUtil.INSTANCE.getVOICE_SHOOT());
                    mBullets.add(new BulletImg(mContext, mMineImg));
                }
                bulletCreateFlag = 0;
            }
            bulletCreateFlag++;
            enemyCreateFlag += 1;
            ArrayList<GameImg> clone = (ArrayList<GameImg>) mGameImgs.clone();
            for (GameImg gameImg : clone) {
                canvas.drawBitmap(gameImg.getImg(), gameImg.getX(), gameImg.getY(), null);
            }
            for (BulletImg bulletImg : (ArrayList<BulletImg>) mBullets.clone()) {
                canvas.drawBitmap(bulletImg.getImg(), bulletImg.getX(), bulletImg.getY(), null);
            }
            canvas.drawText("分数：" + score, 20, mTextHeight + 20, mPaint);
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (GameImg gameImg : mGameImgs) {
                    if (gameImg instanceof MineImg) {
                        MineImg mine = (MineImg) gameImg;
                        if (mine.isSelect(event.getX(), event.getY())) {
                            mMineImg = mine;
                        }
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mMineImg != null) {
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
