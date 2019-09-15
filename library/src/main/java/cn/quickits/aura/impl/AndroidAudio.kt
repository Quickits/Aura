package cn.quickits.aura.impl

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import cn.quickits.aura.interfaces.Audio
import cn.quickits.aura.interfaces.Music
import cn.quickits.aura.interfaces.Sound


/**
 * @author Gavin Liu
 *
 * Created on 2019-09-06.
 */
internal class AndroidAudio constructor(context: Context) : Audio {

    private val maxStreams = 16

    private val srcQualityDefault = 0

    private val manager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private var soundPool: SoundPool

    val musics: ArrayList<AndroidMusic> = ArrayList()

    init {
        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(maxStreams).build()
        } else {
            SoundPool(maxStreams, AudioManager.STREAM_MUSIC, srcQualityDefault)
        }
    }

    override fun pause() {
        synchronized(musics) {
            musics.forEach { music ->
                if (music.isPlaying()) {
                    music.pause()
                    music.wasPlaying = true
                } else {
                    music.wasPlaying = false
                }
            }
        }
    }

    override fun resume() {
        synchronized(musics) {
            musics.forEach { music ->
                if (music.wasPlaying) music.play()
            }
        }
    }

    override fun newMusic(): Music {
        val music = AndroidMusic(audio = this)

        synchronized(musics) {
            musics.add(music)
        }

        return music
    }

    override fun newMusic(url: String): Music {
        val music = AndroidMusic(audio = this, url = url)

        synchronized(musics) {
            musics.add(music)
        }

        return music
    }

    override fun newMusic(id: Int): Music {
        val music = AndroidMusic(audio = this, resourceId = id)

        synchronized(musics) {
            musics.add(music)
        }

        return music
    }

    override fun newSound(url: String): Sound =
        AndroidSound(audio = this, soundPool = soundPool, url = url)

    override fun newSound(id: Int): Sound =
        AndroidSound(audio = this, soundPool = soundPool, resourceId = id)
}