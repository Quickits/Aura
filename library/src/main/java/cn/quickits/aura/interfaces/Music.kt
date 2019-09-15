package cn.quickits.aura.interfaces

/**
 * Music 表示比较长的音频
 *
 * @author Gavin Liu
 *
 * Created on 2019-09-06.
 */
interface Music : Disposable {

    fun play()

    fun play(url: String)

    fun play(rawId: Int)

    fun pause()

    fun stop()

    fun isPlaying(): Boolean

    fun setLooping(isLooping: Boolean)

    fun isLooping(): Boolean

    fun setVolume(volume: Float)

    fun getVolume(): Float

    fun setPan(pan: Float, volume: Float)

    fun setPosition(position: Int)

    fun getPosition(): Int

    fun getDuration(): Int

    fun setOnCompletionListener(listener: OnCompletionListener)

    interface OnCompletionListener {
        fun onCompletion(music: Music)
    }
}