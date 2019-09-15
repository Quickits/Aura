package cn.quickits.aura.impl

import cn.quickits.aura.Aura
import cn.quickits.aura.interfaces.Music
import cn.quickits.aura.util.RawDataSourceProvider
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.io.IOException
import kotlin.math.abs


/**
 * @author Gavin Liu
 *
 * Created on 2019-09-06.
 */
internal class AndroidMusic constructor(
    val audio: AndroidAudio,
    var url: String? = null,
    var rawId: Int? = null
) : Music {

    var wasPlaying = false

    private var player: IjkMediaPlayer = IjkMediaPlayer()

    private var isPrepared = false

    private var volume = 1f

    private var mOnCompletionListener: Music.OnCompletionListener? = null

    init {
        player.reset()
        player.setOnPreparedListener {
            isPrepared = true
            player.start()
        }

        player.setOnCompletionListener {
            mOnCompletionListener?.onCompletion(this)
        }
    }

    override fun play() {
        if (player.isPlaying) return

        if (isPrepared) {
            player.start()
            return
        }

        url?.let { prepare(it) }

        rawId?.let { prepare(it) }
    }

    override fun play(url: String) {
        this.rawId = null
        this.url = url
        isPrepared = false
        play()
    }

    override fun play(rawId: Int) {
        this.rawId = rawId
        this.url = null
        isPrepared = false
        play()
    }

    override fun pause() {
        try {
            if (player.isPlaying) {
                player.pause()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        wasPlaying = false
    }

    override fun stop() {
        if (isPrepared) {
            player.seekTo(0)
        }

        player.stop()
        isPrepared = false
    }

    override fun isPlaying(): Boolean {
        return try {
            player.isPlaying
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun setLooping(isLooping: Boolean) {
        player.isLooping = isLooping
    }

    override fun isLooping(): Boolean {
        return try {
            player.isLooping
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun setVolume(volume: Float) {
        this.volume = volume
        player.setVolume(volume, volume)
    }

    override fun getVolume(): Float = volume

    override fun setPan(pan: Float, volume: Float) {
        var leftVolume = volume
        var rightVolume = volume

        if (pan < 0) {
            rightVolume *= 1 - abs(pan)
        } else if (pan > 0) {
            leftVolume *= 1 - abs(pan)
        }

        player.setVolume(leftVolume, rightVolume)
        this.volume = volume
    }

    override fun setPosition(position: Int) {
        try {
            if (!isPrepared) {
                player.prepareAsync()
            }
            player.seekTo(position.toLong())
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun getPosition(): Int = player.currentPosition.toInt()

    override fun getDuration(): Int = player.duration.toInt()

    override fun setOnCompletionListener(listener: Music.OnCompletionListener) {
        this.mOnCompletionListener = listener
    }

    override fun dispose() {
        val time = System.currentTimeMillis()
        try {
            player.release()
        } catch (t: Throwable) {
            t.printStackTrace()
        } finally {
            mOnCompletionListener = null

            synchronized(audio.musics) {
                audio.musics.remove(this)
            }
        }
    }

    private fun prepare(source: String) {
        try {
            player.reset()
            player.dataSource = source
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }

        player.prepareAsync()
    }

    private fun prepare(id: Int) {
        try {
            player.reset()
            player.setDataSource(RawDataSourceProvider(Aura.context.resources.openRawResourceFd(id)))
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        player.prepareAsync()
    }
}