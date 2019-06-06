package com.itfitness.planegame.gameimg

/**
 *
 * @ProjectName:    PlaneGame
 * @Package:        com.itfitness.planegame.gameimg
 * @ClassName:      Enemy
 * @Description:     java类作用描述 ：
 * @Author:         作者名：lml
 * @CreateDate:     2019/6/6 15:29
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/6/6 15:29
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface Enemy {
    /**
     * 获取宽度
     */
    fun getWidth():Int

    /**
     * 获取高度
     */
    fun getHeight():Int

    /**
     * 是否被击落
     */
    fun isExplode():Boolean
}