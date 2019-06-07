package com.itfitness.planegame.gameimg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import com.itfitness.planegame.R
import com.itfitness.planegame.utils.SoundPlayUtil
import com.itfitness.planegame.widget.GameView
import java.util.ArrayList

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
    private lateinit var mMineImg:Bitmap
    private var mCtx: Context
    private var mImgIndex:Int = 0
    private var mTimeDelay:Int = 0
    private var mX:Int = 0
    private var mY:Int = 0
    private var mIsDown:Boolean = false //是否被击落
    private var mDisplayWidth:Int = 0
    private var mDisplayHeight:Int = 0
    private var mImgs:ArrayList<Bitmap> //存放我方的图片
    private var mExplodeImgs:ArrayList<Bitmap> //存放爆炸图片
    private var mGameOver:GameOver //游戏结束的回调
    constructor(ctx:Context,displayWidth:Int,displayHeight:Int,explodeImgs:ArrayList<Bitmap>,gameOver:GameOver){
        mCtx = ctx
        mGameOver = gameOver
        mMineRes = BitmapFactory.decodeResource(ctx.resources, R.drawable.mine)
        mImgs = ArrayList()
        mImgs.add(Bitmap.createBitmap(mMineRes,0,0,mMineRes.width/2,mMineRes.height))
        mImgs.add(Bitmap.createBitmap(mMineRes,1 * mMineRes.width/2,0,mMineRes.width/2,mMineRes.height))
        mExplodeImgs = explodeImgs
        mDisplayWidth = displayWidth
        mDisplayHeight = displayHeight
        mX = mDisplayWidth / 2 - mMineRes.width / 4
        mY = mDisplayHeight - mMineRes.height - 20
    }
    override fun getImg(): Bitmap {
        mMineImg = mImgs[mImgIndex]
        if(mTimeDelay == 5){
            mImgIndex ++
            if(mImgIndex == mExplodeImgs.size){
                mGameOver.onGameOver()//游戏结束
                Log.e("结果","完蛋了")
            }
            if(mImgIndex == mImgs.size){
                mImgIndex = 0
            }
            mTimeDelay = 0
        }
        isDown()
        mTimeDelay ++
        return mMineImg
    }
    /**
     * 将飞机换为爆炸图片
     */
    fun changeImg(){
        mImgs = mExplodeImgs
    }
    /**
     * 是否选中战机
     */
    fun isSelect(x: Float, y: Float):Boolean{
        return x > mX && x < mX+mMineRes.width/2 && y > mY && y < mY+mMineRes.height
    }
    /**
     * 是否被坠毁
     */
    fun isDown():Boolean{
        if(mIsDown){
            return true
        }
        for(gameimg in GameView.mGameImgs.clone() as ArrayList<GameImg>){
            if(gameimg is Enemy){
                if (!gameimg.isExplode()){
                    if(gameimg.getX() - mX < getWidth() && mX - gameimg.getX() < gameimg.getWidth()  && mY < gameimg.getY()+gameimg.getHeight() && mY >gameimg.getY()){
                        SoundPlayUtil.play(SoundPlayUtil.VOICE_GAMEOVER)
                        changeImg()
                        return true
                    }
                }
            }
        }
        return false
    }
    override fun getX(): Int {
        return mX
    }

    override fun getY(): Int {
        return mY
    }

    override fun setX(x:Int) {
        mX = x - mMineRes.width/4
    }

    override fun setY(y:Int){
        mY = y - mMineRes.height/2
    }

    /**
     * 获取己方飞机宽度
     */
    fun getWidth():Int{
        return mMineRes.width/2
    }
    /**
     * 获取己方飞机高度
     */
    fun getHeight():Int{
        return mMineRes.height
    }
}