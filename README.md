# Everything Ores

The ore unification mod for the **Forge Everything** modpack. This mod owns all duplicate ore blocks, raw ores, and ingots across the pack so that every other mod pulls from a single source of truth instead of generating its own ore in the world.

---

## Prerequisites

Before setting up the project make sure you have the following installed:

### Java 21 JDK
NeoForge 1.21.1 requires **Java 21 specifically**. Java 24 will not work for building.

- Download from [Oracle](https://www.oracle.com/java/technologies/downloads/#java21) or [Adoptium](https://adoptium.net) (Temurin 21)
- Install using the Windows x64 installer
- Default install path will be `C:\Program Files\Java\jdk-21` (Oracle) or similar

### VS Code
Download from [code.visualstudio.com](https://code.visualstudio.com)

Install these three extensions inside VS Code (`Ctrl+Shift+X`):
- **Extension Pack for Java** — by Microsoft
- **Gradle for Java** — by Microsoft

### Git
Download from [git-scm.com](https://git-scm.com) if not already installed.

---

## Cloning the Repository

Open a terminal (PowerShell or Command Prompt) and run:

```powershell
git clone https://github.com/YOUR_ORG/everythingores.git
cd everythingores
```

Or clone it to a specific location:

```powershell
git clone https://github.com/YOUR_ORG/everythingores.git "C:\Users\YOUR_USERNAME\projects\Minecraft Mods\everythingores"
```

---

## Opening in VS Code

Open the project folder in VS Code:

```powershell
code "C:\Users\YOUR_USERNAME\projects\Minecraft Mods\everythingores"
```

Or open VS Code manually and use **File → Open Folder** and select the `everythingores` folder.

When VS Code opens the project you may see a prompt:

> *"A build file was modified. Do you want to synchronize the Java classpath/configuration?"*

Click **Always**. This lets VS Code stay in sync with Gradle automatically.

---

## Configuring the JDK

The project includes a `.vscode/settings.json` that points to JDK 21. If your JDK 21 is installed at a different path you will need to update it.

Open `.vscode/settings.json` and confirm the path matches your JDK 21 installation:

```json
{
  "java.jdt.ls.java.home": "C:\\Program Files\\Java\\jdk-21",
  "java.configuration.runtimes": [
    {
      "name": "JavaSE-21",
      "path": "C:\\Program Files\\Java\\jdk-21",
      "default": true
    }
  ]
}
```

If you are unsure where JDK 21 is installed, run this in PowerShell:

```powershell
Get-ChildItem "C:\Program Files\Java\"
```

Update the paths in `settings.json` and in `gradle.properties` (`org.gradle.java.home`) to match.

---

## Setting Up `local.properties`

The `local.properties` file tells Gradle where your **Forge Everything** modpack instance lives so it can automatically copy the built jar into the mods folder after each build. This file is **gitignored** and must be created manually by each developer.

Create the file in the project root:

```powershell
New-Item local.properties
```

Open it and add the following line, replacing `YOUR_USERNAME` with your Windows username:

```properties
modpack_mods_dir=C:\\Users\\YOUR_USERNAME\\AppData\\Roaming\\modrinth\\profiles\\forge everything\\mods
```

To find your exact Modrinth profiles path you can run:

```powershell
explorer "$env:APPDATA\modrinth\profiles"
```

This opens the profiles folder in Explorer — find your **Forge Everything** instance folder, open it, then copy the path to the `mods` folder inside and paste it into `local.properties`.

> **Note:** Use double backslashes `\\` in the path, not single `\`.

> **Note:** This path is only if you're using modrinth as your launcher. Otherwise it will need to point to where ever you have the modpack installed.

---

## Building and Deploying

### First build

The first build will take several minutes as Gradle downloads NeoForge, Minecraft, and all required dependencies. Subsequent builds are much faster.

In the VS Code terminal (`Ctrl+`` `):

```powershell
.\gradlew build
```

A successful build ends with:
```
BUILD SUCCESSFUL in Xs
```

### Deploy to the modpack

To build and automatically copy the jar into your Forge Everything mods folder:

```powershell
.\gradlew copyToModpack
```

You should see:
```
BUILD SUCCESSFUL
Copied to: C:\Users\...\modrinth\profiles\forge everything\mods
```

Then launch the **Forge Everything** instance in Modrinth and Everything Ores will appear in the mod list.

### Refreshing dependencies

If you run into missing library errors or something seems broken:

```powershell
.\gradlew --refresh-dependencies
```

### Clean build

To wipe all cached build output and start fresh:

```powershell
.\gradlew clean build
```

---

## Project Structure

```
everythingores\
├── .vscode\
│   └── settings.json              # JDK path config for VS Code
├── src\main\
│   ├── java\dev\forgeeverything\everythingores\
│   │   ├── EverythingOres.java     # Main mod class
│   │   └── registry\
│   │       ├── EOBlocks.java       # All 24 ore block registrations
│   │       ├── EOItems.java        # Raw ores, ingots, mineral drops
│   │       └── EOCreativeTab.java  # Creative mode tab
│   └── resources\
│       ├── assets\everythingores\  # Blockstates, models, textures, lang
│       ├── data\everythingores\    # Loot tables, worldgen, biome modifiers
│       ├── data\neoforge\tags\     # neoforge: tag files
│       ├── data\c\tags\            # c: (common) tag files
│       ├── data\minecraft\tags\    # Tool tier and mineable tags
│       └── META-INF\
│           └── neoforge.mods.toml  # Mod metadata
├── .gitignore
├── build.gradle                   # Gradle build config + copyToModpack task
├── gradle.properties              # Mod metadata and version properties
├── local.properties               # Your local Modrinth path (NOT committed)
├── settings.gradle
├── gradlew
└── gradlew.bat
```

---

## Common Issues

**`mod_version` unknown error**
Make sure all required properties are present in `gradle.properties`. See the properties section above.

**Build uses wrong Java version**
Confirm `org.gradle.java.home` in `gradle.properties` points to your JDK 21 folder, not JDK 24 or another version.

**`copyToModpack` does nothing**
Check that `local.properties` exists in the project root and the path uses double backslashes. Also confirm the `forge everything` folder name matches exactly (it is case-sensitive on some systems).

**Gradle daemon errors**
Kill all running daemons and retry:
```powershell
.\gradlew --stop
.\gradlew build
```
