# Hytale Plugin Template

A template for Hytale java plugins. Created by [Up](https://github.com/UpcraftLP), and slightly modified by Kaupenjoe and AzureDoom.
Built with the [Hytale Gradle Plugin](https://github.com/AzureDoom/Hytale-Gradle-Plugin) now.

### Getting Started

```bash
# Generate decompiled sources for your IDE and download the game assets
./gradlew setupHytaleDev

# Launch a local server with this plugin loaded
./gradlew runServer

# Launch with a debugger attached and hot swap enabled
./gradlew runServer -Ddebug=true -Dhotswap=true
```

The first run will prompt you to sign in with your Hytale account so the assets can be downloaded.
Run `./gradlew hytaleDoctor` if something isn't resolving correctly.

### Configuring the Template
Plugin metadata (group, name, description, authors, main class, dependencies) lives in `gradle.properties`.
`src/main/resources/manifest.json` is regenerated from those values on every build, so don't edit it by hand.

`src/main/resources` is linked straight into the local server's `run/mods` folder, so any asset changes you make
in-game with the Asset Editor are written directly back into your source tree.

If you'd rather use your installed copy of the game than download the assets, point the project at it.
The recommended way is to create a file at `%USERPROFILE%/.gradle/gradle.properties` to set this globally.

```properties
# Use a local Hytale install instead of downloading assets
hytale_home=path/to/Hytale
```

See the [plugin wiki](https://github.com/AzureDoom/Hytale-Gradle-Plugin/wiki) for the full list of options.
