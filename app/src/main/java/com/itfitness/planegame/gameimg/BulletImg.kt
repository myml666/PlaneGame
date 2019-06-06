package com.itfitness.planegame.gameimg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.itfitness.planegame.R
import com.itfitness.planegame.widget.GameView

/**
 *
 * @ProjectName:    PlaneGame
 * @Package:        com.itfitness.planegame.gameimg
 * @ClassName:      BulletImg
 * @Description:     java类作用描述 ：子弹
 * @Author:         作者名：lml
 * @CreateDate:     2019/6/5 15:47
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/6/5 15:47
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class BulletImg:GameImg {
    private var mBulletRes:Bitmap
    private var mX:Int = 0
    private var mY:Int = 0
    constructor(ctx:Context,minePlane:MineImg){
        mBulletRes = BitmapFactory.decodeResource(ctx.resources, R.drawable.yaling)
        mX = minePlane.getX() + minePlane.getWidth() / 2 -mBulletRes.width/2
        mY = minePlane.getY() - 10
    }

    /**
     * 获取子弹的宽度
     */
    fun getWidth():Int{
        return mBulletRes.width
    }
    /**
     * 获取子弹的高度
     */
    fun getHeight():Int{
        return mBulletRes.height
    }
    override fun getImg(): Bitmap {
        mY -= 10
        if(mY+mBulletRes.height<0){
            GameView.mBullets.remove(this)
        }
        return mBulletRes
    }

    override fun getX(): Int {
        return mX
    }

    override fun getY(): Int {
        return mY
    }

    override fun setX(x: Int) {
    }

    override fun setY(y: Int) {
    }
}