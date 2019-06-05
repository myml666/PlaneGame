package com.itfitness.planegame.gameimg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.itfitness.planegame.R

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
class MineImg:GameImg {
    private var mMineRes:Bitmap
    private var mMineImg:Bitmap
    private var mCtx: Context
    private var mImgIndex:Int = 0
    private var mTimeDelay:Int = 0
    private var mX:Int = 0
    private var mY:Int = 0
    private var mDisplayWidth:Int = 0
    private var mDisplayHeight:Int = 0
    constructor(ctx:Context,displayWidth:Int,displayHeight:Int){
        mCtx = ctx
        mMineRes = BitmapFactory.decodeResource(ctx.resources, R.drawable.my)
        mMineImg = Bitmap.createBitmap(mMineRes,0,0,mMineRes.width/4,mMineRes.height)
        mDisplayWidth = displayWidth
        mDisplayHeight = displayHeight
        mX = mDisplayWidth / 2 - mMineRes.width / 8
        mY = mDisplayHeight - mMineRes.height - 20
    }
    override fun getImg(): Bitmap {
        if(mTimeDelay == 10){
            mImgIndex ++
            mMineImg = Bitmap.createBitmap(mMineRes,mImgIndex * mMineRes.width/4,0,mMineRes.width/4,mMineRes.height)
            mTimeDelay = 0
            if(mImgIndex == 3){
                mImgIndex = 0
            }
        }
        mTimeDelay ++
        return mMineImg
    }

    /**
     * 是否选中战机
     */
    fun isSelect(x: Float, y: Float):Boolean{
        return x > mX && x < mX+mMineRes.width/4 && y > mY && y < mY+mMineRes.height
    }
    override fun getX(): Int {
        return mX
    }

    override fun getY(): Int {
        return mY
    }

    override fun setX(x:Int) {
        mX = x - mMineRes.width/8
    }

    override fun setY(y:Int){
        mY = y - mMineRes.height/2
    }

    /**
     * 获取己方飞机宽度
     */
    fun getWidth():Int{
        return mMineRes.width/4
    }
    /**
     * 获取己方飞机高度
     */
    fun getHeight():Int{
        return mMineRes.height
    }
}