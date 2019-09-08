package cn.quickits.aura.interfaces

interface Music : Disposable {

    fun play()

    fun pause()

    fun stop()

    fun isPlaying(): Boolean

    fun setLooping(isLooping: Boolean)

    fun isLooping(): Boolean

    fun setVolume(volume: Float)

    fun getVolume(): Float

    fun setPan(pan: Float, volume: Float)

    fun setPosition(position: Float)

    fun getPosition(): Float

    fun setOnCompletionListener(listener: OnCompletionListener)

    interface OnCompletionListener {
        fun onCompletion(music: Music)
    }
}