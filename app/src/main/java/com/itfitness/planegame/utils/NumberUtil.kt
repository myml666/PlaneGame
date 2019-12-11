package com.itfitness.planegame.utils

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * 格式化数字
 * Created by Administrator on 2019/12/11.
 */
object NumberUtil {
    private var decimalFormat: DecimalFormat = DecimalFormat("0")

    init {
        decimalFormat.roundingMode = RoundingMode.FLOOR//不四舍五入
    }
    /**
     * 获取小数的整数部分
     * @param num
     * @return
     */
    fun getIntNum(num: Double): Int {
        return Integer.valueOf(decimalFormat.format(num))
    }
}