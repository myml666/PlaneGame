package com.itfitness.planegame.gameimg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.itfitness.planegame.R
import com.itfitness.planegame.widget.GameView
import java.util.*

/**
 *
 * @ProjectName:    PlaneGame
 * @Package:        com.itfitness.planegame.gameimg
 * @ClassName:      MineImg
 * @Description:     java类作用描述 ：
 * @Author:         作者名：lml
 * @CreateDate:     2019/6/5 14:03
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/6/5 14:03
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class EnemyImg:GameImg {
    private var mEnemyRes:Bitmap
    private var mEnemyImg:Bitmap
    private var mCtx: Context
    private var mImgIndex:Int = 0
    private var mTimeDelay:Int = 0
    private var mX:Int = 0
    private var mY:Int = 0
    private var mDisplayWidth:Int = 0
    private var mDisplayHeight:Int = 0
    private var mRandom:Random
    constructor(ctx:Context,displayWidth:Int,displayHeight:Int){
        mCtx = ctx
        mEnemyRes = BitmapFactory.decodeResource(ctx.resources, R.drawable.diren)
        mEnemyImg = Bitmap.createBitmap(mEnemyRes,0,0,mEnemyRes.width/4,mEnemyRes.height)
        mDisplayWidth = displayWidth
        mDisplayHeight = displayHeight
        mRandom = Random()
        mX = mRandom.nextInt(mDisplayWidth - mEnemyRes.width/4)
        mY = -mEnemyImg.height
    }
    override fun getImg(): Bitmap {
        if(mTimeDelay == 10){
            mImgIndex ++
            mEnemyImg = Bitmap.createBitmap(mEnemyRes,mImgIndex * mEnemyRes.width/4,0,mEnemyRes.width/4,mEnemyRes.height)
            mTimeDelay = 0
            if(mImgIndex == 3){
                mImgIndex = 0
            }
        }
        mTimeDelay ++
        mY += 8
        if(mY > mDisplayHeight){
            GameView.mGameImgs.remove(this)
        }
        return mEnemyImg
    }
    override fun getX(): Int {
        return mX
    }

    override fun getY(): Int {
        return mY
    }

    override fun setX(x:Int) {
        mX = x
    }

    override fun setY(y:Int){
        mY = y
    }
}