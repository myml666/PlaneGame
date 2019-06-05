package com.itfitness.planegame.gameimg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import com.itfitness.planegame.R

/**
 *
 * @ProjectName:    PlaneGame
 * @Package:        com.itfitness.planegame.gameimg
 * @ClassName:      BgImg
 * @Description:     java类作用描述 ：
 * @Author:         作者名：lml
 * @CreateDate:     2019/6/5 11:18
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/6/5 11:18
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class BgImg:GameImg {
    override fun getX(): Int {
        return 0
    }

    override fun getY(): Int {
        return 0
    }

    override fun setX(x:Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setY(y:Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mBgImg:Bitmap
    private var mCtx:Context
    private var mDisplayWidth:Int = 0
    private var mDisplayHeight:Int = 0
    private var mMoveHeight:Int = 0
    private var mBgRes:Bitmap
    constructor(ctx:Context,displayWidth:Int,displayHeight:Int){
        mCtx = ctx
        mDisplayWidth = displayWidth
        mDisplayHeight = displayHeight
        mBgImg = Bitmap.createBitmap(mDisplayWidth,mDisplayHeight,Bitmap.Config.ARGB_8888)
        mBgRes = BitmapFactory.decodeResource(mCtx.resources, R.drawable.bg)
    }
    /**
     * 获取图像
     */
    override fun getImg(): Bitmap {
        val canvas:Canvas = Canvas(mBgImg)
        canvas.drawBitmap(mBgRes, Rect(0,0,mBgRes.width,mBgRes.height),
                Rect(0,mMoveHeight,mDisplayWidth,mDisplayHeight+mMoveHeight),null)
        canvas.drawBitmap(mBgRes, Rect(0,0,mBgRes.width,mBgRes.height),
                Rect(0,-mDisplayHeight+mMoveHeight,mDisplayWidth,mMoveHeight),null)
        mMoveHeight+=3
        if(mMoveHeight>=mDisplayHeight){
            mMoveHeight = 0
        }
        return mBgImg
    }

}