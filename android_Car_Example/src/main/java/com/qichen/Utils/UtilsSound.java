package com.qichen.Utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.qichen.ruida.R;

/**
 * Created by 35876 于萌萌
 * 创建日期: 10:45 . 2016年11月22日
 * 描述: 播放声音
 * <p>
 * <p>
 * 备注:
 */

public class UtilsSound {
    private static SoundPool soundPool;
    public  static void sound(Context context){
        soundPool = new SoundPool(10,AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(context, R.raw.collide, 1);

        soundPool.play(1, 1, 1, 0, 0, 1);

    }

}
