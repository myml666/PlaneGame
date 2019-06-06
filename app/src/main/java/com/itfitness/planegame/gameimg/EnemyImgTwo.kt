package com.itfitness.planegame.gameimg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.itfitness.planegame.R
import com.itfitness.planegame.utils.SoundPlayUtil
import com.itfitness.planegame.widget.GameView
import java.util.*

/**
 *
 * @ProjectName:    PlaneGame
 * @Package:        com.itfitness.planegame.gameimg
 * @ClassName:      MineImg
 * @Description:     java类作用描述 ：第一类敌人
 * @Author:         作者名：lml
 * @CreateDate:     2019/6/5 14:03
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/6/5 14:03
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class EnemyImgTwo:GameImg,Enemy {
    override fun isExplode(): Boolean {
        return mIsDown
    }

    private var mEnemyRes:Bitmap
    private lateinit var mEnemyImg:Bitmap
    private var mCtx: Context
    private var mImgIndex:Int = 0
    private var mTimeDelay:Int = 0
    private var mX:Int = 0
    private var mY:Int = 0
    private var mDisplayWidth:Int = 0
    private var mDisplayHeight:Int = 0
    private var mRandom:Random
    private var mIsDown:Boolean = false //是否被击落
    private var mImgs:ArrayList<Bitmap> //存放敌机的图片
    private var mExplodeImgs:ArrayList<Bitmap> //存放爆炸图片
    private var mLife:Int = 1 //生命是1
    constructor(ctx:Context,displayWidth:Int,displayHeight:Int,explodeImgs:ArrayList<Bitmap>){
        mCtx = ctx
        mEnemyRes = BitmapFactory.decodeResource(ctx.resources, R.drawable.dirend)
        mImgs = ArrayList()
//        初始化敌飞机的图片集合
        mImgs.add(mEnemyRes)
        mEnemyImg = mImgs[0]//初始化返回的第一张图片
        //        初始化爆炸照片的图片集合
        mExplodeImgs = explodeImgs
        mDisplayWidth = displayWidth
        mDisplayHeight = displayHeight
        mRandom = Random()
        mX = mRandom.nextInt(mDisplayWidth - mEnemyRes.width)
        mY = -mEnemyRes.height
    }
    /**
     * 是否被击落
     */
    fun isDown():Boolean{
        if(mIsDown){
            return true
        }
        for(bullet in GameView.mBullets.clone() as ArrayList<BulletImg>){
            if(mX - bullet.getX() < bullet.getWidth() && bullet.getX() - mX < mEnemyImg.width && bullet.getY()<mY+mEnemyImg.height && bullet.getY() >mY){
                GameView.mBullets.remove(bullet)
                mLife --
                if(mLife == 0){
                    SoundPlayUtil.play(SoundPlayUtil.VOICE_BOOM)
                    changeImg()
                    return true
                }
            }
        }
        return false
    }

    /**
     * 将飞机换为爆炸图片
     */
    fun changeImg(){
        mImgs = mExplodeImgs
    }
    override fun getWidth():Int {
        return mEnemyImg.width
    }

    override fun getHeight():Int {
        return mEnemyImg.height
    }
    override fun getImg(): Bitmap {
        mEnemyImg = (mImgs.clone() as ArrayList<Bitmap>).get(mImgIndex)
        if(mTimeDelay == 5){
            mImgIndex ++
            mTimeDelay = 0
            //爆炸结束移除飞机
            if(mImgIndex == mExplodeImgs.size-1){
                GameView.mGameImgs.remove(this)
            }
            if(mImgIndex == mImgs.size){
                mImgIndex = 0
            }
        }
        mTimeDelay ++
        mY += 8
        if(mY > mDisplayHeight){
            GameView.mGameImgs.remove(this)
        }
        mIsDown = isDown()
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