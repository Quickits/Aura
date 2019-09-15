package cn.quickits.aura.impl

import android.media.SoundPool
import cn.quickits.aura.Aura
import cn.quickits.aura.interfaces.Sound
import kotlin.math.abs


/**
 * @author Gavin Liu
 *
 * Created on 2019-09-06.
 */
internal class AndroidSound(
    private val audio: AndroidAudio,
    private val soundPool: SoundPool,
    url: String? = null,
    rawId: Int? = null
) : Sound {

    private var soundId: Int = 0

    private val streamIds = arrayListOf<Int>()

    init {
        rawId?.let { soundId = soundPool.load(Aura.context.resources.openRawResourceFd(it), 1) }

        url?.let { soundId = soundPool.load(it, 1) }
    }

    override fun play(): Int = play(1f)

    override fun play(volume: Float): Int {
        if (streamIds.size == 8) streamIds.remove(0)

        val streamId = soundPool.play(soundId, volume, volume, 1, 0, 1f)

        if (streamId == 0) return -1

        streamIds.add(streamId)
        return streamId
    }

    override fun play(volume: Float, pitch: Float, pan: Float): Int {
        if (streamIds.size == 8) streamIds.remove(0)
        var leftVolume = volume
        var rightVolume = volume
        if (pan < 0) {
            rightVolume *= (1 - abs(pan))
        } else if (pan > 0) {
            leftVolume *= (1 - abs(pan))
        }
        val streamId = soundPool.play(soundId, leftVolume, rightVolume, 1, 0, pitch)

        if (streamId == 0) return -1

        streamIds.add(streamId)
        return streamId
    }

    override fun loop(): Int = loop(1f)

    override fun loop(volume: Float): Int {
        if (streamIds.size == 8) streamIds.remove(0)

        val streamId = soundPool.play(soundId, volume, volume, 1, -1, 1f)

        if (streamId == 0) return -1

        streamIds.add(streamId)
        return streamId
    }

    override fun loop(volume: Float, pitch: Float, pan: Float): Int {
        if (streamIds.size == 8) streamIds.remove(0)
        var leftVolume = volume
        var rightVolume = volume
        if (pan < 0) {
            rightVolume *= (1 - abs(pan))
        } else if (pan > 0) {
            leftVolume *= (1 - abs(pan))
        }
        val streamId = soundPool.play(soundId, leftVolume, rightVolume, -1, 0, pitch)

        if (streamId == 0) return -1

        streamIds.add(streamId)
        return streamId
    }

    override fun stop() {
        streamIds.forEach { soundPool.stop(it) }
    }

    override fun pause() {
        soundPool.autoPause()
    }

    override fun resume() {
        soundPool.autoResume()
    }

    override fun stop(streamId: Int) {
        soundPool.stop(streamId)
    }

    override fun pause(streamId: Int) {
        soundPool.pause(streamId)
    }

    override fun resume(streamId: Int) {
        soundPool.resume(streamId)
    }

    override fun setLooping(streamId: Int, looping: Boolean) {
        soundPool.setLoop(streamId, if (looping) -1 else 0)
    }

    override fun setPitch(streamId: Int, pitch: Float) {
        soundPool.setRate(streamId, pitch)
    }

    override fun setVolume(streamId: Int, volume: Float) {
        soundPool.setVolume(streamId, volume, volume)
    }

    override fun setPan(streamId: Int, pan: Float, volume: Float) {
        var leftVolume = volume
        var rightVolume = volume
        if (pan < 0) {
            rightVolume *= (1 - abs(pan))
        } else if (pan > 0) {
            leftVolume *= (1 - abs(pan))
        }
        soundPool.setVolume(streamId, leftVolume, rightVolume)
    }

    override fun dispose() {
        stop()
        soundPool.unload(soundId)
        streamIds.clear()
    }

}