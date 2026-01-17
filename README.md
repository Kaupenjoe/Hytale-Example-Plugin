# Hytale Plugin Template

A template for Hytale java plugins. Created by Up, and slightly modified by Kaupenjoe. 


## Override default Hytale install location

If you have installed Hytale in a custom location then by default the decompiled Server jar will not show up in IntelliJ External Libraries, also the `decompileServer` Gradle task will fail.

To fix this you can specify the your install location using `hytale` function in `build.gradle.kts` using `gameDir` and set the value to be where you have installed Hytale.

Example for Windows
```kotlin
hytale {
  gameDir = "G:/games/Hytale"
}
```
Where `G:` is your drive where Hytale is installed