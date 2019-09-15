package cn.quickits.aura.interfaces

/**
 * @author Gavin Liu
 *
 * Created on 2019-09-06.
 */
interface Audio {

    fun pause()

    fun resume()

    fun newMusic(): Music

    fun newMusic(url: String): Music

    fun newMusic(rawId: Int): Music

    fun newSound(url: String): Sound

    fun newSound(rawId: Int): Sound
}