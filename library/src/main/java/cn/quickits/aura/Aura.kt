package cn.quickits.aura

import android.annotation.SuppressLint
import android.content.Context
import cn.quickits.aura.impl.AndroidAudio
import cn.quickits.aura.interfaces.Audio
import cn.quickits.aura.interfaces.Music
import cn.quickits.aura.interfaces.Sound


/**
 * @author Gavin Liu
 *
 * Created on 2019-09-06.
 */
@SuppressLint("StaticFieldLeak")
object Aura : Audio {

    private lateinit var audio: Audio

    internal lateinit var context: Context

    @JvmStatic
    fun init(context: Context) {
        this.context = context.applicationContext
        audio = AndroidAudio(context)
    }

    override fun pause() = audio.pause()

    override fun resume() = audio.resume()

    override fun newMusic(): Music = audio.newMusic()

    override fun newMusic(url: String): Music = audio.newMusic(url)

    override fun newMusic(id: Int): Music = audio.newMusic(id)

    override fun newSound(url: String): Sound = audio.newSound(url)

    override fun newSound(id: Int): Sound = audio.newSound(id)

}