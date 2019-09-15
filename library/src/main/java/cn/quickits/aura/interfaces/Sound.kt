package cn.quickits.aura.interfaces

/**
 * Sound 表示比较短的音频
 *
 * @author Gavin Liu
 *
 * Created on 2019-09-06.
 */
interface Sound : Disposable {

    fun play(): Int

    fun play(volume: Float): Int

    fun play(volume: Float, pitch: Float, pan: Float): Int

    fun loop(): Int

    fun loop(volume: Float): Int

    fun loop(volume: Float, pitch: Float, pan: Float): Int

    fun stop()

    fun pause()

    fun resume()

    fun stop(streamId: Int)

    fun pause(streamId: Int)

    fun resume(streamId: Int)

    fun setLooping(streamId: Int, looping: Boolean)

    fun setPitch(streamId: Int, pitch: Float)

    fun setVolume(streamId: Int, volume: Float)

    fun setPan(streamId: Int, pan: Float, volume: Float)
}