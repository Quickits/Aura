package cn.quickits.aura.interfaces

interface Sound : Disposable {

    fun play(): Long

    fun play(volume: Float): Long

    fun play(volume: Float, pitch: Float, pan: Float): Long

    fun loop(): Long

    fun loop(volume: Float): Long

    fun loop(volume: Float, pitch: Float, pan: Float): Long

    fun stop()

    fun pause()

    fun resume()

    fun stop(soundId: Long)

    fun pause(soundId: Long)

    fun resume(soundId: Long)

    fun setLooping(soundId: Long, looping: Boolean)

    fun setPitch(soundId: Long, pitch: Float)

    fun setVolume(soundId: Long, volume: Float)

    fun setPan(soundId: Long, pan: Float, volume: Float)
}