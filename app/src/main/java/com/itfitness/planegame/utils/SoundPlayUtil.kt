package com.itfitness.planegame.utils

import android.content.Context
import android.media.SoundPool
import android.media.AudioManager
import android.media.AudioAttributes
import android.os.Build
import android.support.annotation.RequiresApi
import com.itfitness.planegame.R


/**
 *
 * @ProjectName:    PlaneGame
 * @Package:        com.itfitness.planegame.utils
 * @ClassName:      SoundPlayUtil
 * @Description:     java类作用描述 ：
 * @Author:         作者名：lml
 * @CreateDate:     2019/6/6 17:01
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/6/6 17:01
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
object SoundPlayUtil{
    private lateinit var mSoundPool:SoundPool
    private var mIsLoadSuccess:Boolean = false
    var VOICE_SHOOT:Int = 0//子弹声音
    var VOICE_BOOM:Int = 0//爆炸声音
    var VOICE_GAMEOVER:Int = 0//游戏结束声音
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            /**
             * 初始化声音
             */
    fun init(ctx:Context){
        if (Build.VERSION.SDK_INT >= 21) {
            val builder = SoundPool.Builder()
            //传入最多播放音频数量,
            builder.setMaxStreams(10)
            //AudioAttributes是一个封装音频各种属性的方法
            val attrBuilder = AudioAttributes.Builder()
            //设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC)
            //加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build())
            mSoundPool = builder.build()
        } else {
            /**
             * 第一个参数：int maxStreams：SoundPool对象的最大并发流数
             * 第二个参数：int streamType：AudioManager中描述的音频流类型
             * 第三个参数：int srcQuality：采样率转换器的质量。 目前没有效果。 使用0作为默认值。
             */
            mSoundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        }
        VOICE_SHOOT = mSoundPool.load(ctx, R.raw.shot, 1)
        VOICE_BOOM = mSoundPool.load(ctx, R.raw.bomb, 1)
        VOICE_GAMEOVER = mSoundPool.load(ctx, R.raw.gameover, 1)
        mSoundPool.setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener { soundPool, sampleId, status ->
            if (status == 0) {
                //第一个参数soundID
                //第二个参数leftVolume为左侧音量值（范围= 0.0到1.0）
                //第三个参数rightVolume为右的音量值（范围= 0.0到1.0）
                //第四个参数priority 为流的优先级，值越大优先级高，影响当同时播放数量超出了最大支持数时SoundPool对该流的处理
                //第五个参数loop 为音频重复播放次数，0为值播放一次，-1为无限循环，其他值为播放loop+1次
                //第六个参数 rate为播放的速率，范围0.5-2.0(0.5为一半速率，1.0为正常速率，2.0为两倍速率)
//                soundPool.play(voiceId, 1f, 1f, 1, 0, 1f)
                mIsLoadSuccess = true
            }
        })
    }
    fun getSoundPool():SoundPool{
        return mSoundPool
    }
    /**
     * 播放声音
     */
    fun play(voiceId:Int){
        if(mIsLoadSuccess){
            VoiceThread(voiceId).start()
        }
    }
}
class VoiceThread(voiceId:Int):Thread(){
    private var mVId:Int = voiceId
    override fun run() {
        SoundPlayUtil.getSoundPool().play(mVId,1f,1f,1,0,1f)
        super.run()
    }
}