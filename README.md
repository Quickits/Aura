# Aura

🎶 Aura is a an audio player framework

## Feature

- Global Pause & Resume
- Base on IjkMediaPlayer & SoundPool

## Usage

### init

```kotlin
Aura.init(context)
```

### Create Music or Sound

```kotlin
val music = Aura.newMusic("your music url")
val sound = Aura.newSound(R.raw.your_sound_id)
```

### Control Music or Sound

```kotlin
music.play()
sound.play()
...
```

### Release Music or Sound

```kotlin
music.dispose()
sound.dispose()
```

### Golbal Pause & Resume

```kotlin
Aura.pause()
Aura.resume()
```

# License

Apache License Version 2.0

Copyright (c) 2019-present, Quickits.CN
