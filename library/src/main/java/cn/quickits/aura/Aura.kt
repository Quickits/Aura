package cn.quickits.aura

import cn.quickits.aura.impl.AndroidAudio
import cn.quickits.aura.interfaces.Audio
import cn.quickits.aura.interfaces.Music
import cn.quickits.aura.interfaces.Sound

object Aura : Audio {

    private val audio = AndroidAudio()

    override fun newMusic(): Music = audio.newMusic()

    override fun newSound(): Sound = audio.newSound()

}