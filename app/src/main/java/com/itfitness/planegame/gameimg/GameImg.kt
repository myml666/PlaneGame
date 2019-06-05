package com.itfitness.planegame.gameimg

import android.graphics.Bitmap

/**
 *
 * @ProjectName:    PlaneGame
 * @Package:        com.itfitness.planegame.gameimg
 * @ClassName:      GameImg
 * @Description:     java类作用描述 ：
 * @Author:         作者名：lml
 * @CreateDate:     2019/6/5 11:19
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/6/5 11:19
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface GameImg {
    /**
     * 获取图像
     */
    fun getImg():Bitmap

    /**
     * 设置图像坐标
     */
    fun getX():Int
    fun getY():Int
    fun setX(x:Int)
    fun setY(y:Int)
}